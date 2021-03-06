/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionUsers.services;

import java.security.SecureRandom;
import scrumifyd.util.BCrypt;
import scrumifyd.GestionUsers.models.User;
import scrumifyd.util.MyDbConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scrumifyd.GestionProjets.services.ProjectService;

/**
 *
 * @author Amira Doghri
 */
public class UserCrud {

    Connection Cn = MyDbConnection.getInstance().getConnexion();
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public void ajouterUser(User U) {
        try {
            Statement st = Cn.createStatement(); //l'element qui va éxécuter la requete sql

            String req = "insert into user ( username, username_canonical, email, email_canonical, enabled, salt,password,last_login,confirmation_token,password_requested_at,roles) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ste = Cn.prepareStatement(req);
            ste.setString(1, U.getUsername());
            ste.setString(2, U.getUsernameCanonical());
            ste.setString(3, U.getEmail());
            ste.setString(4, U.getEmailCanonical());
            ste.setBoolean(5, U.getEnabled());
            ste.setString(6, U.getSalt());
            ste.setString(7, BCrypt.hashpw(U.getPassword(), BCrypt.gensalt(12)));
            ste.setDate(8, (Date) U.getLastLogin());
            ste.setString(9, U.getConfirmationToken());
            ste.setDate(10, (Date) U.getPasswordRequestedAt());
            ste.setString(11, U.getRoles());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error sql : " + ex.getMessage());
        }

    }

    public Boolean SupprimerUser(int id) {
        try {
            Statement st = Cn.createStatement();
            String req = "Update user set enabled=0  where id=" + id;
            st.executeUpdate(req);
            return true;
           
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
   public Boolean enableUser(int id) {
        try {
            Statement st = Cn.createStatement();
            String req = "Update user set enabled=1  where id=" + id;
            st.executeUpdate(req);
            return true;
           
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    public ObservableList<User> getAllUser() {
        ObservableList<User> l = FXCollections.observableArrayList();

        try {
            Statement st = Cn.createStatement();

            String req = "select * from user";

            ResultSet rs = st.executeQuery(req); //retourne un résulat

            while (rs.next()) {
                User U = new User(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getString(4),rs.getString(6),  rs.getBoolean(8), rs.getDate(11), rs.getString(15));

                l.add(U);
            }

            return l;
        } catch (SQLException ex) {
            System.out.println("erreur " + ex.getMessage());
            return null;
        }
    }

    public void modifierUser(User u) {
        try {
            PreparedStatement ps = Cn.prepareStatement("update user set username=?,email=?,enabled=?,password=?,roles=? where id=?");
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getEmail());
            ps.setBoolean(3, u.getEnabled());
            ps.setString(4, BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(12)));
            ps.setString(5, u.getRoles());
            ps.setInt(6, u.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("error : " + ex.getCause());
        }
    }

    public User VerifyUser(String username) {
        User u = new User();

        String query = "select * from user where username = '" + username + "'";
        try {
            Statement st = Cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(10));
            }
            return u;
        } catch (SQLException ex) {
            System.out.println("erreur" + ex.getMessage());
            return null;
        }

    }

    public Boolean isUserExist(String username, String userEmail) {
        boolean userId = false;

        String req = "SELECT * FROM user where email = ? or username = ?";
        PreparedStatement prpStm;
        try {
            prpStm = Cn.prepareStatement(req);
            prpStm.setString(1, userEmail);
            prpStm.setString(2, username);
            ResultSet result = prpStm.executeQuery();
            if (result.next()) {
                userId = true;
            } else {
                userId = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userId;
    }

    public Boolean isExist(String userEmail) {
        boolean userId = false;

        String req = "SELECT * FROM user where email = ? ";
        PreparedStatement prpStm;
        try {
            prpStm = Cn.prepareStatement(req);
            prpStm.setString(1, userEmail);
            ResultSet result = prpStm.executeQuery();
            if (result.next()) {
                userId = true;
            } else {
                userId = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userId;
    }

    public String getConf(String userEmail) {
        String c = null;

        String req = "SELECT confirmation_token FROM user where email = ?";
        PreparedStatement prpStm;
        try {
            prpStm = Cn.prepareStatement(req);
            prpStm.setString(1, userEmail);
            ResultSet result = prpStm.executeQuery();
            if (result.next()) {
                c = result.getString("confirmation_token");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return c;
    }

    public static boolean validateEmailAddress(String emailID) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(emailID).matches();
    }

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public Boolean VerifyEnable(String u) {
        int c = 0;
        String req = "SELECT enabled FROM user where username = ?";
        PreparedStatement prpStm;
        try {
            prpStm = Cn.prepareStatement(req);
            prpStm.setString(1, u);
            ResultSet result = prpStm.executeQuery();
            if (result.next()) {
                c = result.getInt("enabled");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (c == 1) {
            return true;
        } else {
            return false;
        }
    }

    public String findByEmail(String email) {
        String c = null;

        String req = "SELECT username FROM user where email = ?";
        PreparedStatement prpStm;
        try {
            prpStm = Cn.prepareStatement(req);
            prpStm.setString(1, email);
            ResultSet result = prpStm.executeQuery();
            if (result.next()) {
                c = result.getString("username");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return c;
    }

    public User getUser(int id) {
        User u = new User();

        String req = "SELECT * FROM user where id = ?";
        PreparedStatement prpStm;
        try {
            prpStm = Cn.prepareStatement(req);
            prpStm.setInt(1, id);
            ResultSet result = prpStm.executeQuery();
            if (result.next()) {
                u.setId(result.getInt("id"));
                u.setName(result.getString("name"));
                u.setLastname(result.getString("lastname"));
                u.setUsername(result.getString("username"));
                u.setEmail(result.getString("email"));
                u.setImage(result.getString("image"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return u;
    }

    public Boolean resetP(int id, String new1) throws SQLException {
        String newp = BCrypt.hashpw(new1, BCrypt.gensalt(13));

        String req = "UPDATE user set password =? where id =" + id + " ";
        PreparedStatement stm = Cn.prepareStatement(req);
        stm.setString(1, newp);

        if (stm.executeUpdate() > 0) {

            return true;

        } else {
            return false;
        }
    }

    public String getP(int id) {
        String p = null;
        String req = "SELECT password FROM user where id = ?";
        PreparedStatement prpStm;
        try {
            prpStm = Cn.prepareStatement(req);
            prpStm.setInt(1, id);
            ResultSet result = prpStm.executeQuery();
            if (result.next()) {
                p = result.getString("password");

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return p;
    }

    public boolean deleteAcc(int id) {
        try {
            String delete = "DELETE FROM user WHERE ID=" + id + "";
            PreparedStatement stm = Cn.prepareStatement(delete);

            int res = stm.executeUpdate();
            return (res > 0);

        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean updateAv(String img, int id) {
        try {
            String update = "UPDATE user set image=?  WHERE ID='" + id + "'";
            PreparedStatement stm = Cn.prepareStatement(update);
            stm.setString(1, img);
            int res = stm.executeUpdate();
            return (res > 0);

        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean updateProfile(User u) {
        try {
            String update = "UPDATE user set name=? , lastname=? , username=?,email=? WHERE ID=" + u.getId() + "";
            PreparedStatement stm = Cn.prepareStatement(update);
            stm.setString(1, u.getName());
            stm.setString(2, u.getLastname());
            stm.setString(3, u.getUsername());
            stm.setString(4, u.getEmail());

            int res = stm.executeUpdate();
            return (res > 0);

        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }
  public ArrayList<Integer> getTeams(int user_id){
      ArrayList<Integer> team_list = new ArrayList<>();
      String req="SELECT t.team_id FROM team_user t WHERE t.user_id="+user_id;
      PreparedStatement prpStm;
        try {
            prpStm = Cn.prepareStatement(req);
            ResultSet result = prpStm.executeQuery();
            if (result.next()) {
                int t = result.getInt("team_id");
                team_list.add(t);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
      return team_list;
  }
}

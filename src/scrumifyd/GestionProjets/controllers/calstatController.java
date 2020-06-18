/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.controllers;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.services.InterfaceProjet;
import scrumifyd.GestionProjets.services.ProjectService;
import scrumifyd.GestionUsers.controllers.SigninController;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class calstatController implements Initializable {

    InterfaceProjet pr = new ProjectService();

    @FXML
    private Pane contentPane;
    @FXML
    private Label lbl_calendar;
    @FXML
    private Label lbl_charts;

    private PieChart projectsChart;
    private PieChart projectsTChart;
    @FXML
    private PieChart charts;
    @FXML
    private PieChart charts1;
    @FXML
    private GridPane gridd;
    @FXML
    private Label year;
    @FXML
    private FontAwesomeIconView back;
    @FXML
    private FontAwesomeIconView next;

    private Year currentYear;
    Calendar calendar = Calendar.getInstance();
    @FXML
    private VBox p_1;
    @FXML
    private VBox p_2;
    @FXML
    private VBox p_3;
    @FXML
    private VBox p_4;
    @FXML
    private VBox p_5;
    @FXML
    private VBox p_6;
    @FXML
    private VBox p_7;
    @FXML
    private VBox p_8;
    @FXML
    private VBox p_10;
    @FXML
    private VBox p_9;
    @FXML
    private VBox p_11;
    @FXML
    private VBox p_12;
    @FXML
    private Label m;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        currentYear = Year.now();
        refreshCalendar(currentYear);
        EventHandler<MouseEvent> chartsHandler = (MouseEvent e) -> {
            if (e.getSource() == lbl_charts) {

                lbl_charts.setStyle("-fx-text-fill: #16cabd;");
                lbl_calendar.setStyle("-fx-text-fill: black;");

                contentPane.getChildren().clear();
                SigninController s = new SigninController();

                int user_id = SigninController.user.getUserId();
                charts = new PieChart(pr.getProjectGraphStatistics(user_id));
                charts.setLayoutX(500);

                charts1 = new PieChart(pr.getProjectTimeGraphStatistics(user_id));

                contentPane.getChildren().addAll(charts, charts1);

            }
        };
        EventHandler<MouseEvent> CalendarHandler = (MouseEvent e) -> {
            if (e.getSource() == lbl_calendar) {

                lbl_calendar.setStyle("-fx-text-fill: #16cabd;");
                lbl_charts.setStyle("-fx-text-fill: black;");
                refreshCalendar(currentYear);
            }
        };

        lbl_charts.addEventHandler(MouseEvent.MOUSE_CLICKED, chartsHandler);
        lbl_calendar.addEventHandler(MouseEvent.MOUSE_CLICKED, CalendarHandler);

    }

    public void setLabelColor(Label label) {
        label.setStyle("-fx-text-fill: #16cabd;" + "-fx-font-weight: bold;");
    }

    public void setLabelColor1(Label label) {
        label.setStyle("-fx-text-fill: #000000;" + "-fx-font-weight:regular;");
    }

//    public class calendar extends JFrame {
//
//        public calendar() {
//
//            setTitle("Calendar");
//            setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//            Container contentPane = getContentPane();
//            contentPane.setLayout(new GridLayout(2, 2, 20, 20));
//
//            // Create a border for all calendars
//            javax.swing.border.Border etchedBorder
//                    = BorderFactory.createEtchedBorder();
//            javax.swing.border.Border emptyBorder
//                    = BorderFactory.createEmptyBorder(10, 10, 10, 10);
//            CompoundBorder compoundBorder
//                    = BorderFactory.createCompoundBorder(etchedBorder, emptyBorder);
//
//            // Create a date listener to be used for all calendars
//            MyDateListener listener = new MyDateListener();
//
//            // Display date and time using the default calendar and locale.
//            // Display today's date at the bottom.
//            JCalendar calendar1
//                    = new JCalendar(
//                            JCalendar.DISPLAY_DATE | JCalendar.DISPLAY_TIME,
//                            true);
//            calendar1.addDateListener(listener);
//            calendar1.setBorder(compoundBorder);
//
//            calendar1.setSize(500, 500);
//
//            // Add all the calendars to the content  pane
//            JPanel panel1 = new JPanel(new FlowLayout());
//            panel1.setSize(500, 500);
//            panel1.add(calendar1);
//            contentPane.add(panel1);
//
//            // Make the window visible
//            pack();
//            setVisible(true);
//        }
//    }
//
//    private class MyDateListener implements DateListener {
//
//        @Override
//        public void dateChanged(DateEvent e) {
//            Calendar c = e.getSelectedDate();
//            if (c != null) {
//                System.out.println(c.getTime());
//            } else {
//                System.out.println("No time selected.");
//            }
//        }
//
//    }
    public void refreshCalendar(Year cuYear) {
        contentPane.getChildren().clear();
        System.out.println(cuYear);
        
        for (int i = 0; i < 12; i++) {
            ScrollPane pn = (ScrollPane) gridd.getChildren().get(i);
            VBox bb = (VBox) pn.getContent();
            bb.getChildren().clear();
        }

        SigninController s = new SigninController();
        int user_id = SigninController.user.getUserId();
        InterfaceProjet Projects = new ProjectService();
        List<Project> pr1 = Projects.getDeadlines(user_id);
        Node nodes[] = new Node[pr1.size() + 1];
        for (int i = 1; i < 13; i++) {

            for (int j = 0; j < pr1.size(); j++) {
                Project p = pr1.get(j);

                if (p.getDuedate().getMonthValue() == i && p.getDuedate().getYear() == cuYear.getValue()) {
                    try {

                        FXMLLoader loader = new FXMLLoader(calstatController.this.getClass().getResource("/scrumifyd/GestionProjets/views/calendarEvent.fxml"));
                        nodes[j] = loader.load();
                        ItemCalendarController item = loader.getController();
                        item.setName(p.getName());
                        ScrollPane t = (ScrollPane) gridd.getChildren().get(i-1);
                        VBox box = (VBox) t.getContent();
                        box.getChildren().add(nodes[j]);
                    } catch (IOException ex) {
                        Logger.getLogger(calstatController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }

        if (cuYear == Year.now()) {
            gridd.getChildren().get(calendar.get(Calendar.MONTH)).setStyle("-fx-background-color: #16cabd");
        }

        contentPane.getChildren()
                .add(year);
        contentPane.getChildren()
                .add(back);
        contentPane.getChildren()
                .add(next);
        contentPane.getChildren()
                .add(gridd);

        back.setOnMouseClicked(
                (MouseEvent event) -> {
                    Year tt = minusYear();
                    year.setText(tt.toString());
                }
        );

        next.setOnMouseClicked(
                (event) -> {
                    Year tt = plusYear();
                    year.setText(tt.toString());

                }
        );
    }

    Year minusYear() {
        currentYear = currentYear.minusYears(1);
        refreshCalendar(currentYear);
        return currentYear;
    }

    Year plusYear() {
        currentYear = currentYear.plusYears(1);
        refreshCalendar(currentYear);
        return currentYear;
    }

    private BorderPane createCell(int month) {

        BorderPane cell = new BorderPane();
        //label.setOnMouseClicked(e -> addNewNote(ldt));

        VBox vbox = new VBox();
        vbox.getChildren().addAll(loadProjects(month));
        //BorderPane.setAlignment(vbox, Pos.CENTER);
        cell.setCenter(vbox);

        //BorderPane.setAlignment(label, Pos.TOP_RIGHT);
        //cell.setTop(label);
        //cell.getStyleClass().add("cell");
        return cell;
    }

    private List<VBox> loadProjects(int month) {
        List<VBox> tempList = new ArrayList();

        SigninController s = new SigninController();
        int user_id = SigninController.user.getUserId();
        InterfaceProjet Projects = new ProjectService();
        List<Project> pr1 = Projects.getDeadlines(user_id);
        pr1.forEach((t) -> {
            if (t.getDuedate().getMonthValue() == month) {
                VBox hbox = new VBox();
                hbox.getChildren().add(new Label(t.getName()));
                tempList.add(hbox);
            }
        });

        return tempList;
    }

    int getColumn(LocalDateTime ldt) {
        int i = 0;
        while (ldt.getDayOfWeek() != DayOfWeek.SUNDAY) {
            i++;
            ldt = ldt.plusDays(1);
        }

        return i;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        refreshCalendar();
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
                refreshCalendar();
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
    public void refreshCalendar() {
        contentPane.getChildren().clear();
        Calendar calendar = Calendar.getInstance();
        int yearr = calendar.get(Calendar.YEAR);
        System.out.println(yearr);
        year.setText("" + yearr);

        int month = calendar.get(Calendar.MONTH);
        for (int i = 1; i < 13; i++) {
            if (month == i) {
                gridd.getChildren().get(i).setStyle("-fx-background-color: #c9b716");
            }
        }

        SigninController s = new SigninController();
        int user_id = SigninController.user.getUserId();

        InterfaceProjet Projects = new ProjectService();

        List<Project> pr1 = Projects.getDeadlines(user_id);

        pr1.forEach((Project t) -> {
            for (int i = 0; i < 13; i++) {
                if (t.getDuedate().getMonthValue() == i) {
                    System.out.println(t.getName());
                    ScrollPane b = new ScrollPane(new Label(t.getName()));
                    System.out.println(gridd.getChildren().get(i));
                    gridd.getChildren().get(i).toFront();
                    gridd.getChildren().add(i, b);

                }
            }
        });

        contentPane.getChildren().add(year);
        contentPane.getChildren().add(back);
        contentPane.getChildren().add(next);
        contentPane.getChildren().add(gridd);
    }
}

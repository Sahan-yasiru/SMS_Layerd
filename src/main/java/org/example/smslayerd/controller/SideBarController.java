package org.example.smslayerd.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {

    @FXML
    private AnchorPane temp;
    @FXML
    private Button btnDashbord;
    @FXML
    private Label lblWelcome;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblWelcome.setText(" Welcome " + LoginController.getLabel());
        lordPage("dashboradpage.fxml");
    }

    public void lordDashBord(ActionEvent actionEvent) {

        lordPage("dashboradpage.fxml");
    }

    public void lordPage(String location) {
        try {
            temp.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/org/example/smslayerd/" + location));
            temp.getChildren().add(pane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }

    }

    public void lordSettings(ActionEvent actionEvent) {
        lordPage("settingpage.fxml");
    }


    public void lordSubject(ActionEvent actionEvent) {
        lordPage("subjectpage.fxml");
    }

    public void lordAttendance(ActionEvent actionEvent) {
        lordPage("attendancepage.fxml");
    }

    public void lordStudent(ActionEvent actionEvent) {
        lordPage("StudentPage.fxml");
    }

    public void lordTeacher(ActionEvent actionEvent) {
        lordPage("TeacherPage.fxml");
    }

    public void lordClasses(ActionEvent actionEvent) {
        lordPage("ClassPage.fxml");
    }
    public void lordExmas(ActionEvent actionEvent) {
        lordPage("ExamPage.fxml");
    }
    public void lordTimeTable(ActionEvent actionEvent) {
        lordPage("TimeTable.fxml");
    }


    public void pressedExit(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/Exitpage.fxml"));
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);  // Important for transparent scenes
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void mouseEnterd(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            Button btn = (Button) event.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), btn);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(15);
            glow.setHeight(15);
            glow.setRadius(15);
            btn.setEffect(glow);

        }
    }

    public void mouseExited(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            Button btn = (Button) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), btn);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            btn.setEffect(null);
        }
    }

}



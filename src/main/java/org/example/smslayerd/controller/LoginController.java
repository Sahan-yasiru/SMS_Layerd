package org.example.smslayerd.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.smslayerd.bo.BOFactory;
import org.example.smslayerd.bo.custom.UserBO;
import org.example.smslayerd.bo.custom.impl.UserBOImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {

    public TextField txtuserName;
    public PasswordField txtPassword;
    public AnchorPane loginPane;
    private UserBO userBO;
    private static String userName;
    @FXML
    private Button btnLogin;


    public void login(ActionEvent actionEvent) {

        userBO = (UserBOImpl) BOFactory.getInstance().getBOType(BOFactory.BOTypes.User);
        try {
            Boolean b = userBO.chackLogin(txtuserName.getText(), txtPassword.getText());
            if (b) {
                userName = txtuserName.getText();
                lunchSidebar();
            } else {
                clear();
                new Alert(Alert.AlertType.ERROR, "Incorrect Password").show();

            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
            clear();
        }
    }

    public void clear() {
        txtPassword.setText("");
        txtuserName.setText("");
    }

    public void lunchSidebar() {
        try {
            Parent dashboardRoot = FXMLLoader.load(getClass().getResource("/org/example/smslayerd/sidebar.fxml"));
            Scene dashboardScene = new Scene(dashboardRoot);

            Stage stage = (Stage) loginPane.getScene().getWindow();
            stage.setScene(dashboardScene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.centerOnScreen();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "page not found");
            alert.show();
        }


    }

    public void lordAddUser(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/org/example/smslayerd/crateuserpage.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            scene.setFill(Color.TRANSPARENT);
            stage.centerOnScreen();
            stage.alwaysOnTopProperty();
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
        }

    }

    public static String getLabel() {
        return userName;

    }

    public void mouseeEnterd(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            Button btn = (Button) event.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), btn);
            scaleT.setToX(btn.getScaleX()+0.1);
            scaleT.setToY(btn.getScaleY()+0.1);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(15);
            glow.setHeight(15);
            glow.setRadius(15);
            btn.setEffect(glow);

        }
    }

    public void mouseeExited(MouseEvent event) {
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


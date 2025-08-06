package org.example.smslayerd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("/org/example/smslayerd/loginpage.fxml"));
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
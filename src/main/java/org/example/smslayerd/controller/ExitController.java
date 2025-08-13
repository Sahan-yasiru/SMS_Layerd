package org.example.smslayerd.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ExitController implements Initializable {

    @FXML
    private Button btnYes;
    @FXML
    private Button btnNo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnYes.setOnAction(actionEvent -> {
            System.exit(1);
        });
        btnNo.setOnAction(actionEvent -> {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        });
    }
}

package org.example.smslayerd.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.example.smslayerd.bo.BOFactory;
import org.example.smslayerd.bo.custom.impl.UserBOImpl;
import org.example.smslayerd.model.DtoAdmin;
import org.example.smslayerd.view.tdm.AdminTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class settingsController implements Initializable {

    @FXML
    private AnchorPane anPane;
    @FXML
    private TableColumn<AdminTM, String> ColAdmin_ID;
    @FXML
    private ChoiceBox<String> cboxType;
    @FXML
    private TableColumn<AdminTM, String> colType;
    @FXML
    private TableColumn<AdminTM, String> colUser_Name;
    @FXML
    private TableColumn<AdminTM, String> colPassword;
    @FXML
    private TableView<AdminTM> tableView;
    @FXML
    private HBox hboxCurrentPass;
    @FXML
    private HBox hboxType;
    @FXML
    private HBox boxsaveandUpdate;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    @FXML
    private Label txtnum;
    @FXML
    private Label labelAdminID;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField SearchBar;
    private UserBOImpl userBO=(UserBOImpl) BOFactory.getInstance().getBOType(BOFactory.BOTypes.User);
    private DtoAdmin dtoAdmin;
    private static boolean adminType;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chackAdminType();
        ColAdmin_ID.setCellValueFactory(new PropertyValueFactory<>("adminID"));
        colUser_Name.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colType.setCellValueFactory(new PropertyValueFactory<>("adminType"));
        reLord();
    }

    public void chackAdminType() {
        try {
            Boolean b = userBO.chackAdminType(LoginController.getLabel());
            if (b) {
                adminType = true;

            } else {
                adminType = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            adminType = false;
        }
    }

    public void lordAdminID() {
        try {
            labelAdminID.setText(userBO.getNewId());

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
            throw new RuntimeException();
        }
    }

    public void adminSave(ActionEvent actionEvent) {
        try {
            DtoAdmin dtoAdmin1 = new DtoAdmin(labelAdminID.getText(), txtUserName.getText(), txtPassword.getText(), cboxType.getValue());
            boolean result = userBO.save(dtoAdmin);
            new Alert(Alert.AlertType.INFORMATION, result?"successfully added":"Failed").show();
            reLord();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void adminUpdate(ActionEvent actionEvent) {
        try {
            if (labelAdminID.getText().isEmpty() || txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty() || adminType == true ? cboxType.getValue() != null : true) {
                dtoAdmin = new DtoAdmin(labelAdminID.getText(), txtUserName.getText(), txtPassword.getText(), cboxType.getValue());
                boolean result = userBO.adminUpdateWithType(dtoAdmin, adminType);
                new Alert(Alert.AlertType.INFORMATION, result?"successfully Updated":"Failed").show();
                reLord();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Records are empty").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void adminDelete(ActionEvent actionEvent) {
        if(labelAdminID.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Record is empty").show();
        }
        try {
            boolean result = userBO.delete(labelAdminID.getText());
            new Alert(Alert.AlertType.INFORMATION, result?"successfully deleted":"Failed").show();
            reLord();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void clear() {
        txtUserName.clear();
        SearchBar.clear();
        txtPassword.clear();
        setNumber();
    }

    public void setNumber() {
        try {
            txtnum.setText(userBO.getNumber());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void reLord() {
        setNumber();
        lordTable();
        clear();
        lordAdminID();
        if (!adminType) {
            hboxType.setDisable(true);
            boxsaveandUpdate.setDisable(true);
        } else {
            boxsaveandUpdate.setDisable(false);
            hboxType.setDisable(false);
        }
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);

    }

    public void lordTable() {
            try {
                ObservableList<AdminTM> adminTMS=FXCollections.observableArrayList();
                userBO.getAll(adminType).forEach(dtoAdmin1 -> {
                    adminTMS.add(new AdminTM(dtoAdmin1.getAdminID(),dtoAdmin1.getUserName(),dtoAdmin1.getPassword(),dtoAdmin1.getAdminType()));
                });
                tableView.setItems(adminTMS);
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

    }

    public void lordtableAgin(MouseEvent mouseEvent) {
        reLord();
    }

    public void searchUser(KeyEvent keyEvent) {
        lordTable();
        FilteredList<AdminTM> filteredList = new FilteredList<>(tableView.getItems(), e -> true);
        SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(dtoAdmin -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String filterText = newValue.toLowerCase();
                return dtoAdmin.getAdminID().toLowerCase().contains(filterText) ||
                        dtoAdmin.getUserName().toLowerCase().contains(filterText) ||
                        dtoAdmin.getAdminType().toLowerCase().contains(filterText) ;
            });
        });
        SortedList<AdminTM> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }


    public void tableClicked(MouseEvent mouseEvent) {
        AdminTM adminTM = tableView.getSelectionModel().getSelectedItem();

        if (adminTM != null) {
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            labelAdminID.setText(adminTM.getAdminID());
            txtUserName.setText(adminTM.getUserName());
            try {
                txtPassword.setText(userBO.getPassword(labelAdminID.getText()));
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            cboxType.setValue(adminTM.getAdminType());
        }

    }

    public void lordAdminTypes(MouseEvent mouseEvent) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.add("SuperAdmin");
        observableList.add("Admin");
        cboxType.setItems(observableList);

    }
}

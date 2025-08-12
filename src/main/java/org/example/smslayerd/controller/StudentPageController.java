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
import javafx.scene.input.MouseEvent;
import org.example.smslayerd.bo.BOFactory;
import org.example.smslayerd.bo.custom.ClassBO;
import org.example.smslayerd.bo.custom.StudentBO;
import org.example.smslayerd.bo.custom.impl.ClassBOImpl;
import org.example.smslayerd.bo.custom.impl.StudentBOImpl;
import org.example.smslayerd.model.DtoStudent;
import org.example.smslayerd.view.tdm.StudentTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class StudentPageController implements Initializable {
    @FXML
    private TextField txtTelNo, txtStuName, txtAdress;
    @FXML
    private ComboBox<String> cmbClassID;
    @FXML
    private ComboBox<Integer> cmbGrade;
    @FXML
    private TableView<StudentTM> tableView;
    @FXML
    private Button saveButton;
    @FXML
    private Button UpdateButton, deleteBtn;
    @FXML
    private Button DeleteBtn;
    @FXML
    private TextField SearchBar;
    @FXML
    private Label lblStudentID;
    @FXML
    private TableColumn<StudentTM, String> colStudetID, colClassID, colName, colAddress;
    @FXML
    private TableColumn<StudentTM, Integer> colGrade, colTelNo;
    @FXML
    private Label lblNumber;
    private StudentBO studentBO= (StudentBOImpl)BOFactory.getInstance().getBOType(BOFactory.BOTypes.Student);
    private ClassBO classBO=(ClassBOImpl) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reLode();
        clear();
        deleteBtn.setOnAction(actionEvent -> {
            txtAdress.clear();
            SearchBar.clear();
            txtTelNo.clear();
            txtStuName.clear();
            cmbClassID.setValue(null);
            cmbGrade.setValue(null);
        });
        searchSubject();

    }

    public void getID() {
        try {
            lblStudentID.setText(studentBO.getNewId());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnSave(ActionEvent actionEvent) {
        if (txtTelNo.getText().isEmpty() || txtStuName.getText().isEmpty() || txtAdress.getText().isEmpty() || cmbClassID.getValue().isEmpty() || cmbGrade.getValue() == 0) {
            ArrayList<Control> controls = new ArrayList<>(Arrays.asList(txtTelNo, txtStuName, txtAdress, cmbClassID, cmbGrade));
            controls.forEach(e -> {
                e.setStyle(e.getStyle() + " -fx-border-color: red;");
            });

        } else {
            Boolean b = false;
            Integer tel = 0;
            try {
                tel = Integer.parseInt(txtTelNo.getText());
                b = true;
                b= txtTelNo.getText().length() == 10;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (b) {
                try {
                    studentBO.save(new DtoStudent(lblStudentID.getText(), tel, cmbClassID.getValue(), txtStuName.getText(), cmbGrade.getValue(), txtAdress.getText()));
                    reLode();
                    clear();
                } catch (SQLException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } else {
                txtTelNo.setStyle(txtTelNo.getStyle() + " -fx-border-color: red;");
            }
        }
    }

    public void btnUpdate(ActionEvent actionEvent) {
        if (txtTelNo.getText().isEmpty() || txtStuName.getText().isEmpty() || txtAdress.getText().isEmpty() || cmbClassID.getValue().isEmpty() || cmbGrade.getValue() == 0) {
            ArrayList<Control> controls = new ArrayList<>(Arrays.asList(txtTelNo, txtStuName, txtAdress, cmbClassID, cmbGrade));
            controls.forEach(e -> {
                e.setStyle(e.getStyle() + " -fx-border-color: red;");
            });

        } else {
            Integer tel = 0;
            try {
                tel = Integer.parseInt(txtTelNo.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                txtTelNo.setStyle(txtTelNo.getStyle() + " -fx-border-color: red;");

            }
            try {
                studentBO.update(new DtoStudent(lblStudentID.getText(), tel, cmbClassID.getValue(), txtStuName.getText(), cmbGrade.getValue(), txtAdress.getText()));
                reLode();
                clear();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }

    public void btnDelete(ActionEvent actionEvent) {
        try {
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"DO you want to delete this student?",ButtonType.YES,ButtonType.NO);
            if (alert.showAndWait().get()==ButtonType.YES) {
                boolean result = studentBO.delete(lblStudentID.getText());
                new Alert(Alert.AlertType.INFORMATION, result?"Done":"Failed").show();
                reLode();
                clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void lordTable() {
        colStudetID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colTelNo.setCellValueFactory(new PropertyValueFactory<>("telNO"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colClassID.setCellValueFactory(new PropertyValueFactory<>("classID"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));


        try {
            ObservableList<StudentTM> observableList=FXCollections.observableArrayList();
            studentBO.getAll().forEach(dtoStudent -> {
                observableList.add(new StudentTM(dtoStudent));
            });
            tableView.setItems(observableList);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void reLode() {
        ArrayList<Control> controls = new ArrayList<>(Arrays.asList(txtTelNo, txtStuName, txtAdress, cmbClassID, cmbGrade));
        controls.forEach(e -> {
            e.setStyle(e.getStyle() + " -fx-border-color: Black;");
        });
        lordTable();
        getID();
        setNumber();
        saveButton.setDisable(false);
        UpdateButton.setDisable(true);
        DeleteBtn.setDisable(true);


    }

    public void clear() {
            txtAdress.clear();
            SearchBar.clear();
            txtTelNo.clear();
            txtStuName.clear();
            cmbClassID.setValue(null);
            cmbGrade.setValue(null);
    }

    public void setNumber() {
        try {
            String result = studentBO.getNumber();
            lblNumber.setText(result);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void reFreshTable(MouseEvent mouseEvent) {
        reLode();
    }

    public void searchSubject() {
        lordTable();
        FilteredList<StudentTM> filteredList=new FilteredList<>(tableView.getItems(),e-> true);
        SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(dtoStudent -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String filterText=newValue.toLowerCase();
                return dtoStudent.getName().toLowerCase().contains(filterText)||
                        dtoStudent.getStudentID().toLowerCase().contains(filterText) ||
                        dtoStudent.getName().toLowerCase().contains(filterText)||
                        dtoStudent.getClassID().toLowerCase().contains(filterText) ||
                        Integer.toString(dtoStudent.getTelNO()).toLowerCase().contains(filterText) ||
                        Integer.toString(dtoStudent.getGrade()).toLowerCase().contains(filterText);
            });
        });
        SortedList<StudentTM> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }


    public void tableClicked(MouseEvent mouseEvent) {
        clear();
        StudentTM Student = tableView.getSelectionModel().getSelectedItem();

        if (Student != null) {
            saveButton.setDisable(true);
            UpdateButton.setDisable(false);
            DeleteBtn.setDisable(false);
            lblStudentID.setText(Student.getStudentID());
            txtStuName.setText(Student.getName());
            txtAdress.setText(Student.getAddress());
            txtTelNo.setText(Student.getTelNO() + "");
            cmbGrade.setValue(Student.getGrade());
            cmbClassID.setValue(Student.getClassID());
        }


    }

    public void lordClassID(MouseEvent mouseEvent) {
        try {
            classBO.getClassIDs().forEach(dtoClass->{
                cmbClassID.getItems().add(dtoClass.getClassID());
            });
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void lordGrade(MouseEvent mouseEvent) {
        ObservableList<Integer> grade = FXCollections.observableArrayList();
        for (int i = 1; i <= 13; i++) {
            grade.add(i);
        }

        cmbGrade.setItems(grade);
    }
}

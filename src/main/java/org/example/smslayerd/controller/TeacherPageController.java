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
import org.example.smslayerd.bo.custom.SubjectBO;
import org.example.smslayerd.bo.custom.TeacherBO;
import org.example.smslayerd.bo.custom.impl.ClassBOImpl;
import org.example.smslayerd.bo.custom.impl.SubjectBOImpl;
import org.example.smslayerd.bo.custom.impl.TeacherBOImpl;
import org.example.smslayerd.model.DtoTeacher;
import org.example.smslayerd.view.tdm.TeacherTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TeacherPageController implements Initializable {
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<String> cmbClassID;
    @FXML
    private ComboBox<Integer> cmbGrade;
    @FXML
    private ComboBox<String> cmbSubjectID;
    @FXML
    private TableView<TeacherTM> tableView;
    ;
    @FXML
    private Button UpdateButton, saveButton, btnClear, DeleteBtn;
    @FXML
    private TextField SearchBar;
    @FXML
    private Label lblTeacherID;
    @FXML
    private TableColumn<TeacherTM, String> colTeacherID, colSubjectID, colClassID, colTeaName;
    @FXML
    private TableColumn<TeacherTM, Integer> colGrade;
    @FXML
    private Label lblNumber;
    private TeacherBO teacherBO = (TeacherBOImpl) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Teacher);
    private ClassBO classBO = (ClassBOImpl) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Class);
    private SubjectBO subjectBO=(SubjectBOImpl)BOFactory.getInstance().getBOType(BOFactory.BOTypes.Subject);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reLode();
        clear();
        btnClear.setOnAction(actionEvent -> {
            txtName.clear();
            SearchBar.clear();
            cmbClassID.setValue(null);
            cmbGrade.setValue(null);
            cmbSubjectID.setValue(null);
        });
        searchSubject();
    }

    public void getID() {
        try {
            lblTeacherID.setText(teacherBO.getNewId());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnSave(ActionEvent actionEvent) {
        if (txtName.getText().isEmpty() || cmbClassID.getValue().isEmpty() || cmbGrade.getValue() == 0 || cmbSubjectID.getValue().isEmpty()) {
            ArrayList<Control> controls = new ArrayList<>(Arrays.asList(txtName, cmbSubjectID, cmbClassID, cmbGrade));
            controls.forEach(e -> {
                e.setStyle(e.getStyle() + " -fx-border-color: red;");
            });

        } else {
            try {
                boolean result = teacherBO.save(new DtoTeacher(lblTeacherID.getText(), cmbSubjectID.getValue(), txtName.getText(), cmbClassID.getValue(), cmbGrade.getValue()));
                new Alert(Alert.AlertType.INFORMATION, result ? "Done" : "Failed").show();
                reLode();
                clear();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }


    public void btnUpdate(ActionEvent actionEvent) {
        if (txtName.getText().isEmpty() || cmbClassID.getValue().isEmpty() || cmbGrade.getValue() == 0 || cmbSubjectID.getValue().isEmpty()) {
            ArrayList<Control> controls = new ArrayList<>(Arrays.asList(txtName, cmbSubjectID, cmbClassID, cmbGrade));
            controls.forEach(e -> {
                e.setStyle(e.getStyle() + " -fx-border-color: red;");
            });

        } else {
            try {
                boolean result = teacherBO.update(new DtoTeacher(lblTeacherID.getText(), cmbSubjectID.getValue(), txtName.getText(), cmbClassID.getValue(), cmbGrade.getValue()));
                new Alert(Alert.AlertType.INFORMATION, result ? "Updated" : "Failed").show();
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
            boolean result = teacherBO.delete(lblTeacherID.getText());
            new Alert(Alert.AlertType.INFORMATION, result ? "Deleted" : "Failed").show();
            reLode();
            clear();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void lordTable() {
        colTeacherID.setCellValueFactory(new PropertyValueFactory<>("TeacherID"));
        colSubjectID.setCellValueFactory(new PropertyValueFactory<>("subjectID"));
        colTeaName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colClassID.setCellValueFactory(new PropertyValueFactory<>("classId"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("gradeAssign"));


        try {
            ObservableList<TeacherTM> teacherTMS = FXCollections.observableArrayList();
            teacherBO.getAll().forEach(dto -> {
                teacherTMS.add(new TeacherTM(dto.getTeacherID(), dto.getSubjectID(), dto.getName(), dto.getClassId(), dto.getGradeAssign()));
            });
            tableView.setItems(teacherTMS);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void reLode() {
        ArrayList<Control> controls = new ArrayList<>(Arrays.asList(txtName, cmbSubjectID, cmbClassID, cmbGrade));
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
        txtName.clear();
        SearchBar.clear();
        cmbClassID.setValue(null);
        cmbGrade.setValue(null);
        cmbSubjectID.setValue(null);
    }

    public void setNumber() {
        try {
            String result = teacherBO.getNumber();
            lblNumber.setText(result);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void reFreshTable(MouseEvent mouseEvent) {
        reLode();
    }

    public void searchSubject() {
        lordTable();
        FilteredList<TeacherTM> filteredList = new FilteredList<>(tableView.getItems(), e -> true);
        SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(dtoTeacher -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String filterText = newValue.toLowerCase();
                return dtoTeacher.getName().toLowerCase().contains(filterText) ||
                        dtoTeacher.getSubjectID().toLowerCase().contains(filterText) ||
                        dtoTeacher.getName().toLowerCase().contains(filterText) ||
                        dtoTeacher.getClassId().toLowerCase().contains(filterText) ||
                        dtoTeacher.getTeacherID().toLowerCase().contains(filterText) ||
                        Integer.toString(dtoTeacher.getGradeAssign()).toLowerCase().contains(filterText);
            });
        });
        SortedList<TeacherTM> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }


    public void tableClicked(MouseEvent mouseEvent) {
        clear();
        TeacherTM teacherTM = tableView.getSelectionModel().getSelectedItem();

        if (teacherTM != null) {
            saveButton.setDisable(true);
            UpdateButton.setDisable(false);
            DeleteBtn.setDisable(false);
            lblTeacherID.setText(teacherTM.getTeacherID());
            txtName.setText(teacherTM.getName());
            cmbSubjectID.setValue(teacherTM.getSubjectID());
            cmbGrade.setValue(teacherTM.getGradeAssign());
            cmbClassID.setValue(teacherTM.getClassId());
        }


    }

    public void lordClassID(MouseEvent mouseEvent) {
        try {
            classBO.getClassIDs().forEach(dtoClass -> {
                cmbClassID.getItems().add(new String(dtoClass.getClassID()));
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

    public void lordSubjectIDs(MouseEvent mouseEvent) {
        try {
            cmbSubjectID.getItems().clear();
            subjectBO.getAll().forEach(dtoSubject -> {
                cmbSubjectID.getItems().add(dtoSubject.getSubjectID());
            });
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}

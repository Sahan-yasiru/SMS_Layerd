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
import javafx.util.StringConverter;
import org.example.smslayerd.bo.BOFactory;
import org.example.smslayerd.bo.custom.ExamBO;
import org.example.smslayerd.bo.custom.StudentBO;
import org.example.smslayerd.bo.custom.SubjectBO;
import org.example.smslayerd.bo.custom.TeacherBO;
import org.example.smslayerd.model.DtoExam;
import org.example.smslayerd.view.tdm.ExamTM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class ExamController implements Initializable {

    @FXML
    private TextField SearchBar;
    @FXML
    private Button saveButton,DeleteBtn,UpdateButton,clearbutton;
    @FXML
    private Label lblExmaID;
    @FXML
    private ComboBox<String> cmbSubjectIDs, cmbStudentIDs, cmbTeacherIDs;
    @FXML
    private TableView<ExamTM> tableView;
    @FXML
    private TableColumn<ExamTM, String> colSubjectID, colExamID, colStudentID, colExamDate, colTeacherID, colMarks;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField txtMaks;
    private ExamBO examBO=(ExamBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Exam);
    private SubjectBO subjectBO=(SubjectBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Subject);
    private StudentBO studentBO=(StudentBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Student);
    private TeacherBO teacherBO=(TeacherBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Teacher);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fixDate();
        reLorde();
        clear();

    }

    public void fixDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date == null ? "" : formatter.format(date);
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
            }
        });
        datePicker.setEditable(false);
    }

    public void reLorde() {
        lordTable();
        DeleteBtn.setDisable(true);
        UpdateButton.setDisable(true);
        saveButton.setDisable(false);
        try {
            lblExmaID.setText(examBO.getNewId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Control[] controls = {cmbSubjectIDs, cmbStudentIDs, cmbTeacherIDs, txtMaks, datePicker};
        for (int i = 0; i < controls.length; i++) {
            controls[i].setStyle(controls[i].getStyle() + ";-fx-border-color: black;");
        }
    }

    public void lordTable() {
        ArrayList<String> colNamesList = new ArrayList<>(List.of("subjectID", "examID", "studentID", "exmaDate", "teacherID", "marks"));
        ArrayList<TableColumn> tableColumnsList = new ArrayList<>(List.of(colSubjectID, colExamID, colStudentID, colExamDate, colTeacherID, colMarks));
        AtomicInteger index = new AtomicInteger(0);
        tableColumnsList.forEach(column -> {
            column.setCellValueFactory(new PropertyValueFactory<>(colNamesList.get(index.getAndIncrement())));
        });
        try {
            ObservableList<ExamTM>examTMS= FXCollections.observableArrayList();
            examBO.getAll().forEach(dtoExam -> {
                examTMS.add(new ExamTM(dtoExam));
            });
            tableView.setItems(examTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void reFreshTable(MouseEvent mouseEvent) {
        reLorde();

    }

    public void lordSubjectIDs(MouseEvent mouseEvent) {
        try {
            cmbSubjectIDs.getItems().clear();
            subjectBO.getAll().forEach(dtoSubject -> {
                cmbSubjectIDs.getItems().add(dtoSubject.getSubjectID());
            });

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void lordStudentIDs(MouseEvent mouseEvent) {
        try {
            cmbStudentIDs.getItems().clear();
            studentBO.getAll().forEach(dtoStudent -> {
                cmbStudentIDs.getItems().add(dtoStudent.getStudentID());
            });

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void lordTeacherIDs(MouseEvent mouseEvent) {
        try {
            cmbTeacherIDs.getItems().clear();
            teacherBO.getAll().forEach(dtoTeacher -> {
                cmbTeacherIDs.getItems().add(dtoTeacher.getTeacherID());
            });
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnDelete(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            if(alert.showAndWait().get()==ButtonType.OK){
                boolean result=examBO.delete(lblExmaID.getText());
                reLorde();
                clear();
            }
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnSave(ActionEvent actionEvent) {
        if (chackInseted()) {
            try {
                boolean result = examBO.save(new DtoExam(lblExmaID.getText(), cmbSubjectIDs.getValue(), cmbStudentIDs.getValue(), datePicker.getValue().toString(), cmbTeacherIDs.getValue(), Integer.parseInt(txtMaks.getText())));
                new Alert(Alert.AlertType.INFORMATION, result?"Saved":"Failed").show();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            reLorde();
            clear();
        }

    }
    public void clear(){
        txtMaks.clear();
        cmbSubjectIDs.setValue(null);
        cmbStudentIDs.setValue(null);
        cmbTeacherIDs.setValue(null);
        datePicker.setValue(null);
        SearchBar.clear();



    }

    public boolean chackInseted() {
        boolean[] chack = {cmbSubjectIDs.getValue() == null, cmbStudentIDs.getValue() == null, cmbTeacherIDs.getValue() == null, txtMaks.getText() == null, datePicker.getValue() == null};
        Control[] controls = {cmbSubjectIDs, cmbStudentIDs, cmbTeacherIDs, txtMaks, datePicker};
        boolean flag = true;
        for (int i = 0; i < chack.length; i++) {
            if (chack[i]) {
                controls[i].setStyle(controls[i].getStyle() + ";-fx-border-color: #CB0404;");
                flag = false;
            }else {
                controls[i].setStyle(controls[i].getStyle() + ";-fx-border-color: black;");
            }
        }
        if (flag) {
            if (txtMaks.getText() != null) {
                try {
                    int marks = Integer.parseInt(txtMaks.getText());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    txtMaks.setStyle(txtMaks.getStyle() + ";-fx-border-color: #CB0404;");
                    flag = false;
                }
            }
        }
        return flag;
    }

    public void btnUpdate(ActionEvent actionEvent) {
        if (chackInseted()) {
            try {
                boolean result = examBO.update(new DtoExam(lblExmaID.getText(), cmbSubjectIDs.getValue(), cmbStudentIDs.getValue(), datePicker.getValue().toString(), cmbTeacherIDs.getValue(), Integer.parseInt(txtMaks.getText())));
                new Alert(Alert.AlertType.INFORMATION, result?"Updated":"Failed").show();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            reLorde();
            clear();
        }

    }

    public void btnClear(ActionEvent actionEvent) {
        clear();

    }

    public void tableClicked(MouseEvent mouseEvent) {
        ExamTM examTM =tableView.getSelectionModel().getSelectedItem();
        if(examTM !=null){
            DeleteBtn.setDisable(false);
            UpdateButton.setDisable(false);
            saveButton.setDisable(true);
            txtMaks.setText(examTM.getMarks()+"");
            cmbSubjectIDs.setValue(examTM.getSubjectID());
            cmbStudentIDs.setValue(examTM.getStudentID());
            cmbTeacherIDs.setValue(examTM.getTeacherID());
            datePicker.setValue(LocalDate.parse(examTM.getExmaDate()));
            lblExmaID.setText(examTM.getExamID());
        }
    }
    public void serchExam(KeyEvent keyEvent) {
        lordTable();
        FilteredList<ExamTM> filterDate = new FilteredList<>(tableView.getItems(), e -> true);
        SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filterDate.setPredicate(dto -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterText = newValue.toLowerCase();
                return dto.getExamID().toLowerCase().contains(filterText) ||
                        dto.getExmaDate().contains(filterText) ||
                        dto.getSubjectID().toLowerCase().contains(filterText) ||
                        dto.getStudentID() .toLowerCase().contains(filterText) ||
                        dto.getTeacherID().toLowerCase().contains(filterText);



            });
        });
        SortedList<ExamTM> sortedList = new SortedList<>(filterDate);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }
}

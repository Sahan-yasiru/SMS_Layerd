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
import org.example.smslayerd.bo.BOFactory;
import org.example.smslayerd.bo.custom.ClassBO;
import org.example.smslayerd.bo.custom.SubjectBO;
import org.example.smslayerd.bo.custom.TimeTableBO;
import org.example.smslayerd.bo.custom.impl.ClassBOImpl;
import org.example.smslayerd.bo.custom.impl.SubjectBOImpl;
import org.example.smslayerd.bo.custom.impl.TimeTableBOImpl;
import org.example.smslayerd.model.DtoClass;
import org.example.smslayerd.view.tdm.ClassTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ClassPageController implements Initializable {
    @FXML
    private ComboBox<Integer> cmbGrade;
    @FXML
    private ComboBox<String> cmbTimeTB,cmbSubjectID;
    @FXML
    private TableView<ClassTM> tableView;
    @FXML
    private Button UpdateButton, saveButton, btnClear, DeleteBtn;
    @FXML
    private TextField SearchBar;
    @FXML
    private Label lblClassID;
    @FXML
    private TableColumn<ClassTM, String> colClassID, colTimeTB, colSubjectID;
    @FXML
    private TableColumn<ClassTM, Integer> colGrade;
    @FXML
    private Label lblNumber;

    private ClassBO classBO=(ClassBOImpl) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Class);
    private SubjectBO subjectBO=(SubjectBOImpl)BOFactory.getInstance().getBOType(BOFactory.BOTypes.Subject);
    private TimeTableBO timeTableBO=(TimeTableBOImpl)BOFactory.getInstance().getBOType(BOFactory.BOTypes.TimeTable);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reLode();
        clear();
        btnClear.setOnAction(actionEvent -> {
            cmbTimeTB.setValue(null);
            SearchBar.clear();
            cmbGrade.setValue(null);
            cmbSubjectID.setValue(null);
        });
    }

    public void getID() {
        try {
            lblClassID.setText(classBO.getNewId());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnSave(ActionEvent actionEvent) {
        if (cmbGrade.getValue() == null || cmbSubjectID.getValue().isEmpty() || cmbTimeTB.getValue().isEmpty()) {
            ArrayList<Control> controls = new ArrayList<>(Arrays.asList(cmbTimeTB, cmbSubjectID, cmbGrade));
            controls.forEach(e -> {
                e.setStyle(e.getStyle() + " -fx-border-color: red;");
            });

        } else {
            try {
                boolean result= classBO.save(new DtoClass(lblClassID.getText(), cmbGrade.getValue(),cmbTimeTB.getValue(), cmbSubjectID.getValue()));
                new Alert(Alert.AlertType.INFORMATION, result?"Saved":"Failed").show();
                reLode();
                clear();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }


    public void btnUpdate(ActionEvent actionEvent) {
        if (cmbGrade.getValue() == 0 || cmbSubjectID.getValue().isEmpty() || cmbTimeTB.getValue().isEmpty()) {
            ArrayList<Control> controls = new ArrayList<>(Arrays.asList(cmbTimeTB, cmbSubjectID, cmbGrade));
            controls.forEach(e -> {
                e.setStyle(e.getStyle() + " -fx-border-color: red;");
            });

        } else {
            try {
                boolean result= classBO.update(new DtoClass(lblClassID.getText(), cmbGrade.getValue(),cmbTimeTB.getValue(), cmbSubjectID.getValue()));
                new Alert(Alert.AlertType.INFORMATION, result?"Updated":"Failed").show();
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
            boolean result = classBO.delete(lblClassID.getText());
            new Alert(Alert.AlertType.INFORMATION, result?"Deleted":"Failed").show();
            reLode();
            clear();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void lordTable() {
        colSubjectID.setCellValueFactory(new PropertyValueFactory<>("subjectID"));
        colTimeTB.setCellValueFactory(new PropertyValueFactory<>("timeTableID"));
        colClassID.setCellValueFactory(new PropertyValueFactory<>("classID"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        try {
            ObservableList<ClassTM> dtoClasses = FXCollections.observableArrayList();
            classBO.getAll().forEach(dtoClass -> {
                dtoClasses.add(new ClassTM(dtoClass.getClassID(),dtoClass.getGrade(),dtoClass.getTimeTableID(),dtoClass.getSubjectID()));
            });
            tableView.setItems(dtoClasses);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void reLode() {
        ArrayList<Control> controls = new ArrayList<>(Arrays.asList(cmbTimeTB, cmbSubjectID, cmbGrade));
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
        SearchBar.clear();
        cmbGrade.setValue(null);
        cmbSubjectID.setValue(null);
        cmbTimeTB.setValue(null);
    }

    public void setNumber() {
        try {
            String result = classBO.getNumber();
            lblNumber.setText(result);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void reFreshTable(MouseEvent mouseEvent) {
        reLode();
    }

    public void searchBar(KeyEvent keyEvent) {
        lordTable();
        FilteredList<ClassTM> filteredList = new FilteredList<>(tableView.getItems(), e -> true);
        SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(dtoClass -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String filterText = newValue.toLowerCase();
                return dtoClass.getClassID().toLowerCase().contains(filterText) ||
                        dtoClass.getSubjectID().toLowerCase().contains(filterText) ||
                        dtoClass.getTimeTableID().toLowerCase().contains(filterText) ||
                        Integer.toString(dtoClass.getGrade()).toLowerCase().contains(filterText) ;
            });
        });
        SortedList<ClassTM> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }

    public void tableClicked(MouseEvent mouseEvent) {
        clear();
        ClassTM classTM = tableView.getSelectionModel().getSelectedItem();

        if (classTM != null) {
            saveButton.setDisable(true);
            UpdateButton.setDisable(false);
            DeleteBtn.setDisable(false);
            lblClassID.setText(classTM.getClassID());
            cmbSubjectID.setValue(classTM.getSubjectID());
            cmbGrade.setValue(classTM.getGrade());
            cmbSubjectID.setValue(classTM.getSubjectID());
            cmbTimeTB.setValue(classTM.getTimeTableID());
        }

    }

    public void lordGrade(MouseEvent mouseEvent) {
        ObservableList<Integer> grade = FXCollections.observableArrayList();
        for (int i = 1; i <= 13; i++) {
            grade.add(i);
        }

        cmbGrade.setItems(grade);
    }
    public void lordSubjectID(MouseEvent mouseEvent) {
        try {
            cmbSubjectID.getItems().clear();
            subjectBO.getAll().forEach(dtoSubject -> {
                cmbSubjectID.getItems().add(dtoSubject.getSubjectID());
            });
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void lordTimeTBIDs(MouseEvent mouseEvent) {
        try {
            cmbTimeTB.getItems().clear();
            timeTableBO.getAll().forEach(dtoTimeTable -> {
                cmbTimeTB.getItems().add(dtoTimeTable.getTimeTableID());
            });
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}

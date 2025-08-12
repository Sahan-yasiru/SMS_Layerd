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
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import org.example.smslayerd.bo.BOFactory;
import org.example.smslayerd.bo.custom.AttendanceStuBO;
import org.example.smslayerd.bo.custom.ClassBO;
import org.example.smslayerd.bo.custom.StudentBO;
import org.example.smslayerd.bo.custom.UserBO;
import org.example.smslayerd.bo.custom.impl.AttendanceStuBOImpl;
import org.example.smslayerd.bo.custom.impl.ClassBOImpl;
import org.example.smslayerd.bo.custom.impl.StudentBOImpl;
import org.example.smslayerd.bo.custom.impl.UserBOImpl;
import org.example.smslayerd.model.DtoAttendenceStu;
import org.example.smslayerd.model.DtoClass;
import org.example.smslayerd.model.DtoStudent;
import org.example.smslayerd.view.tdm.AttendenceStuTM;
import org.example.smslayerd.view.toggleButton.ToggleSwitch;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AttendanceStuController implements Initializable {

    public Label lblStatus;
    public Label adminID;
    public Label attendanceID;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSave, btnUpdate, btnDelete;
    @FXML
    private AnchorPane pane;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> cmbclassID;
    @FXML
    private RadioButton btnPresent;
    @FXML
    private RadioButton btnAbsent;
    @FXML
    private ComboBox<String> cmbStuID;
    @FXML
    private TableView<AttendenceStuTM> tableView;
    @FXML
    private TableColumn<AttendenceStuTM, String> colAttendID, colDate, colAdminID, colStudentID, colClassID;
    @FXML
    private TableColumn<AttendenceStuTM, ToggleSwitch> colStatus;

    private AttendanceStuBO attendanceStuBO = (AttendanceStuBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.AttendanceStu);
    private UserBO userBO = (UserBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.User);
    private ClassBO classBO = (ClassBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Class);
    private StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Student);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            adminID.setText(userBO.getAdminName(LoginController.getLabel()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        chanageDateFormat();
        lordCoboBoxes();
        setAttendLabel(true);
        reLord();
        teaSearch();

    }

    public void setAttendLabel(boolean b) {
        if (b) {
            btnPresent.setSelected(true);
            lblStatus.setStyle(lblStatus.getStyle() + "-fx-background-color: #4ED7F1;");
        } else {
            btnAbsent.setSelected(true);
            lblStatus.setStyle(lblStatus.getStyle() + "-fx-background-color: #FF8282;");
        }
        btnPresent.setOnAction(event -> {
            lblStatus.setStyle(lblStatus.getStyle() + "-fx-background-color: #4ED7F1;");
        });
        btnAbsent.setOnAction(event -> {
            lblStatus.setStyle(lblStatus.getStyle() + "-fx-background-color: #FF8282;");
        });

    }

    public void lordCoboBoxes() {
        ObservableList<String> strings = FXCollections.observableArrayList();
        try {
            ArrayList<DtoClass> dtoClasses = classBO.getClassIDs();
            dtoClasses.forEach(dtoClass -> {
                strings.add(dtoClass.getClassID());
            });
            cmbclassID.setItems(strings);
            strings.clear();
            ArrayList<DtoStudent> dtoStudents = studentBO.getStudentIDs();
            dtoStudents.forEach(dtoStudent -> {
                strings.add(dtoStudent.getStudentID());
            });
            cmbStuID.setItems(strings);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void chanageDateFormat() {
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
    }

    public void boxClicked(MouseEvent mouseEvent) {


    }

    public void lordTable() {
        String[] columNames = {"attendID", "date", "adminID", "StudentID", "classID"};
        TableColumn[] columns = {colAttendID, colDate, colAdminID, colStudentID, colClassID};
        for (int i = 0; i < columns.length; i++) {
            columns[i].setCellValueFactory(new PropertyValueFactory<>(columNames[i]));
        }
        colStatus.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(ToggleSwitch item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    AttendenceStuTM data = getTableView().getItems().get(getIndex());
                    if (data != null) {
                        System.out.println(data);
                        setGraphic(data.getToggleSwitch());
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });

        try {
            ObservableList<AttendenceStuTM> attendenceStuTMS = FXCollections.observableArrayList();
            attendanceStuBO.getAll().forEach(dtoAttendenceStu -> {
                attendenceStuTMS.add(new AttendenceStuTM(dtoAttendenceStu));
            });
            tableView.setItems(attendenceStuTMS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reLord() {
        btnDelete.setDisable(true);
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        try {
            attendanceID.setText(attendanceStuBO.getNewId());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        lordTable();
        clear();


    }

    public void clear() {
        cmbclassID.setValue(null);
        cmbStuID.setValue(null);
        datePicker.setValue(null);

    }

    public void tableClicked(MouseEvent mouseEvent) {
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        AttendenceStuTM attendenceStuTM = tableView.getSelectionModel().getSelectedItem();
        if (attendenceStuTM != null) {
            attendanceID.setText(attendenceStuTM.getAttendID());
            adminID.setText(attendenceStuTM.getAdminID());
            cmbStuID.setValue(attendenceStuTM.getStudentID());
            cmbclassID.setValue(attendenceStuTM.getClassID());
            datePicker.setValue(LocalDate.parse(attendenceStuTM.getDate()));
            if (attendenceStuTM.getStatus() == false) {
                setAttendLabel(false);
            } else {
                setAttendLabel(true);

            }
            try {
                System.out.println(attendenceStuTM);
                boolean b = attendanceStuBO.setAttendance(attendenceStuTM.getAttendID(), attendenceStuTM.getToggleSwitch().getSwitchedOn());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void paneClicked(MouseEvent mouseEvent) {
        reLord();

    }

    public void updateAtted(ActionEvent actionEvent) {
        {
            if (datePicker.getValue() == null || cmbStuID.getValue() == null || cmbclassID.getValue() == null) {
                Control[] controls = {datePicker, cmbStuID, cmbclassID};
                for (Control control : controls) {
                    control.setStyle(cmbclassID.getStyle() + ";-fx-border-color: #CB0404;");
                }
            } else if (chackDate(datePicker.getValue())) {
                try {
                    DtoAttendenceStu dtoAttendenceStu = new DtoAttendenceStu(attendanceID.getText(),
                            datePicker.getValue().toString(), adminID.getText(), cmbStuID.getValue(),
                            cmbclassID.getValue(), btnPresent.isSelected() ? new ToggleSwitch(true) : new ToggleSwitch(false));
                    if (attendanceStuBO.ifExitSP(dtoAttendenceStu)) {
                        boolean b = attendanceStuBO.update(new DtoAttendenceStu(attendanceID.getText(),
                                datePicker.getValue().toString(), adminID.getText(), cmbStuID.getValue(),
                                cmbclassID.getValue(), btnPresent.isSelected() ? new ToggleSwitch(true) : new ToggleSwitch(false)));
                        new Alert(Alert.AlertType.INFORMATION, b ? "Done" : "Failed").show();
                    } else {
                        new Alert(Alert.AlertType.INFORMATION, "Something went Wrong").show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }

            } else {
                datePicker.setStyle(datePicker.getStyle() + ";-fx-border-color: #CB0404;");
                new Alert(Alert.AlertType.ERROR, "Enter Date Correct !").show();
            }
            reLord();


        }


    }

    public void deleteAttend(ActionEvent actionEvent) {
        try {
            boolean result = attendanceStuBO.delete(attendanceID.getText());
            new Alert(Alert.AlertType.INFORMATION, result ? "Deleted" : "Failed").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        reLord();


    }

    public void saveAtted(ActionEvent actionEvent) {
        if (datePicker.getValue() == null || cmbStuID.getValue() == null || cmbclassID.getValue() == null) {
            Control[] controls = {datePicker, cmbStuID, cmbclassID};
            for (Control control : controls) {
                control.setStyle(cmbclassID.getStyle() + ";-fx-border-color: #CB0404;");
            }
        } else if (chackDate(datePicker.getValue())) {
            try {
                DtoAttendenceStu dtoAttendenceStu = new DtoAttendenceStu(attendanceID.getText(),
                        datePicker.getValue().toString(), adminID.getText(), cmbStuID.getValue(),
                        cmbclassID.getValue(), btnPresent.isSelected() ? new ToggleSwitch(true) : new ToggleSwitch(false));
                if (!attendanceStuBO.ifExitSP(dtoAttendenceStu)) {
                    boolean result = attendanceStuBO.save(new DtoAttendenceStu(attendanceID.getText(),
                            datePicker.getValue().toString(), adminID.getText(), cmbStuID.getValue(),
                            cmbclassID.getValue(), btnPresent.isSelected() ? new ToggleSwitch(true) : new ToggleSwitch(false)));
                    new Alert(Alert.AlertType.INFORMATION, result ? "Saved" : "Something went Wrong.. ! ").show();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Already saved.. ! ").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        } else {
            datePicker.setStyle(datePicker.getStyle() + ";-fx-border-color: #CB0404;");
            new Alert(Alert.AlertType.ERROR, "Enter Date Correct !").show();
        }
        reLord();


    }

    public void lordStudentIDs(MouseEvent mouseEvent) {
        try {
            ObservableList<String> strings = FXCollections.observableArrayList();
            ArrayList<DtoStudent> dtoStudents = studentBO.getStudentIDs();
            dtoStudents.forEach(dtoStudent -> {
                strings.add(dtoStudent.getStudentID());
            });
            cmbStuID.setItems(strings);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void lordClassIDs(MouseEvent mouseEvent) {
        try {
            ObservableList<String> strings = FXCollections.observableArrayList();
            ArrayList<DtoClass> dtoStudents = classBO.getClassIDs();
            dtoStudents.forEach(dtoClass -> {
                strings.add(dtoClass.getClassID());
            });
            cmbclassID.setItems(strings);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public boolean chackDate(LocalDate date) {
        return !date.isAfter(LocalDate.now());
    }

    public void teaSearch() {
        lordTable();
        FilteredList<AttendenceStuTM> filterDate = new FilteredList<>(tableView.getItems(), e -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterDate.setPredicate(dto -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterText = newValue.toLowerCase();
                return dto.getAttendID().toLowerCase().contains(filterText) ||
                        dto.getClassID().toString().contains(filterText) ||
                        dto.getAdminID().toLowerCase().contains(filterText) ||
                        dto.getStudentID().toLowerCase().contains(filterText) ||
                        dto.getDate().toLowerCase().contains(filterText);

            });
        });
        SortedList<AttendenceStuTM> sortedList = new SortedList<>(filterDate);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }

}





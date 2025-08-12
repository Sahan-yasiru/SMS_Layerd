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
import org.example.smslayerd.bo.custom.AttendanceTeaBO;
import org.example.smslayerd.bo.custom.ClassBO;
import org.example.smslayerd.bo.custom.TeacherBO;
import org.example.smslayerd.bo.custom.UserBO;
import org.example.smslayerd.bo.custom.impl.AttendanceTeaBOImpl;
import org.example.smslayerd.bo.custom.impl.ClassBOImpl;
import org.example.smslayerd.bo.custom.impl.TeacherBOImpl;
import org.example.smslayerd.bo.custom.impl.UserBOImpl;
import org.example.smslayerd.model.DtoAttendenceTea;
import org.example.smslayerd.view.tdm.AttendenceTeaTM;
import org.example.smslayerd.view.toggleButton.ToggleSwitch;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AttendanceTeaController implements Initializable {

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
    private ComboBox<String> cmbTeaID;
    @FXML
    private TableView<AttendenceTeaTM> tableView;
    @FXML
    private TableColumn<AttendenceTeaTM, String> colAttendID, colDate, colAdminID, colTeacherID, colClassID;
    @FXML
    private TableColumn<AttendenceTeaTM,ToggleSwitch> colStatus;
    @FXML
    private TableColumn<AttendenceTeaTM, Void> colMark = new TableColumn<>("Mark");
    private AttendanceTeaBO attendanceTeaBO= (AttendanceTeaBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.AttendanceTea);
    private UserBO userBO = (UserBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.User);
    private ClassBO classBO = (ClassBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Class);
    private TeacherBO teacherBO=(TeacherBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Teacher);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            adminID.setText(userBO.getAdminName(LoginController.getLabel()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        chanageDateFormat();
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
        ObservableList<String> observableList= FXCollections.observableArrayList();
        try {
            classBO.getClassIDs().forEach(dtoClass -> {
                observableList.add(dtoClass.getClassID());
            });
            cmbclassID.setItems(observableList);
            observableList.clear();
            teacherBO.getAll().forEach(teacherBO->{
                observableList.add(teacherBO.getTeacherID());
            });
            cmbTeaID.setItems(observableList);
            observableList.clear();
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
        String[] columNames = {"attendID", "date", "adminID", "teacherID", "classID"};
        TableColumn[] columns = {colAttendID, colDate, colAdminID, colTeacherID, colClassID};
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
                    AttendenceTeaTM data = getTableView().getItems().get(getIndex());
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
            attendanceTeaBO.getAll().forEach(teacherBO->{
                tableView.getItems().add(new AttendenceTeaTM(teacherBO));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reLord() {
        btnDelete.setDisable(true);
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        try {
            attendanceID.setText(attendanceTeaBO.getNewId());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        lordTable();
        clear();


    }

    public void clear() {
        cmbclassID.setValue(null);
        cmbTeaID.setValue(null);
        datePicker.setValue(null);

    }

    public void tableClicked(MouseEvent mouseEvent) {
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        AttendenceTeaTM AttendenceTea = tableView.getSelectionModel().getSelectedItem();
        if (AttendenceTea != null) {
            attendanceID.setText(AttendenceTea.getAttendID());
            adminID.setText(AttendenceTea.getAdminID());
            cmbTeaID.setValue(AttendenceTea.getTeacherID());
            cmbclassID.setValue(AttendenceTea.getClassID());
            datePicker.setValue(LocalDate.parse(AttendenceTea.getDate()));
            if (AttendenceTea.getStatus() == false) {
                setAttendLabel(false);
            } else {
                setAttendLabel(true);

            }
            try {
                System.out.println(AttendenceTea);
                attendanceTeaBO.setAttendance(AttendenceTea.getAttendID(), AttendenceTea.getToggleSwitch().getSwitchedOn());
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
            if (datePicker.getValue() == null || cmbTeaID.getValue() == null || cmbclassID.getValue() == null) {
                Control[] controls = {datePicker, cmbTeaID, cmbclassID};
                for (Control control : controls) {
                    control.setStyle(cmbclassID.getStyle() + ";-fx-border-color: #CB0404;");
                }
            } else if (chackDate(datePicker.getValue())) {
                try {
                    boolean result = attendanceTeaBO.update(new DtoAttendenceTea(attendanceID.getText(),
                            datePicker.getValue().toString(), adminID.getText(), cmbTeaID.getValue(),
                            btnPresent.isSelected() ? new ToggleSwitch(true) : new ToggleSwitch(false),cmbclassID.getValue() ));
                    new Alert(Alert.AlertType.INFORMATION, result?"Done":"something went wrong..!").show();

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
            boolean result = attendanceTeaBO.delete(attendanceID.getText());
            new Alert(Alert.AlertType.INFORMATION, result?"deleted":"Failed").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        reLord();


    }

    public void saveAtted(ActionEvent actionEvent) {
        if (datePicker.getValue() == null || cmbTeaID.getValue() == null || cmbclassID.getValue() == null) {
            Control[] controls = {datePicker, cmbTeaID, cmbclassID};
            for (Control control : controls) {
                control.setStyle(cmbclassID.getStyle() + ";-fx-border-color: #CB0404;");
            }
        } else if (chackDate(datePicker.getValue())) {
            try {
                boolean result = attendanceTeaBO.save(new DtoAttendenceTea(attendanceID.getText(),
                        datePicker.getValue().toString(), adminID.getText(), cmbTeaID.getValue(),
                        btnPresent.isSelected() ? new ToggleSwitch(true) : new ToggleSwitch(false),cmbclassID.getValue() ));
                new Alert(Alert.AlertType.INFORMATION, result?"Done":"something went wrong..!").show();

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

    public void lordTeacherIDs(MouseEvent mouseEvent) {
        try {
            ObservableList<String> observableList=FXCollections.observableArrayList();
            teacherBO.getAll().forEach(teacherBO->{
                observableList.add(teacherBO.getTeacherID());
            });
            cmbTeaID.setItems(observableList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void lordClassIDs(MouseEvent mouseEvent) {
        try {
            ObservableList<String> observableList=FXCollections.observableArrayList();
            classBO.getClassIDs().forEach(dtoClass -> {
                observableList.add(dtoClass.getClassID());
            });
            cmbclassID.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public boolean chackDate(LocalDate date) {
        return !date.isAfter(LocalDate.now());
    }

    public void teaSearch() {
        FilteredList<AttendenceTeaTM> filterDate = new FilteredList<>(tableView.getItems(), e -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterDate.setPredicate(dto -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filterText = newValue.toLowerCase();
                return dto.getAttendID().toLowerCase().contains(filterText) ||
                        dto.getClassID().toString().contains(filterText) ||
                        dto.getAttendID().toLowerCase().contains(filterText) ||
                        dto.getAdminID().toLowerCase().contains(filterText) ||
                        dto.getDate().toLowerCase().contains(filterText);

            });
        });
        SortedList<AttendenceTeaTM> sortedList = new SortedList<>(filterDate);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }

}





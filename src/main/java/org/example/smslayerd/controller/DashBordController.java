package org.example.smslayerd.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.example.smslayerd.bo.BOFactory;
import org.example.smslayerd.bo.custom.*;
import org.example.smslayerd.bo.custom.impl.*;
import org.example.smslayerd.view.tdm.TimeTableTM;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DashBordController implements Initializable {

    @FXML
    private Label lblDate;
    @FXML
    private Label txtNumStu;
    @FXML
    private Label txtNumTea;
    @FXML
    private Label txtNumSub;
    @FXML
    private Label txtNumClass;
    @FXML
    private TableView<TimeTableTM> tableView;
    @FXML
    private TableColumn<TimeTableTM,String> colDate;
    @FXML
    private TableColumn<TimeTableTM,String> colSubject;
    @FXML
    private TableColumn<TimeTableTM,String> colStartTime;
    @FXML
    private TableColumn<TimeTableTM,String> colEndTime;
    @FXML
    private  ImageView imageView;
    private ClassBO classBO= (ClassBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Class);
    private StudentBO studentBO=(StudentBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Student);
    private TeacherBO teacherBO=(TeacherBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Teacher);
    private SubjectBO subjectBO=(SubjectBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Subject);
    private TimeTableBO timeTableBO=(TimeTableBO) BOFactory.getInstance().getBOType(BOFactory.BOTypes.TimeTable);





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtNumStu.setText(studentBO.getNumber());
            txtNumSub.setText(subjectBO.getNumber());
            txtNumTea.setText(teacherBO.getNumber());
            txtNumClass.setText(classBO.getNumber());
            lblDate.setText(getDate());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            e.printStackTrace();
        }
        lordTable();

    }
    public String getDate(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        return simpleDateFormat.format(date);
    }
    public void lordTable(){
        colDate.setCellValueFactory(new PropertyValueFactory<>("dayOfWeek"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        try {
            ObservableList<TimeTableTM> dtoTimeTables = FXCollections.observableArrayList();
            timeTableBO.getAllByDate().forEach(timeTableDto ->{
                dtoTimeTables.add(new TimeTableTM(timeTableDto.getTimeTableID(),timeTableDto.getSubjectID(),timeTableDto.getStartTime(),
                        timeTableDto.getEndTime(),timeTableDto.getDayOfWeek(),timeTableDto.getSubjectName()));
            });
            tableView.setItems(dtoTimeTables);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage());
        }
    }
}

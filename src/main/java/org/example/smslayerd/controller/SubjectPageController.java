package org.example.smslayerd.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.example.smslayerd.bo.BOFactory;
import org.example.smslayerd.bo.custom.impl.SubjectBOImpl;
import org.example.smslayerd.model.DtoSubject;
import org.example.smslayerd.view.tdm.SubjectTM;

import java.net.URL;
import java.util.ResourceBundle;

public class SubjectPageController implements Initializable {
    @FXML
    private TableView<SubjectTM> tableView;
    @FXML
    private TableColumn<SubjectTM,String> colSubjectID;
    @FXML
    private TableColumn<SubjectTM,String> colSubName;
    @FXML
    private Label lblNumber;
    @FXML
    private Label lblSubID;
    @FXML
    private TextField txtSubName;
    @FXML
    private Button saveButton;
    @FXML
    private Button UpdateButton;
    @FXML
    private Button DeleteBtn;

    @FXML
    private  TextField SearchBar;

    private SubjectBOImpl subjectBO=(SubjectBOImpl) BOFactory.getInstance().getBOType(BOFactory.BOTypes.Subject);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reLode();


    }
    public void getID(){
        try {
            lblSubID.setText(subjectBO.getNewId());
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void btnSave(ActionEvent actionEvent) {
        if(lblSubID.getText().isEmpty()||txtSubName.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Records are Empty").show();
            return;
        }
        try {
            boolean result=subjectBO.save(new DtoSubject(lblSubID.getText(),txtSubName.getText()));
            reLode();
            new Alert(Alert.AlertType.INFORMATION,result?"saved":"failed").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }
    public void btnUpdate(ActionEvent actionEvent){
        if(lblSubID.getText().isEmpty()||txtSubName.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Records are Empty").show();
            return;
        }
        try {
            boolean result=subjectBO.update(new DtoSubject(lblSubID.getText(),txtSubName.getText()));
            reLode();
            new Alert(Alert.AlertType.INFORMATION,result?"Updated":"Failed").show();

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void btnDelete(ActionEvent actionEvent) {
        try {
            boolean result=subjectBO.delete(lblSubID.getText());
            reLode();
            new Alert(Alert.AlertType.INFORMATION,result?"Deleted":"Failed").show();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    public void lordTable(){
        colSubjectID.setCellValueFactory(new PropertyValueFactory<>("subjectID"));
        colSubName.setCellValueFactory(new PropertyValueFactory<>("Name"));

        try {
            ObservableList<SubjectTM> subjectTMS= FXCollections.observableArrayList();
            subjectBO.getAll().forEach(subjectdto ->{
                subjectTMS.add(new SubjectTM(subjectdto.getSubjectID(),subjectdto.getName()));
            });

            tableView.setItems(subjectTMS);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    public void reLode(){
        lordTable();
        clear();
        getID();
        setNumber();
        saveButton.setDisable(false);
        UpdateButton.setDisable(true);
        DeleteBtn.setDisable(true);

    }
    public void clear(){
        txtSubName.clear();
        SearchBar.clear();

    }
    public void setNumber(){
        try {
            String result=subjectBO.getNumber();
            lblNumber.setText(result);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void reFreshTable(MouseEvent mouseEvent) {
        reLode();
    }

    public void searchSubject(KeyEvent keyEvent) {
        try {
            DtoSubject dtoSubject=subjectBO.search(SearchBar.getText());
            tableView.getItems().clear();
            tableView.getItems().add(new SubjectTM(dtoSubject.getSubjectID(),dtoSubject.getName()));
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    public void tableClicked(MouseEvent mouseEvent) {
        SubjectTM subjectTM =tableView.getSelectionModel().getSelectedItem();

        if(subjectTM !=null){
            saveButton.setDisable(true);
            UpdateButton.setDisable(false);
            DeleteBtn.setDisable(false);
            lblSubID.setText(subjectTM.getSubjectID());
            txtSubName.setText(subjectTM.getName());
        }


    }
}

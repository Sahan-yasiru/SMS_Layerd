module org.example.smslayerd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;


    opens org.example.smslayerd.controller to javafx.fxml;
    opens org.example.smslayerd.view.tdm to javafx.base;
    exports org.example.smslayerd;


}
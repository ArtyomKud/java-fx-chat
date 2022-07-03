module com.example.javafxchat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxchat.Client to javafx.fxml;
    exports com.example.javafxchat.Client;


}
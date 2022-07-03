package com.example.javafxchat.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Optional;


public class JavaChatController {

    @FXML
    private HBox authBox;

    @FXML
    private AnchorPane msgBox;


    @FXML
    private TextField login;

    @FXML
    private TextField password;


    @FXML
    private TextArea mainTextArea;



    @FXML
    private TextField mainTextField;

    private final JavaChatClient client;

    public JavaChatController() {
        this.client = new JavaChatClient(this);
        while (true) {
            try {
                client.openConnect();
                break;
            } catch (IOException e){
                sowNotification();
            }

        }
    }

    private void sowNotification() {
        final Alert alert = new Alert(Alert.AlertType.ERROR,
                "Не могу подключится к серверу.\n" +
                        "Проверьте, что сервер запущен и доступен",
                new ButtonType("Попробовать снова", ButtonBar.ButtonData.OK_DONE),
                new ButtonType("Выйти", ButtonBar.ButtonData.CANCEL_CLOSE)
        );
        alert.setTitle("Ошибка подключения!");
        final Optional<ButtonType> answer = alert.showAndWait();
        final Boolean isExit = answer
                .map(select -> select.getButtonData().isCancelButton())
                .orElse(false);
        if (isExit) {
            System.exit(0);
        }

    }

    @FXML
    public void ButtonClickSendText() throws IOException {
        client.sendMessage();
        mainTextField.clear();
    }



    public String getLogin() {
        String loginTXT =login.getText();
        return loginTXT;
    }

    public String getPassword() {
       String passwordTXT = password.getText();
       return  passwordTXT;
    }



    public void ButtonClickAuth(ActionEvent actionEvent) {
        client.performAuth();


    }

    public void setAuthorized (Boolean b){
        authBox.setVisible(!b);
        msgBox.setVisible(b);
    }

    public void printText(String strFromServer) throws IOException {
        String str = strFromServer;
        String str1 = client.readMsg(str);
        mainTextArea.appendText(str1+"\n");

    }

    public String outText(){
        String outStr = mainTextField.getText();
        return outStr;
    }
    public Boolean authBoxStatus (){
        Boolean status = authBox.isVisible();
        return status;
    }


}
package com.example.javafxchat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void ButtonClickAddText() {
        mainTextArea.appendText(mainTextField.getText()+"\n");
        mainTextField.clear();
        //mainTextArea.insertText(0,mainTextField.getText()+"\n");
        //mainTextField.clear();


    }
    @FXML
    TextArea mainTextArea;

    @FXML
    TextField mainTextField;

}
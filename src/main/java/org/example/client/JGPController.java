package org.example.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class JGPController {

    @FXML
    private TextField CodeTF;

    @FXML
    private Button JoinButton;

    public Button getJoinButton() {
        return JoinButton;
    }

    public TextField getCodeTF() {
        return CodeTF;
    }
}

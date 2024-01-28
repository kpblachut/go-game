package org.example.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LGPController {

    @FXML
    private Label FNLabel;

    @FXML
    private Button LoadButton;

    @FXML
    private Button OPEButton;

    public Button getLoadButton() {
        return LoadButton;
    }

    public Button getOPEButton() {
        return OPEButton;
    }

    public Label getFNLabel() {
        return FNLabel;
    }
}

package org.example.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GEPController {

    @FXML
    private Button OksButon;

    @FXML
    private Label bPoints;

    @FXML
    private Label wPoints;

    public Label getwPoints() {
        return wPoints;
    }

    public Label getbPoints() {
        return bPoints;
    }

    public Button getOksButon() {
        return OksButon;
    }
}

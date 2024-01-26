package org.example.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

public class LayOutController {

    @FXML
    private Button ChangeTurnButton;

    @FXML
    private ScrollPane ChatArea;

    @FXML
    private VBox ChatMessages;

    @FXML
    private TextField ChatTextField;

    @FXML
    private AnchorPane GamePlace;

    @FXML
    private Button SendTextButton;

    @FXML
    private Label TurnLabel;

    public Button getChangeTurnButton(){
        return ChangeTurnButton;
    }

    public VBox getChatMessages(){
        return ChatMessages;
    }

    public TextField getChatTextField(){
        return ChatTextField;
    }

    public AnchorPane getGamePlace(){
        return GamePlace;
    }

    public Button getSendTextButton(){
        return SendTextButton;
    }

    public ScrollPane getChatArea(){
        return ChatArea;
    }

    public Label getTurnLabel(){
        return TurnLabel;
    }

}

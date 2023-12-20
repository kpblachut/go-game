package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.client.MyClient;

public class FirstScreenController {
    ObservableList<Integer> choices = FXCollections.observableArrayList(9,15,19);

    @FXML
    private Button JoinButton;

    @FXML
    private TextField LobbyCodeField;

    @FXML
    private TextField LobbyIDField;

    @FXML
    private Button joinButton;

    @FXML
    private TextField nicknameField;

    @FXML
    private ChoiceBox<Integer> SizeCheckBox;


    //Metoda po przycisnieciu przycisku
    @FXML
    void StartNewLobby(ActionEvent event) throws Exception{
        String nick = nicknameField.getText();
        MyClient newClient = new MyClient(nick, SizeCheckBox.getValue().intValue());
        Parent root = newClient;

        Stage newStage = new Stage();
        newStage.setTitle(nick);
        newStage.setScene(new Scene(root));
        newStage.show();
        newClient.myRun();
    }

    @FXML
    void JoinLobby(ActionEvent event) {
        String nick = nicknameField.getText();
        String Lobby = LobbyIDField.getText();
        String LobbyCode = LobbyCodeField.getText();

        //MyClient newClient = new MyClient(nick, SizeCheckBox.getValue());
        //Parent root = newClient;

        //Stage newStage = new Stage();
        //newStage.setTitle(nick);
        //newStage.setScene(new Scene(root));
        //newStage.show();
        //newClient.myRun();
    }

    @FXML
    void initialize() {
        SizeCheckBox.setItems(choices);
    }

}

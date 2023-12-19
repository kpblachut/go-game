package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.client.MyClient;

public class FirstScreenController {

    @FXML
    private Button joinButton;

    @FXML
    private TextField nicknameField;

    //Metoda po przycisnieciu przycisku
    @FXML
    void JoinWIthTheNickname(ActionEvent event) {
        String nick = nicknameField.getText();
        MyClient newClient = new MyClient(nick);
        Parent root = newClient;

        Stage newStage = new Stage();
        newStage.setTitle(nick);
        newStage.setScene(new Scene(root));
        newStage.show();
        newClient.myRun();
    }

}

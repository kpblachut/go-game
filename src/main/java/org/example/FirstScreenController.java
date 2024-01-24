package org.example;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.client.LayOutController;
import org.example.client.MyClient;

public class FirstScreenController {
    ObservableList<Integer> choices = FXCollections.observableArrayList(9,13,19);

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
    void StartNewLobby(ActionEvent event) throws Exception {
        String nick = nicknameField.getText();
        try {
            MyClient newClient = new MyClient(nick, SizeCheckBox.getValue().intValue());

            //Nowe
            FXMLLoader fxmlLoader = new FXMLLoader(FirstScreenController.class.getResource("/LayOut.fxml"));
            newClient.setScene(new Scene(fxmlLoader.load()));

            LayOutController controller = fxmlLoader.getController();
            newClient.setController(controller);

            //Zamykanie
            Stage biezaceOkno = (Stage) ((Node) JoinButton).getScene().getWindow();

            newClient.setStage(biezaceOkno);
            newClient.myRun();
            biezaceOkno.setScene(newClient.getScene());

            //biezaceOkno.sizeToScene();

            biezaceOkno.setOnCloseRequest(e ->{
                newClient.getThread().interrupt();
                Platform.exit();
                System.exit(0);
            });

        } catch (Exception e) {
            System.out.println("hihi blad");
            e.printStackTrace();
        }
    }

    @FXML
    void JoinLobby(ActionEvent event) {
        String nick = nicknameField.getText();
        String Lobby = LobbyIDField.getText();
        String LobbyCode = LobbyCodeField.getText();

        try {
            MyClient newClient = new MyClient(nick, Lobby, LobbyCode);

            //Nowe
            FXMLLoader fxmlLoader = new FXMLLoader(FirstScreenController.class.getResource("/LayOut.fxml"));
            newClient.setScene(new Scene(fxmlLoader.load(), 500, 500));

            LayOutController controller = fxmlLoader.getController();
            newClient.setController(controller);

            //Zamykanie
            Stage biezaceOkno = (Stage) ((Node) JoinButton).getScene().getWindow();

            newClient.setStage(biezaceOkno);
            newClient.myRun();
            biezaceOkno.setScene(newClient.getScene());

            biezaceOkno.sizeToScene();

            biezaceOkno.setOnCloseRequest(e ->{
                newClient.getThread().interrupt();
                System.out.println("Watek interrupt");
                Platform.exit();
                System.exit(0);
            });

        } catch (Exception e) {
            System.out.println("hihi blad");
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        SizeCheckBox.setItems(choices);
    }

}

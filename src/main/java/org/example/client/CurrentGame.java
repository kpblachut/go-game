package org.example.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.example.NewController;

import java.io.IOException;
import java.io.PrintWriter;

public class CurrentGame {

    Client client;
    NewController controller;

    PrintWriter outputWriter;
    public CurrentGame(Client client, NewController controller){
        this.client = client;
        this.controller = controller;
        controller.getNewGameItem().setOnAction(this::showNGPopUp);
        controller.getJoinGameItem().setOnAction(this::showJGPopUp);
    }

    public void HandleInput(Object input) {

    }

    private void sendOutput(String response){
        outputWriter.println(response);
    }

    public void setOutputWriter(PrintWriter outputWriter){
        this.outputWriter = outputWriter;
    }

    private void showNGPopUp(ActionEvent event) {
        try {
            Stage popek = new Stage();
            FXMLLoader loder = new FXMLLoader(getClass().getResource("/NGPopup.fxml"));
            AnchorPane popupLayout = loder.load();
            NGPController popControl = loder.getController();
            Scene dialogScene = new Scene(popupLayout);
            popek.setTitle("Start new game");
            popek.setScene(dialogScene);
            /*TODO
            Tutaj dodac obsluge wysylania nowej gry z popupa
            + jeszcze zeby popup sie zamykał kiedy sie juz wlaczy nowa gre
            */
            Platform.runLater(()->{popek.show();});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showJGPopUp(ActionEvent event) {
        try {
            Stage popek = new Stage();
            FXMLLoader loder = new FXMLLoader(getClass().getResource("/JGPopup.fxml"));
            AnchorPane popupLayout = loder.load();
            JGPController popControl = loder.getController();
            Scene dialogScene = new Scene(popupLayout);
            popek.setTitle("Join game");
            popek.setScene(dialogScene);
            /*TODO
            Tutaj dodac obsluge dolaczania gry z popupa
            + jeszcze zeby popup sie zamykał kiedy sie juz wlaczy nowa gre
            */
            Platform.runLater(()->{popek.show();});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package org.example.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.NewController;
import org.example.SObject;
import org.example.client.exceptions.WrongSizeOfBoardException;

import java.io.IOException;
import java.io.PrintWriter;

public class CurrentGame {

    private boolean myTurn;
    Client client;
    NewController controller;

    GameBoard goban;

    PrintWriter outputWriter;
    public CurrentGame(Client client, NewController controller){
        this.client = client;
        this.controller = controller;
        controller.getNewGameItem().setOnAction(this::showNGPopUp);
        controller.getJoinGameItem().setOnAction(this::showJGPopUp);
        controller.getPlayWBItem().setOnAction(this::showPWBPopUp);
        controller.getPassTurnItem().setOnAction(this::passTurn);
        controller.getGiveUpItem().setOnAction(this::giveUp);
        controller.getSaveGameItem().setOnAction(this::saveGame);
        controller.getQuitItem().setOnAction(this::quit);
        controller.getLoadGameItem().setOnAction(this::LoadGame);
        goban = client.getGoban();
    }

    public void handleInput(Object input) {
        /*TODO

         */

        // Mniej wiecej cos takiego
        SObject ip;

        if (input instanceof SObject) {
            ip = (SObject) input;
            try {
                goban.updateBoard(ip.board);
                myTurn = ip.yourTurn;
            } catch (WrongSizeOfBoardException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendOutput(String response){
        client.send(response);
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
            popControl.getCGButton().setOnAction(e -> NewGame(e,popControl.getSizeChoice().getValue()));
            /*TODO
            Tutaj dodac obsluge wysylania nowej gry z popupa
            + jeszcze zeby popup sie zamykał kiedy sie juz wlaczy nowa gre
            */
            Platform.runLater(popek::show);
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

            popControl.getJoinButton().setOnAction(e -> JoinGame(e,popControl.getCodeTF().getText()));
            /*TODO
            Tutaj dodac obsluge dolaczania gry z popupa
            + jeszcze zeby popup sie zamykał kiedy sie juz wlaczy nowa gre
            */
            Platform.runLater(popek::show);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPWBPopUp(ActionEvent event) {
        try {
            Stage popek = new Stage();
            FXMLLoader loder = new FXMLLoader(getClass().getResource("/NGPopup.fxml"));
            AnchorPane popupLayout = loder.load();
            NGPController popControl = loder.getController();
            Scene dialogScene = new Scene(popupLayout);
            popek.setTitle("Play with bot");
            popek.setScene(dialogScene);
            /*TODO
            Tutaj dodac obsluge wysylania nowej gry z popupa
            + jeszcze zeby popup sie zamykał kiedy sie juz wlaczy nowa gre
            */
            Platform.runLater(popek::show);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void passTurn(ActionEvent event) {
        System.out.println("Passing my turn...");
        /*TODO

         */
    }

    private void giveUp(ActionEvent event) {
        System.out.println("Giving up...");
        /*TODO

         */
    }

    private void saveGame(ActionEvent event) {
        System.out.println("Saving game...");
        /*TODO

         */
    }

    private void quit(ActionEvent event) {
        System.out.println("Quitting game...");
        /*TODO

         */
    }

    private void NewGame(ActionEvent e, int size) {
        System.out.println("Starting new game, size: " + size);
        /*TODO

         */
    }

    private void JoinGame(ActionEvent e, String code) {
        System.out.println("Joining game with code: " + code);
        /*TODO

         */
    }

    private void NewGameWB(ActionEvent e, int size){
        System.out.println("Starting new game with bot, size: " + size);
        /*TODO

         */
    }

    private void LoadGame(ActionEvent event){
        System.out.println("Loading game...");
        //TODO
    }
}

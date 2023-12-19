package org.example.client;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static javafx.scene.layout.StackPane.setAlignment;

public class MyClient extends StackPane {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    String lobby, code;

    BufferedReader inputReader;
    PrintWriter outputWriter;

    String nickname;

    public MyClient(String nickname) {
        this.nickname = nickname;
        StackPane pain = new StackPane();
        Button dupon = new Button("CHANGE_TURN");
        dupon.setOnMouseClicked(this::changeTurn);
        this.getChildren().add(dupon);
        setAlignment(dupon, Pos.CENTER);
    }

    public void myRun() {
        try{
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputWriter = new PrintWriter(socket.getOutputStream(), true);
            outputWriter.println(nickname); // Podanie serwerowi nicku

            // Testowanie
            if(lobby == null || code == null)
                outputWriter.println("CREATE_LOBBY");
            else
                outputWriter.println("JOIN_LOBBY " + lobby + " " + code);

        } catch (IOException e){
            System.out.println("nie udalo sie");
        }
    }

    private void changeTurn(MouseEvent mouseEvent) {
        outputWriter.println("CHANGE_TURN");
    }

    static class ClientListener implements Runnable {
        private BufferedReader reader;

        public ClientListener(BufferedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            try {
                String serverMessage;
                while ((serverMessage = reader.readLine()) != null) {
                    System.out.println(serverMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLobbyAndCode(String lobby, String code) {
        this.lobby = lobby;
        this.code = code;
    }

}


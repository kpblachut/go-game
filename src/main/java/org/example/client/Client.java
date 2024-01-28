package org.example.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.NewController;

import java.io.*;
import java.net.Socket;
import java.util.zip.GZIPOutputStream;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    ObjectOutputStream outputWriter;

    NewController controller;
    private GameBoard goban;
    private Scene scene;
    static Client instance;

    boolean gexit = false;
    Socket sk;

    CurrentGame cg;

    /*
    public static void main(String[] args) {
        new Client().startClient();
    }
     */

    public Client(NewController controller) {
        this.instance = this;
        this.controller = controller;
        goban = new GameBoard(19);

        Platform.runLater(()->{
            controller.getGamePlace().getChildren().add(goban); //Fake board, without listeners to place stones
        });

        cg = new CurrentGame(this, controller);

        System.out.println("Client initialised");
    }

    public void startClient() {
        try {
            System.out.println("starting client");
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            sk = socket;
            //BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //outputWriter = new PrintWriter(socket.getOutputStream(), true);
            outputWriter = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            /*
            // Set up client's nickname
            System.out.print("Enter your nickname: ");
            String nickname = new BufferedReader(new InputStreamReader(System.in)).readLine();
            outputWriter.println(nickname);

            // Menu to choose between creating and joining a lobby
            System.out.println("Choose an option:");
            System.out.println("1. Create a lobby");
            System.out.println("2. Join a lobby");

            int choice = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());

            if (choice == 1) {
                outputWriter.println("CREATE_LOBBY");
            } else if (choice == 2) {
                System.out.print("Enter the lobby name to join: ");
                String lobbyToJoin = new BufferedReader(new InputStreamReader(System.in)).readLine();

                // For simplicity, the lobby code is assumed to be the same as the lobby name
                System.out.print("Enter the lobby code: ");
                String lobbyCode = new BufferedReader(new InputStreamReader(System.in)).readLine();

                outputWriter.println("JOIN_LOBBY " + lobbyToJoin + " " + lobbyCode);
            } else {
                System.out.println("Invalid choice. Exiting...");
                socket.close();
                return;
            }

            // Start a thread to listen for server messages*/

            new Thread(new ClientListener(ois, instance)).start();

            // Send messages to the server
//            while (!gexit) {
////                System.out.print("Enter message (Type 'exit' to close): ");
////                String message = new BufferedReader(new InputStreamReader(System.in)).readLine();
////                //outputWriter.println(message);
////
////                if ("exit".equalsIgnoreCase(message)) {
////                    break;
////                }
//            }
//            System.out.println("closing socket");
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientListener implements Runnable {

        Client instance;
        private BufferedReader reader;
        private ObjectInputStream ois;

        public ClientListener(/*BufferedReader reader*/ ObjectInputStream ois, Client instance) {
            //this.reader = reader;
            this.ois = ois;
            this.instance = instance;
            System.out.println("Client listener initialised");
        }

        @Override
        public void run() {
            try {
                //String serverMessage;
                System.out.println("listening for objects");
                Object serverObject;
                System.out.println("sth");
                while ((serverObject = ois.readObject()) != null) {
                    if(serverObject instanceof Response){
                        Response so = (Response) serverObject;
                        System.out.println(so.getBoard());
                        System.out.println("getting object from server");
                        instance.handle(so);
                    } else {continue;}
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public GameBoard getGoban() {
        return goban;
    }

    public void handle(Response serverObject) {
        System.out.println("handling object from server");
        cg.handleInput(serverObject);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene(){
        return scene;
    }

    public void send(Object o) {
        if(outputWriter != null) {
            try {
                System.out.println("sending object to server");
                outputWriter.writeObject((Object) o);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public void setLobbyId(int lobbyId) {
        System.out.println("Changing name...");
        Platform.runLater(()->{((Stage) scene.getWindow()).setTitle("Go Game - Lobby: " + lobbyId);});
    }

    public void quit(){
        System.out.println("Quitting");
        gexit = true;
        try{sk.close();}catch(IOException e){e.printStackTrace();}
    }
}

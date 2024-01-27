/*
package org.example.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static javafx.scene.layout.StackPane.setAlignment;
*/
/*
public class MyClient {   //Zmiana StackPane na Scene
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    Thread thread;

    boolean myTurn;
    StoneType myType;
    String lobby, code;

    static MyClient instance;

    BufferedReader inputReader;
    PrintWriter outputWriter;

    String nickname;

    GameBoard gb;

    int size;

    //NOWE RZECZY
    Stage stage;
    Scene scene;
    LayOutController MyController;
    AnchorPane GP;
    Button SB;
    Button CTB;
    TextField Chat;
    ScrollPane ChatArea;
    VBox chatMessages;

    Label turn;

    public MyClient(String nickname, int size) {
        instance = this; // To sie wydaje mega niestosowne
        this.nickname = nickname;
        //StackPane pain = new StackPane();
        this.size = size;
    }
    public MyClient(String nickname, String lobby, String code) {
        instance = this; // To sie wydaje mega niestosowne
        this.nickname = nickname;
        this.lobby = lobby;
        this.code = code;
    }


    public void myRun() {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputWriter = new PrintWriter(socket.getOutputStream(), true);
            outputWriter.println(nickname); // Podanie serwerowi nicku

            // Testowanie
            if(lobby == null || code == null) {
                outputWriter.println("CREATE_LOBBY " + size);
            } else if(lobby != null && code != null) {
                outputWriter.println("JOIN_LOBBY " + lobby + " " + code);
            } else {
                System.out.println("Invalid choice. Exiting...");
                socket.close();
                return;
            }

            turn.setText("NOT SET");

            thread = new Thread(new ClientListener(inputReader));
            thread.start();

        } catch (IOException e){
            System.out.println("nie udalo sie");
        }
    }

    private void changeTurn(MouseEvent mouseEvent) {
        outputWriter.println("CHANGE_TURN " + myType.toString());
    }

    public static MyClient getInstance() {
        return instance;
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
                    MyClient.getInstance().handleCommand(serverMessage);
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

    public void setTurn(boolean turn) {
        this.myTurn = turn;
    }

    public void changeMyTurn(){
        myTurn = !myTurn;
    }
    public void setColor(StoneType st){
        this.myType = st;
    }

    public void putStone(StoneType tp, int x, int y) {
        gb.getCrossings()[x][y].PlaceStone(new Stone(tp));
    }

    public void removeStone(int x, int y) {
        gb.getCrossings()[x][y].rmStone();
    }

    public void handleCommand(String serverMessage){
        if(serverMessage.equals("CH_TURN")){    // Niepotrzebne raczej, bo tutaj odrazu po kliknieciu zmieniamy lokalnie runde
            myTurn = !myTurn;                   // tak aby zabezpieczyc się przed ewentualnym lagiem ze strony serwera
        } else if(serverMessage.equals("Y_TURN")){ // Your Turn
            myTurn = true;
            Platform.runLater(() -> {
                turn.setText("YOURS");
            });
        } else if(serverMessage.equals("NY_TURN")){ // Not Your Turn
            myTurn = false;
            Platform.runLater(() -> {
                turn.setText("THEIRS");
            });
        } else if(serverMessage.split(" ")[0].equals("PUT")){ // Place
            StoneType st;
            if(serverMessage.split(" ")[1].equals("WHITE")){
                st = StoneType.WHITE;
            } else {
                st = StoneType.BLACK;
            }
            putStone(st , Integer.parseInt(serverMessage.split(" ")[2]),Integer.parseInt(serverMessage.split(" ")[3]));
        } else if(serverMessage.split(" ")[0].equals("RM")){
            removeStone(Integer.parseInt(serverMessage.split(" ")[1]), Integer.parseInt(serverMessage.split(" ")[2]));
        } else if(serverMessage.split(" ")[0].equals("INIT")){ //Zmiana Do inicjalizacji z serwera
            int size = Integer.parseInt(serverMessage.split(" ")[2]);
            myTurn = false;
            setColor((serverMessage.split(" ")[1].equals("B")) ? StoneType.BLACK : StoneType.WHITE);

            gb = new GameBoard(size);
            //gb.initialise();
            //System.out.println("gb initialised");
            AddEventHandlers(gb.getChildren());
            //System.out.println("Added EventHandlers to children");
            gb.setColor(serverMessage.split(" ")[1]);
            Platform.runLater(() -> {
                //this.getChildren().add(gb);
                GP.getChildren().add(gb);

                setAlignment(GP.getChildren().get(0), Pos.CENTER);
            });
            //System.out.println("board placed");
        } else {
            Platform.runLater(() -> {
                chatMessages.getChildren().add(new Label(serverMessage));
            });
        }
    }

    private void AddEventHandlers(ObservableList<Node> s) { // Tu podmienie potem na gb.getCrossings() powinno dzialac
        for (Node node : s){
            try {
                ((Spot) node).setOnMouseClicked(this::handleSpotMouseClick);
            } catch (Exception e){
                System.out.println("Nie spot :(");
                continue;
            }
        }
    }
    private void handleSpotMouseClick(MouseEvent event) {
        if (event.getSource() instanceof Spot clickedSpot && myTurn) { //tutaj nie jestem dumny z tego instanceof, mozna go podmienic na try catcha
            if(!clickedSpot.hasStone()){
                outputWriter.println(myType.toString()+" "+clickedSpot.getCoords());
                changeMyTurn();
            } else {
                System.out.println("zajete pole");
            }
        } else {
            System.out.println("Czekaj na swoja kolej");
        }
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }
    public Scene getScene(){return scene;}

    public void setStage(Stage stage){
        this.stage = stage;
    }

    private void sendText(MouseEvent mouseEvent) {
        String message = nickname + ": ";
        message = message + Chat.getText();
        Chat.clear();
        outputWriter.println(message);
    }

    public void setController(LayOutController controller){
        MyController = controller;
        CTB = controller.getChangeTurnButton();
        CTB.setOnMouseClicked(this::changeTurn);
        SB = controller.getSendTextButton();
        SB.setOnMouseClicked(this::sendText);
        GP = controller.getGamePlace();
        Chat = controller.getChatTextField();
        ChatArea = controller.getChatArea();
        chatMessages = controller.getChatMessages();
        turn = controller.getTurnLabel();
    }

    public Thread getThread(){
        return thread;
    }

}
*/

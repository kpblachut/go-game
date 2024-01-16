package org.example.client;


import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class SemiLogic {
    GameBoard gb;
    boolean whiteTurn;

    public SemiLogic(int size){
        gb = new GameBoard(size);
        gb.initialise();

         // To trzeba bedzie przeniesc do MyClienta, aby moc dodawać kamyki

        whiteTurn = false; // Zaczyna czarny
    }

    private void AddEventHandlers(ObservableList<Node> s) {
        for (Node node : s){
            try {
                ((Spot) node).setOnMouseClicked(this::handleMouseClick);
            } catch (Exception e){
                System.out.println("Nie spot :(");
                continue;
            }
        }
    }

    public void ChangeTurn(int c){
        //ta funkcja jest do ustawiania odgornie
        //zakladam ze dla 0 jest czarny, dla innego jest bialy
        whiteTurn= c != 0;
    }

    public void ChangeTurn(){
        whiteTurn = !whiteTurn;
    }

    Stone place(boolean white){
        StoneType type;

        if(white)
            type = StoneType.WHITE;
        else
            type = StoneType.BLACK;

        return new Stone(type);
    }

    private void handleMouseClick(MouseEvent event) {
        if (event.getSource() instanceof Spot clickedSpot) { //tutaj nie jestem dumny z tego instanceof, mozna go podmienic na try catcha
            if(!clickedSpot.hasStone()){
                clickedSpot.PlaceStone(place(whiteTurn));
                this.ChangeTurn();
                System.out.println("jest kamien");
            } else {
                System.out.println("nie ma kamienia");
            }
        }
    }

    public GameBoard getGb() {
        return gb;
    }
}

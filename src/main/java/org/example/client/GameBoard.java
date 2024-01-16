package org.example.client;


import javafx.scene.layout.GridPane;

public class GameBoard extends GridPane {

    //przeciecia
    private Spot[][] crossings;
    int size;

    GameBoard(int size) {
        super();
        this.size = size;
    }

    public void initialise(){
        crossings = new Spot[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                // Wciskamy na chama do tabeli podwójnej wszystkie przecięcia
                crossings[i][j] = new Spot();
                this.add(crossings[i][j], i, j);
                crossings[i][j].setCoords(i,j); // Aby Spot wiedział którym jest
            }
        }
    }

    public Spot[][] getCrossings() {
        return crossings;
    }

    public void setCrossings(Spot[][] crossings) {
        this.crossings = crossings;
    }
}

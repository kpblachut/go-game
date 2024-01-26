package org.example.client;


import javafx.scene.layout.GridPane;

public class GameBoard extends GridPane {

    //przeciecia
    private Intersection[][] intersections;
    int size;

    GameBoard(int size) { //cala ma byc 741x741
        super();
        this.size = size;
        intersections = new Intersection[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                // Wciskamy na chama do tabeli podwójnej wszystkie przecięcia
                intersections[i][j] = new Intersection(this, i, j);
                this.add(intersections[i][j].getField(), i, size-j); //W GridPane na odwrót są współrzędne wysokości
            }
        }
    }

    public Intersection[][] getCrossings() {
        return intersections;
    }

    public void updateBoard(char board[][]){
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                intersections[i][j].rmStone();
                if(board[i][j] == 'W' || board[i][j] == 'B') {
                    Stone stone = new Stone((board[i][j]=='W') ? StoneType.WHITE : StoneType.BLACK);
                }
            }
        }
    }

    public void setColor(String color) {
        for(Intersection[] inters : intersections) {
            for(Intersection inter : inters) {
                inter.setShadowColor((color.equals("W"))? "#d4cfcf" : "#1f1d1d");
            }
        }
    }

    public int getSize() {
        return size;
    }
}

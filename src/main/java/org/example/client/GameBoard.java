package org.example.client;


import javafx.scene.layout.GridPane;

import java.util.Objects;

public class GameBoard extends GridPane {

    private Intersection[][] intersections;
    int size;

    public GameBoard(int size) { //cala ma byc 741x741 pikseli
        super();
        this.size = size;
        intersections = new Intersection[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                intersections[i][j] = new Intersection(this, i, j);
                this.add(intersections[i][j], i, size-j); //W GridPane na odwrót są współrzędne wysokości
            }
        }
    }

    public Intersection[][] getIsecs() {
        return intersections;
    }

    public void updateBoard(Integer[][] board) {
        System.out.println("Updatuje goban!");
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                intersections[i][j].rmStone();
                if(Objects.equals(board[i][j], 1) || Objects.equals(board[i][j],2)) {
                    Stone stone = new Stone((Objects.equals(board[i][j],1)) ? StoneType.WHITE : StoneType.BLACK);
                    System.out.println("placing stone on: " + i+" "+j);
                    intersections[i][j].placeStone(stone);
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

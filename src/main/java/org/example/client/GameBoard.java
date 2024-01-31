package org.example.client;


import javafx.application.Platform;
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
            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
//                    if(intersections[i][j].hasStone() && board[i][j]==0){
//                        intersections[i][j].rmStone();}
//                    if (Objects.equals(board[i][j], 1) || Objects.equals(board[i][j], 2)) {
//                        Stone stone = new Stone((Objects.equals(board[i][j], 1)) ? StoneType.WHITE : StoneType.BLACK);
//                        System.out.println("placing stone on: " + i + " " + j);
//                        intersections[i][j].placeStone(stone);
//                    }
                    Stone st=null;
                    if(board[i][j]!=0){
                        st = new Stone((board[i][j]==1) ? StoneType.WHITE : StoneType.BLACK);
                    }
                    intersections[i][j].updateIC(st);
                }
            }
    }

    public void setColor(Integer color) {
        for(Intersection[] inters : intersections) {
            for(Intersection inter : inters) {
                inter.setShadowColor((color==1) ? "#d4cfcf" : "#1f1d1d");
            }
        }
    }

    public int getSize() {
        return size;
    }
}

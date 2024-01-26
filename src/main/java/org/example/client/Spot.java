package org.example.client;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.ObservableFaceArray;
import javafx.scene.shape.Rectangle;

public class Spot extends StackPane {
    private Stone stone;

    double size;
    int x, y; // Coordinates

    Circle shadow;
    ObservableList<Node> nodes;

    public boolean hasStone(){
        return stone != null;
    }

    public Spot(double size){
        super();
        this.size = size;
        // rozmiar pola dla kamyczka
        this.setWidth(size);
        this.setHeight(size);

        this.getChildren().add(new Rectangle(size,size, Color.valueOf("#524631"))); // #524631


        this.getChildren().add(new Rectangle(0.483*size,0.483*size, Color.valueOf("#ded9b6"))); // #ded9b6
        this.getChildren().add(new Rectangle(0.483*size,0.483*size, Color.valueOf("#ded9b6")));
        this.getChildren().add(new Rectangle(0.483*size,0.483*size, Color.valueOf("#ded9b6")));
        this.getChildren().add(new Rectangle(0.483*size,0.483*size, Color.valueOf("#ded9b6")));

        setAlignment(this.getChildren().get(0), javafx.geometry.Pos.CENTER);
        setAlignment(this.getChildren().get(1), Pos.TOP_LEFT);
        setAlignment(this.getChildren().get(2), Pos.TOP_RIGHT);
        setAlignment(this.getChildren().get(3), Pos.BOTTOM_LEFT);
        setAlignment(this.getChildren().get(4), Pos.BOTTOM_RIGHT);

        shadow = new Circle(0.4*size);
        //shadow = new Ellipse( 0.4*size,0.233*size);
        shadow.setFill(Color.valueOf("#000000"));
        shadow.setOpacity(0.9);
        shadow.setVisible(false);
        this.getChildren().add(shadow);
        //TEST

        this.addEventFilter(MouseEvent.MOUSE_ENTERED, eventHandler);
        this.addEventFilter(MouseEvent.MOUSE_EXITED, eventHandler2);
    }

    public void PlaceStone(Stone stone){
        this.stone = stone;
        stone.setScaleX(0.4*size);
        stone.setScaleY(0.4*size);
        stone.setStrokeWidth(0.1);
        Platform.runLater(() -> {
            this.getChildren().add(stone);
            shadow.setVisible(true);
        });

        stone.setCenterX(size/2);
        stone.setCenterY(size/2);
        //setMargin(shadow, new Insets(4,2,0,0));
    }
    public void rmStone() {
        this.stone = null;
    }

    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setShadowColor(String color){
        String sColor = (color.equals("W")) ? "#d4cfcf" : "#1f1d1d";
        shadow.setFill(Color.valueOf(sColor));
    }

    public String getCoords(){
        return String.valueOf(x)+" "+String.valueOf(y);
    }

    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if(stone == null) {
                shadow.setVisible(true);
            }
        }
    };

    EventHandler<MouseEvent> eventHandler2 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if(stone==null) {
                shadow.setVisible(false);
            }
        }
    };

}
package org.example.client;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;

import static javafx.scene.layout.StackPane.setAlignment;

public class Intersection {

    private static final int BW = 741; // BOARD WIDTH
    final GameBoard gb;
    final int x;
    final int y;

    Stone stone;

    StackPane field;
    Circle shadow;

    public Intersection(GameBoard gb, int x, int y) {
        this.gb = gb;
        this.x = x;
        this.y = y;

        field = new StackPane();
        BuildField(field, gb.getSize());
    }

    public StackPane getField(){
        return field;
    }

    public void PlaceStone(Stone stone){
        double size = 0.4* ((double) BW /gb.getSize());
        this.stone = stone;

        stone.setScaleY(size);
        stone.setScaleX(size);
        stone.setStrokeWidth(0.1);

        Platform.runLater(() -> {
            field.getChildren().add(stone);
            shadow.setVisible(false);
            stone.setCenterX(size/2);
            stone.setCenterY(size/2);
        });
    }

    public void rmStone(){
        if(stone != null) {
            Platform.runLater(() -> {
                field.getChildren().remove(stone);
            });
            this.stone = null;
        }
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    // Functions for building appearence only
    private void BuildField(StackPane f, int border) {
        double size = (double) BW /border;
        f.setPrefSize(size, size);

        f.getChildren().add(new Rectangle(size,size, Color.valueOf("#524631"))); // #524631
        setAlignment(f.getChildren().get(0), javafx.geometry.Pos.CENTER);

        //Squares for board look
        Rectangle luc = new Rectangle(0.483*size,0.483*size, Color.valueOf("#ded9b6"));
        Rectangle ruc = new Rectangle(0.483*size,0.483*size, Color.valueOf("#ded9b6"));
        Rectangle rlc = new Rectangle(0.483*size,0.483*size, Color.valueOf("#ded9b6"));
        Rectangle llc = new Rectangle(0.483*size,0.483*size, Color.valueOf("#ded9b6"));

        //Shadow for stones
        shadow = new Circle(0.4*size);
        shadow.setOpacity(0.9);
        shadow.setVisible(false);

        if(x == 0){llc.setHeight(0.517*size);}
        if(x == border - 1){ruc.setHeight(0.517*size);}
        if(y == 0){rlc.setWidth(0.517*size);}
        if(y == border - 1){luc.setWidth(0.517*size);}

        f.getChildren().add(luc);
        f.getChildren().add(ruc);
        f.getChildren().add(rlc);
        f.getChildren().add(llc);
        f.getChildren().add(shadow);

        f.addEventFilter(MouseEvent.MOUSE_ENTERED, eventHandler);
        f.addEventFilter(MouseEvent.MOUSE_EXITED, eventHandler2);

        setAlignment(luc, Pos.TOP_LEFT);
        setAlignment(ruc, Pos.TOP_RIGHT);
        setAlignment(llc, Pos.BOTTOM_LEFT);
        setAlignment(rlc, Pos.BOTTOM_RIGHT);
    }

    public void setShadowColor(String color){
        shadow.setFill(Color.valueOf(color));
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
            if(stone == null) {
                shadow.setVisible(false);
            }
        }
    };
}

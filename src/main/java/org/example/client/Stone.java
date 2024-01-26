package org.example.client;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Stone extends Circle {
    private StoneType type;

    public StoneType getType(){
        return type;
    }

    public Stone(StoneType type) {
        super(1);
        this.type = type;
        this.setFill(type == StoneType.BLACK ? Color.valueOf("#1f1d1d") : Color.valueOf("#d4cfcf"));
        this.setStroke(Color.BLACK);
        //this.setStrokeWidth(1);
    }

    EventHandler<MouseEvent> eventHandler2 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

        }
    };
}

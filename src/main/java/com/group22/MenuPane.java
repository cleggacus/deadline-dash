package com.group22;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MenuPane extends GridPane {
    private int count = 0;

    public MenuPane() {
        this.getStyleClass().add("menu-outer");
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
    }

    public void addItem(String name, Runnable onClick) {
        Button button = new Button(name);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) { 
                onClick.run();
            }
        });

        button.getStyleClass().add("menu-button");
        button.minWidthProperty().bind(this.widthProperty());

        this.add(button, 0, this.count);

        this.count++;
    }
    
}

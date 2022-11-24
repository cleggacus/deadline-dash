package com.group22;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * 
 * The class {@code MenuPane} is a scene layout element which extends GridPane.
 * 
 * MenuPane is used to abstract the menu into its own pane.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class MenuPane extends GridPane {
    private int count = 0;

    /**
     * Creates a MenuPane.
     */
    public MenuPane() {
        this.getStyleClass().add("menu-outer");
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
    }

    
    /** 
     * Adds a button element to the menues list.
     * @param name the text displayed on the button.
     * @param onClick determins what happens when a button is clicked
     */
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

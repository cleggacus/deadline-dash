package com.gorup22;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MenuPane extends GridPane {
    private int count = 0;

    public void addItem(String name, Runnable onClick) {
        Button button = new Button(name);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) { 
                onClick.run();
            }
        });

        this.add(button, 0, this.count);

        this.count++;
    }
    
}

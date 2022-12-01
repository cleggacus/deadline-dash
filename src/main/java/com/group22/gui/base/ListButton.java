package com.group22.gui.base;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class ListButton extends BorderPane {
    public ListButton(String text, OnClickEvent onClick, String removeText, RemoveButtonEvent onClickRemove) {
        Button b1 = new Button();
        b1.setText(text);
        b1.setOnAction(e -> onClick.run());

        Button b2 = new Button();
        b2.setText(removeText);
        b2.setOnAction(e -> onClickRemove.run(this));

        b2.getStyleClass().add("remove");

        this.setCenter(b1);
        this.setRight(b2);

        this.getStyleClass().add("list-button");
    }

    public interface RemoveButtonEvent {
        void run(ListButton listButton);
    }

    public interface OnClickEvent {
        void run();
    }
}
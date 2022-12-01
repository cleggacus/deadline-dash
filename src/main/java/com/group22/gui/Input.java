package com.group22.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

public class Input extends BorderPane {
    public Input(String placeholder, String buttonText, InputEvent onEnter) {
        Button button = new Button(buttonText);

        TextField textField= new TextField();

        textField.setPromptText(placeholder);

        textField.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                onEnter.run(textField.getText());
            }
        });

        button.setOnAction(e -> onEnter.run(textField.getText()));

        this.setCenter(textField);
        this.setRight(button);

        this.getStyleClass().add("input");
    }

    public interface InputEvent {
        void run(String s);
    }
}


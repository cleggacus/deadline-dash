package com.group22.gui.base;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

/**
 * 
 * The class {@code Input} is a text field with a submit button which extends 
 * BorderPane. 
 * 
 * @author Liam Clegg
 * @version 1.1
 */
public class Input extends BorderPane {

    /**
     * Creates an Input GUI element with placeholder, button text and an on 
     * enter event.
     * 
     * @param placeholder Prompt text for input field.
     * @param buttonText Text in submit button in input.
     * @param onEnter Called when button is clicked or the enter key is pressed.
     */
    public Input(String placeholder, String buttonText, InputEvent onEnter) {
        Button button = new Button(buttonText);

        TextField textField = new TextField();

        textField.setPromptText(placeholder);

        textField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onEnter.run(textField.getText());
            }
        });

        button.setOnAction(e -> onEnter.run(textField.getText()));

        this.setCenter(textField);
        this.setRight(button);

        this.getStyleClass().add("input");
    }

    /**
     * InputEvent intereface which calls run when input is entered.
     * Can be used by class that impliments {@code InputEvent} or 
     * a lambda function.
     */
    public interface InputEvent {
        void run(String s);
    }
}


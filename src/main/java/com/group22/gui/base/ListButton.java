package com.group22.gui.base;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 * 
 * The class {@code ListButton} is a button with a remove button to the 
 * right of it and extends BorderPane.
 * 
 * @author Liam Clegg
 * @version 1.1
 */
public class ListButton extends BorderPane {

    /**
     * Creates a {@code ListButton} with button text, on click event, 
     * remove button text and on click remove event.
     * 
     * @param text Button text.
     * @param onClick On button click event.
     * @param removeText Text in remove button.
     * @param onClickRemove On remove button click event.
     */
    public ListButton(
        String text, 
        OnClickEvent onClick, 
        String removeText, 
        RemoveButtonEvent onClickRemove) {
            
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


    /**
     * RemoveButtonEvent interface which calls run when a remove button 
     * is clicked which the ListButton that is clicked as a paramenter.
     * Can be used by class that impliments {@code ListButton} or 
     * a lambda function.
     */
    public interface RemoveButtonEvent {
        void run(ListButton listButton);
    }

    /**
     * OnClickEvent interface which calls run when a button is clicked.
     * Can be used by class that impliments {@code ListButton} or 
     * a lambda function.
     */
    public interface OnClickEvent {
        void run();
    }
}
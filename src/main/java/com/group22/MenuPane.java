package com.group22;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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
    public static int TITLE_PADDING = 10;
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

    public TextField addInput(String placeholder) {
        TextField textField = new TextField();
        BorderPane textFieldOuter = new BorderPane(textField);

        textField.setPromptText(placeholder);
        textField.getStyleClass().add("menu-input");
        textField.setFocusTraversable(false);

        textFieldOuter.maxWidthProperty().bind(this.widthProperty());

        this.add(textFieldOuter, 0, this.count);

        this.count++;

        return textField;
    }

    /**
     * Adds a title (Text) element to the menu pane on the next available row.
     * 
     * @param name
     *      The text displayed in the title item.
     */
    public void addSubTitle(String name) {
        BorderPane titleOuter = new BorderPane();

        Text title = new Text(name);
        title.getStyleClass().add("menu-subtitle");

        titleOuter.setPadding(new Insets(0, 0, 0, 0));
        titleOuter.setCenter(title);
        titleOuter.maxWidthProperty().bind(this.widthProperty());

        this.add(titleOuter, 0, this.count);

        this.count++;
    }

    /**
     * Adds a title (Text) element to the menu pane on the next available row.
     * 
     * @param name
     *      The text displayed in the title item.
     */
    public void addTitle(String name) {
        BorderPane titleOuter = new BorderPane();

        Text title = new Text(name);
        title.getStyleClass().add("menu-title");

        titleOuter.setPadding(new Insets(0, 0, TITLE_PADDING, 0));
        titleOuter.setCenter(title);
        titleOuter.maxWidthProperty().bind(this.widthProperty());

        this.add(titleOuter, 0, this.count);

        this.count++;
    }
    
    /** 
     * Adds a button element to the menus list in the next available row.
     * 
     * @param name 
     *      The text displayed on the button.
     * 
     * @param onClick 
     *      Determins what happens when a button is clicked.
     */
    public void addItem(String name, Runnable onClick) {
        Button button = new Button(name);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) { 
                onClick.run();
            }
        });

        button.getStyleClass().add("menu-button");
        button.maxWidthProperty().bind(this.widthProperty());
        button.setTextFill(TileColor.LIGHT_RED.color);

        this.add(button, 0, this.count);

        this.count++;
    }
    
}

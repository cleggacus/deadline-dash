package com.group22.gui.base;

import java.util.Iterator;

import com.group22.gui.base.Input.InputEvent;
import com.group22.gui.base.ListButton.OnClickEvent;
import com.group22.gui.base.ListButton.RemoveButtonEvent;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

/**
 * 
 * The class {@code MenuPane} extends GridPane and is an abstraction 
 * which makes it easy to make simple menus and vertical lists.
 * 
 * @author Liam Clegg
 * @version 1.1
 */
public class MenuPane extends GridPane {

    /**
     * Creates a MenuPane.
     */
    public MenuPane() {
        this.getStyleClass().add("menu-pane");
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
    }

    /**
     * Adds an Input with placeholder and onEnter event to menu.
     * 
     * @param placeholder Prompt text in input.
     * @param onEnter Called as input button click or enter pressed.
     * @return The Input element added.
     */
    public Input addInput(String placeholder, InputEvent onEnter) {
        Input input = new Input(placeholder, "+", onEnter);
        input.maxWidthProperty().bind(this.widthProperty());

        this.add(input);

        return input;
    }

    /**
     * Adds a ListButton with text onclick evenet and on remove event to menu.
     * 
     * @param name Text in button.
     * @param onClick Called when button is clicked
     * @param onClickRemove Called when remove button is clicked.
     * @return The ListButton instance that was added to the menu.
     */
    public ListButton addListButton(
        String name, 
        OnClickEvent onClick,
        RemoveButtonEvent onClickRemove) {
            
        ListButton listButton = 
            new ListButton(name, onClick, "-", onClickRemove);

        listButton.prefWidthProperty().bind(this.widthProperty());

        this.add(listButton);

        return listButton;
    }

    /**
     * Adds a Button with text and onClick event to Menu.
     * 
     * @param name Button text.
     * @param onClick Called when button is clicked.
     * @return The instance of Button that was added to menu.
     */
    public Button addButton(String name, OnClickEvent onClick) {
        Button button = new Button(name);

        button.setOnAction(e -> onClick.run());
        button.maxWidthProperty().bind(this.widthProperty());

        this.add(button);

        return button;
    }

    /**
     * Adds a paragraph style label to the Menu.
     * 
     * @param text String thats in the label element.
     * @return The Label instance that was added to menu.
     */
    public Label addParagraph(String text) {
        Label paragraph = new Label(text);

        paragraph.getStyleClass().add("paragraph");
        paragraph.maxWidthProperty().bind(this.widthProperty());
        paragraph.setWrapText(true);

        this.add(paragraph);

        return paragraph;
    }

    /**
     * Adds a small print style label to the Menu.
     * 
     * @param text String thats in the label element.
     * @return The Label instance that was added to menu.
     */
    public Label addSmallPrint(String text) {
        Label paragraph = new Label(text);

        paragraph.getStyleClass().add("small-print");
        paragraph.maxWidthProperty().bind(this.widthProperty());
        paragraph.setWrapText(true);

        this.add(paragraph);

        return paragraph;
    }

    /**
     * Adds a Heading 2 style label to the Menu.
     * 
     * @param text String thats in the label element.
     * @return The Label instance that was added to menu.
     */
    public Label addH2(String text) {
        Label h2 = new Label(text);

        h2.getStyleClass().add("h2");

        h2.maxWidthProperty().bind(this.widthProperty());

        this.add(h2);

        return h2;
    }

    /**
     * Adds a Heading 1 style label to the Menu.
     * 
     * @param text String thats in the label element.
     * @return The Label instance that was added to menu.
     */
    public Label addH1(String text) {
        Label h1 = new Label(text);

        h1.getStyleClass().add("h1");

        h1.maxWidthProperty().bind(this.widthProperty());

        this.add(h1);

        return h1;
    }

    /**
     * Adds any kind of node to the menu as a row.
     * 
     * @param node Element that is added to menu.
     */
    public void add(Node node) {
        this.add(node, 0, this.getRowCount());
    }

    /**
     * Replaces the node at index i without removing elements from menu.
     * 
     * @param node Element that will replace the current element.
     * @param i Index of the element which will be removed.
     */
    public void replace(Node node, int i) {
        if (i < 0 || i >= this.getRowCount())
            return;

        this.add(node, 0, i);
    }

    /**
     * Removes a specific node from Menu list.
     * 
     * @param node Element that is removed.
     * @return The index of the node that was removed in the list.
     */
    public int remove(Node node) {
        Iterator<Node> iterator = this.getChildren().iterator();

        int i = 0;

        while (iterator.hasNext() && node != null) {
            Node current = iterator.next();

            if (node == current) {
                iterator.remove();
                node = null;
            }

            i++;
        }

        for (Node current : this.getChildren()) {
            int row = GridPane.getRowIndex(current);

            if (row >= i) {
                GridPane.setRowIndex(current, row - 1);
            }
        }

        return i;
    }

    /**
     * Puts menu in a vertical only scroll pane.
     * 
     * @return The scroll pane which the menu pane is in.
     */
    public ScrollPane getAsScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);

        return scrollPane;
    }
}

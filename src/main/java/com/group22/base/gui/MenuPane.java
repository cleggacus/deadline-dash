package com.group22.base.gui;

import java.util.Iterator;

import com.group22.base.gui.Input.InputEvent;
import com.group22.base.gui.ListButton.OnClickEvent;
import com.group22.base.gui.ListButton.RemoveButtonEvent;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class MenuPane extends GridPane {
    public MenuPane() {
        this.getStyleClass().add("menu-pane");
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
    }

    public Input addInput(String placeholder, InputEvent onEnter) {
        Input input = new Input(placeholder, "+", onEnter);
        input.maxWidthProperty().bind(this.widthProperty());

        this.add(input);

        return input;
    }

    public ListButton addListButton(String name, OnClickEvent onClick, RemoveButtonEvent onClickRemove) {
        ListButton listButton = new ListButton(name, onClick, "-", onClickRemove);

        listButton.maxWidthProperty().bind(this.widthProperty());

        this.add(listButton);

        return listButton;
    }

    public Button addButton(String name, OnClickEvent onClick) {
        Button button = new Button(name);

        button.setOnAction(e -> onClick.run());
        button.maxWidthProperty().bind(this.widthProperty());

        this.add(button);

        return button;
    }

    public Label addH2(String text) {
        Label h2 = new Label(text);

        h2.getStyleClass().add("h2");

        h2.maxWidthProperty().bind(this.widthProperty());

        this.add(h2);

        return h2;
    }

    public Label addH1(String text) {
        Label h1 = new Label(text);

        h1.getStyleClass().add("h1");

        h1.maxWidthProperty().bind(this.widthProperty());

        this.add(h1);

        return h1;
    }

    public void add(Node node) {
        this.add(node, 0, this.getRowCount());
    }

    public void remove(Node node) {
        Iterator<Node> iterator = this.getChildren().iterator();

        int i = 0;

        while(iterator.hasNext() && node != null) {
            Node current = iterator.next();

            if(node == current) {
                iterator.remove();
                node = null;
            }

            i++;
        }

        for(Node current : this.getChildren()) {
            int row = GridPane.getRowIndex(current);

            if(row >= i) {
                GridPane.setRowIndex(current, row - 1);
            }
        }
    }

    public ScrollPane getAsScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        return scrollPane;
    }
}

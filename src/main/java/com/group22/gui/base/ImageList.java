package com.group22.gui.base;

import com.group22.gui.base.ListButton.OnClickEvent;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ImageList extends ScrollPane {
    private GridPane grid;

    public ImageList() {
        this.getStyleClass().add("image-list");

        this.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.grid = new GridPane();
        this.setContent(this.grid);

        this.grid.setHgap(40);
        this.grid.setVgap(0);
    }

    public int getLength() {
        return this.grid.getColumnCount();
    }

    public void removeImages(){
        this.grid.getChildren().clear();
    }

    public void addImage(String text, String path, OnClickEvent onClickEvent) {
        StackPane stackPane = new StackPane();
        ImageView imageView = new ImageView(path);
        BorderPane textPane = new BorderPane();
        Label label = new Label(text);

        textPane.setCenter(label);
        textPane.getStyleClass().add("text-overlay");

        imageView.fitHeightProperty().bind(this.heightProperty());
        imageView.fitWidthProperty().bind(this.widthProperty());
        imageView.setPreserveRatio(true);

        stackPane.getChildren().add(imageView);
        stackPane.getChildren().add(textPane);


        stackPane.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY) {
                onClickEvent.run();
            }
        });

        this.grid.add(stackPane, this.grid.getColumnCount(), 0);;
    }

    public void addLockedImage(String text, String path) {
        StackPane stackPane = new StackPane();
        ImageView imageView = new ImageView(path);
        BorderPane textPane = new BorderPane();
        Label label = new Label("🔒");

        textPane.setCenter(label);
        textPane.getStyleClass().add("text-overlay");

        imageView.fitHeightProperty().bind(this.heightProperty());
        imageView.fitWidthProperty().bind(this.widthProperty());
        imageView.setPreserveRatio(true);

        stackPane.getChildren().add(imageView);
        stackPane.getChildren().add(textPane);

        this.grid.add(stackPane, this.grid.getColumnCount(), 0);
    }
}

package com.group22.game.gui;

import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Playing extends StackPane {
    private GraphicsContext graphicsContext;

    public Playing(GamePane gamePane) {
        Pane canvasPane = this.createCanvasPane();
        BorderPane infoBar = this.createInfoBar();

        this.getChildren().add(canvasPane);
        this.getChildren().add(infoBar);
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    private Pane createCanvasPane() {
        Canvas canvas = new Canvas();
        Pane canvasOuter = new Pane(canvas);

        this.graphicsContext = canvas.getGraphicsContext2D();

        canvas.setCache(true);
        canvas.setCacheHint(CacheHint.SPEED);

        canvas.widthProperty().bind(canvasOuter.widthProperty());
        canvas.heightProperty().bind(canvasOuter.heightProperty());

        return canvasOuter;
    }

    private BorderPane createInfoBar() {
        BorderPane infoBar = new BorderPane();
        BorderPane innerInfoBar = new BorderPane();

        Label score = new Label();
        score.setText("SCORE: 000");

        Label time = new Label();
        time.setText("TIME: 0.00");

        Label level = new Label();
        level.setText("LEVEL: 2-2");

        HBox left = new HBox();
        left.getChildren().add(level);
        left.getStyleClass().add("info-bar-left");

        HBox right = new HBox();
        right.getChildren().add(score);
        right.getChildren().add(time);
        right.getStyleClass().add("info-bar-right");

        innerInfoBar.setRight(right);
        innerInfoBar.setLeft(left);
        innerInfoBar.getStyleClass().add("info-bar");


        infoBar.setTop(innerInfoBar);

        return infoBar;
    }
}

package com.group22.gui;

import java.text.DecimalFormat;

import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Playing extends StackPane {
    private Label timeLabel;
    private Label scoreLabel;
    private Label levelLabel;
    private BorderPane infoBar;
    private GraphicsContext graphicsContext;

    public Playing(GamePane gamePane) {
        Pane canvasPane = this.createCanvasPane();
        BorderPane infoBar = this.createInfoBar();

        this.getChildren().add(canvasPane);
        this.getChildren().add(infoBar);
    }

    public void setInfoBarPadding(double offset) {
        this.infoBar.setPadding(new Insets(0, offset, 0, offset));
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public void setGameTime(double time) {
        DecimalFormat formatter = new DecimalFormat("000");
        this.timeLabel.setText("TIME: " + formatter.format(Math.ceil(time)));
    }

    public void setGameLevel(String level) {
        this.levelLabel.setText(level);
    }

    public void setGameScore(int score) {
        DecimalFormat formatter = new DecimalFormat("000");
        this.scoreLabel.setText("SCORE: " + formatter.format(score));
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
        this.infoBar = new BorderPane();
        BorderPane innerInfoBar = new BorderPane();

        this.scoreLabel = new Label();
        this.scoreLabel.setText("SCORE: 000");

        this.timeLabel = new Label();
        this.timeLabel.setText("TIME: 0.00");

        this.levelLabel = new Label();
        this.levelLabel.setText("LEVEL: 2-2");

        HBox left = new HBox();
        left.getChildren().add(this.levelLabel);
        left.getStyleClass().add("info-bar-left");

        HBox right = new HBox();
        right.getChildren().add(this.scoreLabel);
        right.getChildren().add(this.timeLabel);
        right.getStyleClass().add("info-bar-right");

        innerInfoBar.setRight(right);
        innerInfoBar.setLeft(left);
        innerInfoBar.getStyleClass().add("info-bar");


        infoBar.setTop(innerInfoBar);

        return infoBar;
    }
}

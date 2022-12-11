package com.group22.gui;

import java.text.DecimalFormat;

import com.group22.TimeUtil;

import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The {@code Playing} class extends StackPane and is the pane that 
 * contains the game contents.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class Playing extends StackPane {
    /** Label which contains the time text. */
    private Label timeLabel;
    /** Label which contains the score text. */
    private Label scoreLabel;
    /** Label which contains the level name text. */
    private Label levelLabel;
    /** Pane containing the game info. */
    private BorderPane infoBar;
    /** GraphicsContext to the containing canvas element. */
    private GraphicsContext graphicsContext;

    /**
     * Creates a Playing pane.
     */
    public Playing() {
        Pane canvasPane = this.createCanvasPane();
        BorderPane infoBar = this.createInfoBar();

        this.getChildren().add(canvasPane);
        this.getChildren().add(infoBar);
    }

    /**
     * Sets the left and right padding of the info bar.
     * 
     * @param offset the padding on the left and right of the bar.
     */
    public void setInfoBarPadding(double offset) {
        this.infoBar.setPadding(new Insets(0, offset, 0, offset));

        Stage stage = (Stage)this.getScene().getWindow();
        this.infoBar.prefWidthProperty().bind(stage.widthProperty());
        this.infoBar.minWidthProperty().bind(stage.widthProperty());
        this.infoBar.maxWidthProperty().bind(stage.widthProperty());
    }

    /**
     * Gets the {@link #graphicsContext} of the canvas element.
     * 
     * @return GraphicsContent in canvas in pane.
     */
    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    /**
     * Sets the {@link #timeLabel} text based on the time value.
     * 
     * @param time The game time number.
     */
    public void setGameTime(double time) {
        this.timeLabel.setText("TIME: " + TimeUtil.getLevelTimeLeft(time));
    }

    /**
     * Sets the {@link #levelLabel} text based on the level name.
     * 
     * @param level The name of the level.
     */
    public void setGameLevel(String level) {
        this.levelLabel.setText(level);
    }

    /**
     * Sets the {@link #scoreLabel} text based on the game score.
     * 
     * @param score The score in the game.
     */
    public void setGameScore(int score) {
        DecimalFormat formatter = new DecimalFormat("000");
        this.scoreLabel.setText("SCORE: " + formatter.format(score));
    }

    /**
     * Creates the Pane which contains the canvas and sets the 
     * {@link #graphicsContext} from the canvas.
     * 
     * @return The pane containing canvas.
     */
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

    /**
     * Creates the pane which contains the game information.
     * 
     * @return The Pane containing the information.
     */
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

package com.group22;

import java.text.DecimalFormat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * 
 * The class {@code GamePane} is a scene layout element which extends stack pane.
 * 
 * GamePane is used to abstract the gui into a contained gui element with all functionallity needed for a game. 
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class GamePane extends StackPane {
    public static final int INFO_BAR_HEIGHT = 50;
    public static final int INFO_BAR_SIZE_PADDING = 20;

    private GraphicsContext graphicsContext;
    private MenuPane startMenu;
    private MenuPane pauseMenu;
    private StackPane canvasPane;

    private BorderPane infoBar;
    private Text time;
    private Text level;
    private Text score;

    /**
     * Creates a GamePane.
     */
    public GamePane() {
        this.getStylesheets().add(
            getClass().getResource("/com/group22/menu.css").toExternalForm());

        this.getStyleClass().add("game-pane");

        this.setUpCanvasPane();
        this.setUpPauseMenu();
        this.setUpStartMenu();

        this.setState(GameState.Start);
    }

    
    /** 
     * Changes visibility of child panes based on the game state.
     * Used when {@code Engine} sets its gameState
     * 
     * @param state
     */
    public void setState(GameState state) {
        switch (state) {
            case Start:
                this.startMenu.setVisible(true);
                this.pauseMenu.setVisible(false);
                this.canvasPane.setVisible(false);
                break;
            case Playing:
                this.startMenu.setVisible(false);
                this.pauseMenu.setVisible(false);
                this.canvasPane.setVisible(true);
                break;
            case Paused:
                this.startMenu.setVisible(false);
                this.pauseMenu.setVisible(true);
                this.canvasPane.setVisible(true);
                break;
        }
    }

    public void setGameTime(double time) {
        DecimalFormat formatter = new DecimalFormat("000");
        this.time.setText("TIME: " + formatter.format(time));
    }

    public void setGameLevel(int level) {
        DecimalFormat formatter = new DecimalFormat("00");
        this.level.setText("LEVEL: " + formatter.format(level));
    }

    public void setGameScore(int score) {
        DecimalFormat formatter = new DecimalFormat("000");
        this.score.setText("SCORE: " + formatter.format(score));
    }
    
    /** 
     * Get GraphicsContext of canvas in pane.
     * 
     * @return GraphicsContext
     */
    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public void setGameOffesetX(double offset) {
        this.infoBar.setPadding(new Insets(0, offset, 0, offset));
    }

    private void setUpScore() {
        this.score = new Text();
        this.score.setFont(Font.font("Monospaced", FontWeight.BOLD, 20));
        this.score.setTextAlignment(TextAlignment.CENTER);
        this.score.setFill(TileColor.LIGHT_RED.color);

        BorderPane.setAlignment(this.score, Pos.CENTER);

        this.setGameScore(0);
    }

    private void setUpLevel() {
        this.level = new Text();
        this.level.setFont(Font.font("Monospaced", FontWeight.BOLD, 20));
        this.level.setFill(TileColor.LIGHT_RED.color);

        BorderPane.setAlignment(this.level, Pos.CENTER);

        this.setGameLevel(0);
    }

    private void setUpTime() {
        this.time = new Text();
        this.time.setFont(Font.font("Monospaced", FontWeight.BOLD, 20));
        this.time.setFill(TileColor.LIGHT_RED.color);

        BorderPane.setAlignment(this.time, Pos.CENTER);

        this.setGameTime(0);
    }

    private Pane createCanvas() {
        Canvas canvas = new Canvas();
        Pane canvasOuter = new Pane(canvas);

        this.graphicsContext = canvas.getGraphicsContext2D();

        canvas.setCache(true);
        canvas.setCacheHint(CacheHint.SPEED);

        canvas.widthProperty().bind(canvasOuter.widthProperty());
        canvas.heightProperty().bind(canvasOuter.heightProperty());

        return canvasOuter;
    }

    private void setUpInfoBar() {
        this.infoBar = new BorderPane();
        BorderPane innerInfoBar = new BorderPane();

        this.setUpTime();
        this.setUpScore();
        this.setUpLevel();

        HBox right = new HBox();
        right.getChildren().add(this.score);
        right.getChildren().add(this.time);
        right.setAlignment(Pos.CENTER);
        right.setSpacing(INFO_BAR_SIZE_PADDING);

        innerInfoBar.setPadding(new Insets(0, INFO_BAR_SIZE_PADDING, 0, INFO_BAR_SIZE_PADDING));
        innerInfoBar.setPrefHeight(INFO_BAR_HEIGHT);
        innerInfoBar.setRight(right);
        innerInfoBar.setLeft(this.level);

        this.infoBar.setTop(innerInfoBar);
    }

    private void setUpCanvasPane() {
        Pane canvas = createCanvas();
        this.setUpInfoBar();

        this.canvasPane = new StackPane();
        this.canvasPane.getChildren().add(canvas);
        this.canvasPane.getChildren().add(this.infoBar);

        this.getChildren().add(this.canvasPane);
    }



    private void setUpStartMenu() {
        this.startMenu = new MenuPane();
             
        this.startMenu.addItem("Start", () -> { Game.getInstance().setGameState(GameState.Playing); });

        this.getChildren().add(startMenu);
    }

    private void setUpPauseMenu() {
        this.pauseMenu = new MenuPane();
             
        this.pauseMenu.addItem("Resume", () -> { Game.getInstance().setGameState(GameState.Playing); });
        this.pauseMenu.addItem("Exit", () -> { Game.getInstance().setGameState(GameState.Start); });

        this.getChildren().add(this.pauseMenu);
    }
}

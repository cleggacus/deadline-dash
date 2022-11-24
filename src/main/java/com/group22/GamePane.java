package com.group22;

import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

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
    private GraphicsContext graphicsContext;
    private MenuPane startMenu;
    private MenuPane pauseMenu = new MenuPane();
    private Pane canvasPane = new Pane();

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

    
    /** 
     * Get GraphicsContext of canvas in pane.
     * 
     * @return GraphicsContext
     */
    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    private void setUpCanvasPane() {
        Canvas canvas = new Canvas();
        this.canvasPane = new Pane(canvas);

        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());

        this.graphicsContext = canvas.getGraphicsContext2D();

        canvas.setCache(true);
        canvas.setCacheHint(CacheHint.SPEED);

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

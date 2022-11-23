package com.gorup22;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GamePane extends StackPane {
    private GraphicsContext graphicsContext;
    private MenuPane startMenu;
    private MenuPane pauseMenu = new MenuPane();
    private Pane canvasPane = new Pane();
    
    public GamePane() {
        this.getStylesheets().add(
            getClass().getResource("/com/group22/menu.css").toExternalForm());

        this.setUpCanvasPane();
        this.setUpPauseMenu();
        this.setUpStartMenu();

        this.setState(GameState.Start);
    }


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

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    private void setUpCanvasPane() {
        Canvas canvas = new Canvas();
        this.canvasPane = new Pane(canvas);

        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());

        this.graphicsContext = canvas.getGraphicsContext2D();

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

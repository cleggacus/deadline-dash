package com.gorup22;

import javafx.scene.Scene;

public class Game {
    private static final int INITIAL_WIDTH = 640;
    private static final int INITIAL_HEIGHT = 480;

    private static Game instance;

    private GameState gameState;
    private GamePane gamePane;

    private Game() {
        this.gamePane = new GamePane();

        this.setGameState(GameState.Start);
    }

    public static synchronized Game getInstance() {
        if(Game.instance == null)
            Game.instance = new Game();

        return Game.instance;
    }

    public void setGameState(GameState gameState) {
        this.gamePane.setState(gameState);
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Scene createScene() {
        return new Scene(this.gamePane, INITIAL_WIDTH, INITIAL_HEIGHT);
    }

}

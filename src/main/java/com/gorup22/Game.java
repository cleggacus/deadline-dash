package com.gorup22;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class Game {
    private static final int INITIAL_WIDTH = 640;
    private static final int INITIAL_HEIGHT = 480;

    private static Game instance;

    private ArrayList<Entity> entities;

    private GameState gameState;
    private GamePane gamePane;
    private AnimationTimer gameLoop;

    private Game() {
        this.gamePane = new GamePane();
        this.entities = new ArrayList<>();

        this.setGameState(GameState.Start);

        this.setUpGameLoop();
        this.gameLoop.start();
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
        Scene scene = new Scene(this.gamePane, INITIAL_WIDTH, INITIAL_HEIGHT);

        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ESCAPE)
                this.setGameState(GameState.Paused);  
        });

        return scene;
    }

    private void update() {
        for(Entity entity : this.entities) {
            entity.update();
        }
    }

    private void draw() {
        GraphicsContext ctx = this.gamePane.getGraphicsContext();

        for(Entity entity : this.entities) {
            entity.draw(ctx);
        }
    }

    private void setUpGameLoop() {
        this.gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(gameState == GameState.Playing) {
                    System.out.println(now);
                    update();
                    draw();
                }
            }
        };
    }
}

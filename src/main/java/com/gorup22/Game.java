package com.gorup22;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class Game {
    private static final int INITIAL_WIDTH = 640;
    private static final int INITIAL_HEIGHT = 480;

    private static Game instance;

    private ArrayList<Entity> entities;

    private GameState gameState;
    private GamePane gamePane;
    private Renderer renderer;
    private AnimationTimer gameLoop;
    private KeyboardManager keyboardManager;

    private double delta = 0;
    private long lastTime = 0;

    private Game() {
        this.gamePane = new GamePane();
        this.renderer = new Renderer(this.gamePane.getGraphicsContext());

        this.entities = new ArrayList<>();

        this.setGameState(GameState.Start);

        this.entities.add(new TestObject());

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

        this.keyboardManager = new KeyboardManager(scene);

        return scene;
    }

    public double getDelta() {
        return delta;
    }

    private void update() {
        if(this.keyboardManager.getKeyDown(KeyCode.ESCAPE)) {
            if(this.gameState == GameState.Playing)
                this.setGameState(GameState.Paused);

            else if(this.gameState == GameState.Paused)
                this.setGameState(GameState.Playing);
        }

        if(gameState == GameState.Playing) {
            for(Entity entity : this.entities)
                entity.callUpdateMovement();

            for(Entity entity : this.entities)
                entity.callUpdate();
        }

        this.keyboardManager.nextFrame();
    }

    private void draw() {
        if(gameState != GameState.Start) {
            this.renderer.newFrame();

            for(Entity entity : this.entities)
                entity.draw(renderer);
        }
    }

    private void updateTimer(long now) {
        if(this.lastTime == 0)
            this.lastTime = now;

        this.delta = 0.000000001 * (now - lastTime);
        this.lastTime = now;
    }

    private void setUpGameLoop() {
        this.gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateTimer(now);
                update();
                draw();
                
            }
        };
    }
}

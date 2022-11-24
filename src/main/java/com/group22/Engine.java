package com.group22;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public abstract class Engine {
    private static final int INITIAL_WIDTH = 640;
    private static final int INITIAL_HEIGHT = 480;

    protected ArrayList<Entity> entities;

    private GameState gameState;
    private GamePane gamePane;
    private Renderer renderer;
    private AnimationTimer gameLoop;
    private KeyboardManager keyboardManager;

    private double delta = 0;
    private long lastTime = 0;

    public Engine() {
        this.gamePane = new GamePane();
        this.renderer = new Renderer(this.gamePane.getGraphicsContext());
        this.entities = new ArrayList<>();

        this.setGameState(GameState.Start);

        this.setUpGameLoop();
        this.gameLoop.start();
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

    public boolean getKeyState(KeyCode keyCode) {
        if(this.keyboardManager == null)
            return false;

        return this.keyboardManager.getKeyState(keyCode);
    }

    public boolean getKeyDown(KeyCode keyCode) {
        if(this.keyboardManager == null)
            return false;

        return this.keyboardManager.getKeyDown(keyCode);
    }

    public boolean getKeyUp(KeyCode keyCode) {
        if(this.keyboardManager == null)
            return false;

        return this.keyboardManager.getKeyUp(keyCode);
    }

    protected abstract void update();

    public int getViewHeight() {
        return this.renderer.getViewHeight();
    }

    public int getViewWidth() {
        return this.renderer.getViewWidth();
    }

    protected void setViewSize(int width, int height) {
        this.renderer.setViewSize(width, height);
    }

    private void callUpdate(long now) {
        this.updateTimer(now);
        this.updateState();
        this.updateEntities();
        this.update();
        this.updateAfter();
    }

    private void updateTimer(long now) {
        if(this.lastTime == 0)
            this.lastTime = now;

        this.delta = 0.000000001 * (now - lastTime);
        this.lastTime = now;
    }

    private void updateState() {
        if(this.getKeyDown(KeyCode.ESCAPE)) {
            if(this.gameState == GameState.Playing)
                this.setGameState(GameState.Paused);

            else if(this.gameState == GameState.Paused)
                this.setGameState(GameState.Playing);
        }
    }

    private void updateEntities() {
        if(gameState == GameState.Playing) {
            for(Entity entity : this.entities)
                entity.callUpdateMovement();

            for(Entity entity : this.entities)
                entity.callUpdate();
        }
    }

    private void updateAfter() {
        this.keyboardManager.nextFrame();
    }

    private void draw() {
        if(gameState != GameState.Start) {
            this.renderer.newFrame();

            for(Entity entity : this.entities)
                entity.draw(renderer);
        }
    }

    private void setUpGameLoop() {
        this.gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                callUpdate(now);
                draw();
            }
        };
    }
}

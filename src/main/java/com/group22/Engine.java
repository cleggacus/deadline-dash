package com.group22;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * 
 * The class {@code Engine} handles the underlining mechanisms for the game.
 * 
 * An extending class has to override the method {@link #update()} which is exicuted every frame after entities are updated.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public abstract class Engine {
    private static final int INITIAL_WIDTH = 640;
    private static final int INITIAL_HEIGHT = 480;

    /** List of entities which are updated and rendered in Game. */
    protected ArrayList<Entity> entities;

    private GameState gameState;
    private GamePane gamePane;
    private Renderer renderer;
    private AnimationTimer gameLoop;
    private KeyboardManager keyboardManager;

    private double delta = 0;
    private long lastTime = 0;

    /**
     * Creates a new Engine
     */
    public Engine() {
        this.gamePane = new GamePane();
        this.renderer = new Renderer(this.gamePane.getGraphicsContext());
        this.entities = new ArrayList<>();

        this.setGameState(GameState.Start);

        this.setUpGameLoop();
    }

    
    /** 
     * Sets the current game state of the {@code Engine}.
     * 
     * @param gameState The new gameState
     */
    public void setGameState(GameState gameState) {
        this.gamePane.setState(gameState);
        this.gameState = gameState;
    }

    
    /** 
     * Gets the current game state of the {@code Engine}.
     * 
     * @return GameState gameState of the program
     */
    public GameState getGameState() {
        return gameState;
    }

    
    /** 
     * Creates a scene containing a game pane.
     * A scene must be created for the game to be viewed and the keyboard manager to function.
     * 
     * @return Scene
     */
    public Scene createScene() {
        Scene scene = new Scene(this.gamePane, INITIAL_WIDTH, INITIAL_HEIGHT);

        this.keyboardManager = new KeyboardManager(scene);

        callStart();

        return scene;
    }

    
    /** 
     * Gets the change in time in seconds since the last frame was updated.
     * 
     * @return double
     */
    public double getDelta() {
        return delta;
    }

    
    /** 
     * If the key is currently being pressed down this method return true otherwise it returns false.
     * 
     * @param keyCode
     *      The key code of the key to be checked.
     * 
     * @return boolean
     *      True if key is down, False if key is up.
     */
    public boolean getKeyState(KeyCode keyCode) {
        return this.keyboardManager.getKeyState(keyCode);
    }

    
    /** 
     * Checks if the key is being pressed down on the frame which the method is called.
     * 
     * @param keyCode
     *      The key code of the key to be checked.
     * 
     * @return boolean
     *      True if key is being pressed on the current frame, False otherwise.
     */
    public boolean getKeyDown(KeyCode keyCode) {
        return this.keyboardManager.getKeyDown(keyCode);
    }

    
    /** 
     * Checks if the key is being released on the frame which the method is called.
     * 
     * @param keyCode
     *      The key code of the key to be checked.
     * 
     * @return boolean
     *      True if key is being released on the current frame, False otherwise.
     */
    public boolean getKeyUp(KeyCode keyCode) {
        return this.keyboardManager.getKeyUp(keyCode);
    }

    public boolean isInBounds(int x, int y) {
        return
            x >= 0 && 
            x < this.getViewWidth() && 
            y >= 0 &&
            y < this.getViewHeight();
    }

    
    /** 
     * Gets the height in number of tiles from the renderer.
     * 
     * @return int
     */
    public int getViewHeight() {
        return this.renderer.getViewHeight();
    }

    
    /** 
     * Gets the width in number of tiles from the renderer.
     * 
     * @return int
     */
    public int getViewWidth() {
        return this.renderer.getViewWidth();
    }


    /**
     * This method needs to be overridden through extending the class.
     * The update method is called every frame after the entities have been updated.
     */
    protected abstract void update();

    protected abstract void start();

    
    /** 
     * Sets the width and height in tiles for the renderer.
     * 
     * @param width
     * @param height
     */
    protected void setViewSize(int width, int height) {
        this.renderer.setViewSize(width, height);
    }

    private void callStart() {
        this.gameLoop.start();
        this.start();
    }
    
    /** 
     * This method is called every frame before the frame is drawn to the GraphicsContext.
     * 
     * @param now
     *      Current time in nano secconds.
     */
    private void callUpdate(long now) {
        this.updateTimer(now);
        this.updateState();
        this.updateEntities();
        this.update();
        this.updateAfter();
    }

    
    /** 
     * Updates timer by comparing current time and time of last frame update.
     * Sets delta to the change in time in seconds.
     * 
     * @param now
     */
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
                if(keyboardManager != null) {
                    callUpdate(now);
                    draw();
                }
            }
        };
    }
}

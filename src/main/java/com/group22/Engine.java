package com.group22;

import java.util.ArrayList;
import java.util.Iterator;

import com.group22.gui.GamePane;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * 
 * The class {@code Engine} handles the underlining mechanisms for the game.
 * 
 * An extending class has to override the method {@link #update()} which is executed every frame after entities are updated.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public abstract class Engine {
    /** Initial windows width when the application is launched. */
    private static final int INITIAL_WIDTH = 640;
    /** Initial windows height when the application is launched. */
    private static final int INITIAL_HEIGHT = 480;

    /** List of entities which are updated and rendered in Game. */
    protected ArrayList<Entity> entities;

    private GamePane gamePane;
    private Renderer renderer;
    private AnimationTimer gameLoop;
    private KeyboardManager keyboardManager;

    /** Time passed from the last frame in seconds. */
    private double delta = 0;
    /** Time when the last frame updated in nanoseconds. */
    private long lastTime = 0;

    /**
     * Creates a new Engine.
     */
    public Engine() {
        this.gamePane = new GamePane();
        this.keyboardManager = new KeyboardManager();
        this.renderer = new Renderer(this.gamePane.getPlaying().getGraphicsContext());
        this.renderer.setPadding(50, 0, 0, 0);
        this.entities = new ArrayList<>();

        this.setUpGameLoop();

        this.setGameState(GameState.ProfileSelector);
    }

    public String getUsername() {
        return this.gamePane.getProfileSelector().getUsername();
    }

    /**
     * Gets an ArrayList of all entities that are a given class or inherits that class.
     * 
     * @param withClass
     *      The class that extends {@code Entity} to check.
     * 
     * @return ArrayList of entities
     */
    @SuppressWarnings("unchecked")
    public <T extends Entity>ArrayList<T> getEntities(Class<T> withClass) {
        ArrayList<T> returnEntities = new ArrayList<>();

        for(Entity entity : this.entities) {
            if(withClass.isAssignableFrom(entity.getClass())) {
                returnEntities.add((T)entity);
            }
        }

        return returnEntities;
    }

    /**
     * Gets an the array list of entities in the engine instasnce.
     * 
     * @return ArrayList of entities
     */
    public ArrayList<Entity> getEntities() {
        return this.entities;
    }

    /**
     * Removes entity from the array list of {@link #entities}.
     * 
     * @param entity
     *      The entity to remove.
     */
    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }

    /** 
     * Sets the current game state of the {@code Engine}.
     * 
     * @param gameState 
     *      The new game state.
     */
    public void setGameState(GameState gameState) {
        GameState currentGameState = this.gamePane.getCurrentState();

        if(gameState == currentGameState)
            return;

        boolean restart = 
            currentGameState != GameState.Paused && 
            gameState == GameState.Playing;

        if(restart) {
            this.entities.clear();
            this.start();
        }

        this.gamePane.setState(gameState);
    }
    
    /** 
     * Creates a scene containing a game pane.
     * A scene must be created for the game to be viewed and the keyboard manager to function.
     * 
     * @return 
     *      The scene containing the game pane.
     */
    public Scene createScene() {
        Scene scene = new Scene(this.gamePane, INITIAL_WIDTH, INITIAL_HEIGHT);
        this.keyboardManager.setScene(scene);
        this.gameLoop.start();

        return scene;
    }

    
    /** 
     * Gets the change in time in seconds since the last frame was updated.
     * 
     * @return
     *      Time since last frame in seconds.
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
     * @return
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
     * @return
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
     * @return
     *      True if key is being released on the current frame, False otherwise.
     */
    public boolean getKeyUp(KeyCode keyCode) {
        return this.keyboardManager.getKeyUp(keyCode);
    }

    public KeyCode getLastKeyDown(KeyCode ...keyCodes) {
        return this.keyboardManager.getLastKeyDown(keyCodes);
    }

    /**
     * Checks if a position is in the bounds of the current renderer view size.
     * 
     * @param x
     *      X position to check.
     * 
     * @param y
     *      Y position to check.
     * 
     * @return
     *      True if (x, y) is in renderers view.
     */
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
     * @return
     *      The height in tiles of the grid.
     */
    public int getViewHeight() {
        return this.renderer.getViewHeight();
    }

    
    /** 
     * Gets the width in number of tiles from the renderer.
     * 
     * @return
     *      The width in tiles of the grid.
     */
    public int getViewWidth() {
        return this.renderer.getViewWidth();
    }


    /**
     * This method needs to be overridden through extending the class.
     * The update method is called every frame after the entities have been updated.
     */
    protected abstract void update();

    /**
     * This method needs to be overridden through extending the class.
     * THe start method is called when the game is put into the Playing state from either Start or GameOver.
     */
    protected abstract void start();

    /**
     * Gets the current GamePane GUI element.
     * 
     * @return
     *      The generated GamePane.
     */
    protected GamePane getGamePane() {
        return gamePane;
    }

    
    /** 
     * Sets the width and height in tiles for the renderer.
     * 
     * @param width
     *      The width of the grid in tiles.
     * 
     * @param height
     *      The height of the grid in tiles.
     */
    protected void setViewSize(int width, int height) {
        this.renderer.setViewSize(width, height);
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

        if(this.gamePane.getCurrentState() == GameState.Playing) {
            this.update();
            this.updateEntities();
        }

        this.updateAfter();
    }

    
    /** 
     * Updates timer by comparing current time and time of last frame update.
     * Sets delta to the change in time in seconds.
     * 
     * @param now
     *      The current time in nano seconds.
     */
    private void updateTimer(long now) {
        if(this.lastTime == 0)
            this.lastTime = now;

        this.delta = 0.000000001 * (now - lastTime);
        this.lastTime = now;
    }

    /**
     * Updates the state based on actions like keyboard inputs.
     */
    private void updateState() {
        if(this.getKeyDown(KeyCode.ESCAPE)) {
            if(this.gamePane.getCurrentState() == GameState.Playing)
                this.setGameState(GameState.Paused);

            else if(this.gamePane.getCurrentState() == GameState.Paused)
                this.setGameState(GameState.Playing);
        }
    }

    /**
     * Updates the entities movements and then there game logic.
     */
    @SuppressWarnings("unchecked")
    private void updateEntities() {
        ArrayList<Entity> entities = (ArrayList<Entity>)this.entities.clone();

        for(Entity entity : entities) {
            entity.callUpdate();
        }

        for(Entity entity : entities) {
            entity.callUpdateMovement();
        }
    }

    /**
     * Updates thats should occur after all the other updates in each frame.
     */
    private void updateAfter() {
        this.keyboardManager.nextFrame();
    }

    /**
     * Draws each entity with the renderer.
     */
    private void draw() {
        GameState gameState = this.gamePane.getCurrentState();

        if(
            gameState == GameState.Playing ||
            gameState == GameState.Paused ||
            gameState == GameState.GameOver
        ){
            this.renderer.newFrame();
            this.gamePane.getPlaying().setInfoBarPadding(this.renderer.getOffsetX());

            for(Entity entity : this.entities)
                entity.draw(renderer);
        }
    }

    /**
     * Creates the game loop with and update and a draw call each frame.
     */
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

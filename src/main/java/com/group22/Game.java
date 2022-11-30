package com.group22;

/**
 * The {@code Game} class acts as a game manager handling all the game logic.
 * Since there is only one game and it extends Engine it uses the singleton pattern and can be used with the {@link #getInstance()} method.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class Game extends Engine {
    private double time;
    private int score; 

    private Tile[][] tiles;
    private Entity player;

    private static Game instance;

    /**
     * Creates Game.
     * Set to private so multiple instances cannot be created.
     */
    private Game() {}

    /** 
     * This method is used so the public game data can be accessed in other parts of the applicaiton. 
     * The instance of Game is created on its first call and is returned in the method.
     * 
     * @return Game
     */
    public static synchronized Game getInstance() {
        if(Game.instance == null)
            Game.instance = new Game();

        return Game.instance;
    }

    public Entity getPlayer() {
        return this.player;
    }

    /**
     * Checks if theres a color at (x1, y1) thats in (x2, y2).
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public boolean colorMatch(int x1, int y1, int x2, int y2) {
        return this.tiles[x1][y1].colorMatch(this.tiles[x2][y2]);
    }

    public void addTime(double seconds) {
        this.time += seconds;
    }

    public void addPoints(int val) {
        this.score += val;
    }

    public int getScore() {
        return score;
    }

    @Override
    protected void start(Level level) {

        int width = level.getWidth();
        int height = level.getHeight();

        this.tiles = level.getTiles();
        this.time = level.getTimeToComplete();

        this.setViewSize(width, height);
        this.getGamePane().setGameLevel(1);

        this.entities.addAll(level.getEntities());
    }

    /**
     * Overridden update method from {@code Engine}.
     */
    @Override
    protected void update() {
        updateTime();
    }

    private void updateTime() {
        this.time -= this.getDelta();

        if(this.time <= 0) {
            this.time = 0;
            this.setGameState(GameState.GameOver);
        }

        this.getGamePane().setGameTime(this.time);
        this.getGamePane().setGameScore(this.score);
    }
}

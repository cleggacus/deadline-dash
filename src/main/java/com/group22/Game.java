package com.group22;

/**
 * The {@code Game} class acts as a game manager handling all the game logic.
 * Since there is only one game and it extends Engine it uses the singleton pattern and can be used with the {@link #getInstance()} method.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class Game extends Engine {
    private Tile[][] tiles;

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

    @Override
    protected void start() {
        int width = 15;
        int height = 10;

        this.tiles = new Tile[width][height];

        this.setViewSize(width, height);

        String tileData = 
            "RRRR RRRR RRRR RRRR RRRR RRRR rrrr rrrr RRRR RRRR RRRR RRRR RRRR RRRR RRRR " +
            "rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr RRRR " +
            "bbbb bbbb bbbb bbbb bbbb bbbb bbbb bbbb bbbb bbbb bbbb bbbb bbbb bbbb bRRb " +
            "bbbb rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr " +
            "BbbB BBBB BBBB BBBB BBBB BBBB BBBB BBBB BBBB BBBB BBBB rrrr rrrr rrrr rrrr " +
            "BbbB BBBB BBBB BBBB BBBB BBBB BBBB BBBB BBBB BBBB rBBr rrrr rrrr rrrr rrrr " +
            "bbbb rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr " +
            "bbbb bbbb bbbb bbbb bbbb bbbb bbbb bbbb bbbb bbbb bbbb bbbb bbbb bbbb bRRb " +
            "rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr rrrr RRRR " +
            "RRRR RRRR RRRR RRRR RRRR RRRR rrrr rrrr RRRR RRRR RRRR RRRR RRRR RRRR RRRR";

        String tileDataArray[] = tileData.split(" ");

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                this.tiles[x][y] = new Tile(x, y, tileDataArray[y*width + x]);
                this.entities.add(this.tiles[x][y]);
            }
        }

        for(int i = 0; i < 5; i++)
            this.entities.add(new TestObject());
    }

    /**
     * Overridden update method from {@code Engine}.
     */
    @Override
    protected void update() {}
}

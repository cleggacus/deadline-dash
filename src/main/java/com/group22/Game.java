package com.group22;

public class Game extends Engine {
    private static Game instance;

    private Game() {
        super();
        testMapSetup();
    }

    public static synchronized Game getInstance() {
        if(Game.instance == null)
            Game.instance = new Game();

        return Game.instance;
    }

    private void testMapSetup() {
        int width = 15;
        int height = 10;

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
                Tile t = new Tile(x, y, tileDataArray[y*width + x]);
                this.entities.add(t);
            }
        }

        this.entities.add(new TestObject());
    }

    @Override
    protected void update() {}
}

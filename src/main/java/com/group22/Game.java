package com.group22;

public class Game extends Engine {
    private static Game instance;

    private Game() {
        super();

        this.entities.add(new TestObject());
    }

    public static synchronized Game getInstance() {
        if(Game.instance == null)
            Game.instance = new Game();

        return Game.instance;
    }

    @Override
    protected void update() {}
}

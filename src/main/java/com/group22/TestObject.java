package com.group22;

import javafx.scene.input.KeyCode;

public class TestObject extends LandMover {
    public TestObject() {
        super();

        this.setSprite("character/0.png");

        this.spriteOffsetY = -0.3;
        this.moveEvery = 0.1;
    }

    @Override
    protected void updateMovement() {
        if(Game.getInstance().getKeyState(KeyCode.W)) {
            this.setSprite("character/3.png");
            move(0, -1);
        } else if(Game.getInstance().getKeyState(KeyCode.S)) {
            this.setSprite("character/0.png");
            move(0, 1);
        }

        if(Game.getInstance().getKeyState(KeyCode.A)) {
            this.setSprite("character/1.png");
            move(-1, 0);
        } else if(Game.getInstance().getKeyState(KeyCode.D)) {
            this.setSprite("character/2.png");
            move(1, 0);
        }
    }

    @Override
    protected void update() {}
}

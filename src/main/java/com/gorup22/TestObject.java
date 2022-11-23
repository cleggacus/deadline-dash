package com.gorup22;

import javafx.scene.input.KeyCode;

public class TestObject extends Entity {
    public TestObject() {
        super();

        // sprite name
        this.setSprite("tile0.png");

        // how often to call update move
        this.moveEvery = 0.1;
    }

    @Override
    protected void updateMovement() {
        // change movement every (moveEvery)s

        if(Game.getInstance().getKeyState(KeyCode.W)) {
            this.y --;
        } else if(Game.getInstance().getKeyState(KeyCode.S)) {
            this.y ++;
        }

        if(Game.getInstance().getKeyState(KeyCode.A)) {
            this.x --;
        } else if(Game.getInstance().getKeyState(KeyCode.D)) {
            this.x ++;
        }
    }

    @Override
    protected void update() {
        // things to check for every frame
        // e.g. check for collisions
    }
    
}

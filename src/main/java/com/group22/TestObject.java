package com.group22;

import javafx.scene.input.KeyCode;

public class TestObject extends Entity {
    public TestObject() {
        super();

        // sprite name
        this.setSprite("tile0.png");

        // how often to call update move
        this.moveEvery = 0.05;
    }

    @Override
    protected void updateMovement() {
        // change movement every (moveEvery)s

        if(Game.getInstance().getKeyState(KeyCode.W) && validMove(0, -1)) {
            this.y --;
        } else if(Game.getInstance().getKeyState(KeyCode.S) && validMove(0, 1)) {
            this.y ++;
        }

        if(Game.getInstance().getKeyState(KeyCode.A) && validMove(-1, 0)) {
            this.x --;
        } else if(Game.getInstance().getKeyState(KeyCode.D) && validMove(1, 0)) {
            this.x ++;
        }
    }

    private boolean validMove(int x, int y) {
        int newX = this.x + x;
        int newY = this.y + y;

        return 
            newX >= 0 && newX < Game.getInstance().getViewWidth() &&
            newY >= 0 && newY < Game.getInstance().getViewHeight();
    }

    @Override
    protected void update() {}
}

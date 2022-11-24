package com.group22;

import javafx.scene.input.KeyCode;

public class TestObject extends Entity {
    public TestObject() {
        super();

        this.setSprite("character/0.png");

        this.spriteOffsetY = -0.3;
        this.moveEvery = 0.075;
    }

    @Override
    protected void updateMovement() {
        if(Game.getInstance().getKeyState(KeyCode.W) && validMove(0, -1)) {
            this.setSprite("character/3.png");
            this.y --;
        } else if(Game.getInstance().getKeyState(KeyCode.S) && validMove(0, 1)) {
            this.setSprite("character/0.png");
            this.y ++;
        }

        if(Game.getInstance().getKeyState(KeyCode.A) && validMove(-1, 0)) {
            this.setSprite("character/1.png");
            this.x --;
        } else if(Game.getInstance().getKeyState(KeyCode.D) && validMove(1, 0)) {
            this.setSprite("character/2.png");
            this.x ++;
        }
    }

    private boolean validMove(double x, double y) {
        double newX = this.x + x;
        double newY = this.y + y;

        return 
            newX >= 0 && newX < Game.getInstance().getViewWidth() &&
            newY >= 0 && newY < Game.getInstance().getViewHeight();
    }

    @Override
    protected void update() {}
}

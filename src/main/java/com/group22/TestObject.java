package com.group22;

// randomly moving land move demo
public class TestObject extends LandMover {
    private int moveX = 0;
    private int moveY = 0;

    public TestObject() {
        super();

        this.setSprite("character/0.png");

        this.spriteOffsetY = -0.3;
        this.moveEvery = 0.3;

        this.x = (int)(Math.floor(Math.random() * Game.getInstance().getViewWidth()));
        this.y = (int)(Math.floor(Math.random() * Game.getInstance().getViewHeight()));
    }

    @Override
    protected void updateMovement() {
        if(Math.random() < 0.3) {
            moveX = (int)(Math.floor(Math.random() * 3 - 1));
            moveY = (int)(Math.floor(Math.random() * 3 - 1));
        }

        if(moveY < 0)
            this.setSprite("character/3.png");
        else if(moveY > 0)
            this.setSprite("character/0.png");

        if(moveX < 0)
            this.setSprite("character/1.png");
        else if(moveX > 0)
            this.setSprite("character/2.png");

        this.move(moveX, moveY);
    }

    @Override
    protected void update() {}
}

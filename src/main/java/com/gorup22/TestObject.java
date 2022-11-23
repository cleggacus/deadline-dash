package com.gorup22;

public class TestObject extends Entity {
    public TestObject() {
        super();

        // sprite name
        this.setSprite("tile0.png");

        // how often to call update move
        this.moveEvery = 1;
    }

    @Override
    protected void updateMovement() {
        // change movement every (moveEvery)s
        this.x ++; 
        this.y ++; 
    }

    @Override
    protected void update() {
        // things to check for every frame
        // e.g. check for collisions
    }
    
}

package com.gorup22;

public class TestObject extends Entity {
    public TestObject() {
        super();

        this.setSprite("tile0.png");
        this.moveEvery = 1;
    }

    @Override
    protected void updateMovement() {
        this.x ++; 
        this.y ++; 
    }

    @Override
    protected void update() {
    }
    
}

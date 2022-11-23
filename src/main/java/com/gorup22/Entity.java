package com.gorup22;

import javafx.scene.image.Image;

public abstract class Entity {
    protected double moveEvery = 0;
    protected double timeSinceMove = 0;
    protected int x = 0;
    protected int y = 0;
    protected Image sprite;

    public Entity() {}

    protected abstract void updateMovement();
    protected abstract void update();

    protected void setSprite(String resourcesPath) {
        this.sprite = new Image(getClass().getResource("/com/group22/" + resourcesPath).toString());
    }

    public void callUpdate() {
        this.update();
    }

    public void callUpdateMovement() {
        double delta = Game.getInstance().getDelta();
        this.timeSinceMove += delta;

        if(this.timeSinceMove >= this.moveEvery) {
            this.updateMovement();
            this.timeSinceMove -= this.moveEvery;
        }

        if(this.moveEvery <= delta)
            this.timeSinceMove = 0;
    }

    public void draw(Renderer renderer) {
        if(this.sprite == null)
            return;

        renderer.drawImage(this.sprite, x, y);
    }

}

package com.group22;

import javafx.scene.image.Image;

/**
 * 
 * The class {@code Entity} is used for anything that is drawn or updated in the {@code Engine}.
 * 
 * An extending class has to override the methods {@link #update()} and {@link #updateMovement()}.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public abstract class Entity {
    /** Time between moveUpdates in seconds. */
    protected double moveEvery = 0;

    /** Time since last move in seconds. */
    protected double timeSinceMove = 0;

    /** Entity X position. */
    protected int x = 0;

    /** Entity Y position. */
    protected int y = 0;

    /** Offset Y position added to {@link #y} when drawing sprite. */
    protected double spriteOffsetY = 0;

    /** Offset X position added to {@link #x} when drawing sprite. */
    protected double spriteOffsetX = 0;

    /** Image sprite drawn to show for entity. */
    protected Image sprite;

    /**
     * Creates an Entity.
     */
    public Entity(){}

    /**
     * Publically exposed method which runs abstract update method.
     * This method is used by the engine to update the entities.
     */
    public void callUpdate() {
        this.update();
    }

    /**
     * Publically exposed method which runs abstract updateMovement method.
     * This method is used by the engine to update the movement of the entities.
     * This ensures that the entity only moves every {@link #moveEvery} seconds.
     */
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

    
    /** 
     * Draws the {@link #sprite} at the position and draw offset of the entity.
     * 
     * @param renderer
     */
    public void draw(Renderer renderer) {
        if(this.sprite == null)
            return;

        renderer.drawImage(this.sprite, spriteOffsetX + x, spriteOffsetY + y);
    }

    /**
     * This method needs to be overridden by extending {@code Entity}.
     * This is used for movement and is called every {@link #moveEvery} seconds.
     */
    protected abstract void updateMovement();

    /**
     * This method needs to be overridden by extending {@code Entity}.
     * This method is called every frame.
     */
    protected abstract void update();

    
    /** 
     * Sets the sprite using a path at src/main/resources/com/group22/...
     * 
     * @param resourcesPath
     */
    protected void setSprite(String resourcesPath) {
        this.sprite = new Image(getClass().getResource("/com/group22/" + resourcesPath).toString());
    }

    
    /** 
     * Sets the sprite to a given Image object.
     * 
     * @param image
     */
    protected void setSprite(Image image) {
        this.sprite = image;
    }
}

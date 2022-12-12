package com.group22;

import java.util.ArrayList;

/**
 * 
 * The class {@code FlyingAssassin} impliments an entity which 
 * moves across the board in a straight line regardless of the tiles. 
 * Turns 180 degrees if it hits a wall.
 * If it occupies same space as player then the player loses
 * 
 * @author Ezana Tareke
 * @version 1.1
 */
public class FlyingAssassin extends Entity {
    private int moveByY;
    private int moveByX;
    private boolean isVertical;

    /**
     * Creates the flying assasin.
     * 
     * @param x initial x position.
     * @param y initial y position
     * @param isVertical true if vertical and false if horizontal
     */
    public FlyingAssassin(int x, int y, boolean isVertical) {
        super(x, y);
        this.getSprite().setImage("NPC/FlyingAssassin.png");
        this.moveEvery = 0.2;
        this.isVertical = isVertical;

        this.moveByX = isVertical ? 0 : 1;
        this.moveByY = isVertical ? 1 : 0;
    }

    
    /** 
     * {@inheritDoc}
     * Used for saving a save state.
     */
    @Override
    public String toString() {
        return ("flyingassassin " + getX() + " " + getY() + 
            " " + (this.isVertical ? "v" : "h"));
    }

    /**
     * {@inheritDoc}
     * An overridden method inherited from {@link Entity}.
     * For every frame in the game update the flying assassins position
     */
    
    @Override
    protected void updateMovement() {
        if (!Game.getInstance().isInBounds(getX() +
            this.moveByX, getY() + this.moveByY)) {

            this.moveByY = -this.moveByY;
            this.moveByX = -this.moveByX;
        }

        this.move(this.moveByX, this.moveByY);
        
    }

    /**
     * {@inheritDoc}
     * An overridden method inherited from {@link Entity}.
     * updates on frame to see if it collides with any entities and remove them
     */

    @Override
    protected void update() {
        ArrayList<LandMover> landMovers = 
            Game.getInstance().getEntities(LandMover.class);

        for (LandMover landMover : landMovers) {
            if (landMover.getX() == this.getX() && 
                landMover.getY() == this.getY()) {
                    
                Game.getInstance().removeEntity(landMover);
            }
        }
    }
}

package com.group22;

import java.util.ArrayList;

/**
 * The super class for all pickup objects and an inheritor of entity,
 * checks for collision with thieves.
 * 
 * @author Lewis Meekings
 * @version 1.1
 */
public abstract class PickUp extends Entity {
    
    /**
     * Constuctor for pickup, which inherits from entity.
     * @param posX the horizontal location of the pickup
     * @param posY the vertical location of the pickup
     */
    public PickUp(int posX, int posY) {
        super(posX, posY);
    }

    /**
     * abstract method for pickup effects, as each pickup has different effects
     * they can override it and do their own thing.
     * @param landMover The activator of the pickup, important because players
     * and npcs interact with the pickup in different ways.
     */
    public abstract void activatePickUpEffect(LandMover landMover);

    /**
     * 
     */
    @Override
    protected void update() {
        ArrayList<LandMover> landMovers = 
            Game.getInstance().getEntities(LandMover.class);

        for (LandMover landMover : landMovers) {
            if (landMover.getX() == this.getX() && 
                landMover.getY() == this.getY()) {

                this.activatePickUpEffect(landMover);
            }
        }
    }

    /**
     * 
     */
    @Override
    protected void updateMovement() {}
}

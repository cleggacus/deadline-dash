package com.group22;

/**
 * The super class for all pickup objects and an inheritor of entity,
 * doesn't do much but has all the code that pickups should share.
 * 
 * @author Lewis Meekings
 * @version 1.0
 */
public abstract class PickUp extends Entity {
    
    /**
     * Constuctor for pickup, which inherits from entity.
     * @param posX the horizontal location of the pickup
     * @param posY the vertical location of the pickup
     * @param pickUpSprite the image of the pickup
     */
    public PickUp (int posX, int posY, Sprite pickUpSprite){
        //super(posX, posY, pickUpSprite);
    }

    /**
     * abstract method for pickup effects, as each pickup has different effects
     * they can override it and do their own thing.
     * @param landMover The activator of the pickup, important because players
     * and npcs interact with the pickup in different ways.
     */
    public abstract void activatePickUpEffect(LandMover landMover);

    /**
     * When the player collects a pickup this is called to add it to the
     * inventory.
     */
    public void addToInventory () {
        //Game.getInstance().getPlayer().getInventory().add(this);
    }
}
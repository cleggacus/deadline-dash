package com.group22;

/**
 * The Clock class is a type of pickup, it manipulates the level timer and as a
 * pickup it can be collected by both player and npcs. If the player picks it up
 * they are given more time, but if an npc picks it up the inverse happens to 
 * the level timer.
 * 
 * @author Lewis Meekings
 * @version 1.0
 */
public class Clock extends PickUp {
    
    private double time;

    /**
     * A constructor for the clock class which inherits from pickup and entity.
     * @param posX the horizontal position on the map
     * @param posY the vertical position on the map
     * @param time a temporal value to use on the level timer
     */
    public Clock(int posX, int posY, double time) {
        super(posX, posY);
        setTime(time);
        this.getSprite().setImage("item/clock.png");
    }

    /**
     * Sets the time value of the object
     * @param time
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     * @return the time value of the object
     */
    public double getTime() {
        return this.time;
    }

    /**
     * 
     */
    @Override
    public String toString() {
        return ("clock " + getX() + " " + getY() + " " + getTime());
    }

    /**
     * If the player grabs the loot it will decrease the level time and be added
     * to inventory, if another thief grabs it the time decreases.
     * @param landMover the object activating the pickup
     */
    @Override
    public void activatePickUpEffect(LandMover landMover) {
        if (landMover instanceof Player) {
            Game.getInstance().addTime(time);
        } else {
            Game.getInstance().addTime(-time);
        }
        Game.getInstance().removeEntity(this);
    }

}

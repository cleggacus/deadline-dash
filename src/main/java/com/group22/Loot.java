package com.group22;

/**
 * {@code Loot} is an extension of {@link PickUp}.
 * There can be multiple types of {@code Loot} in the game, with a variety of 
 * points values. 
 * It can be interacted with by {@link LandMover}.
 * If interacted with by a {@link Player}, the {@link Game} score 
 * will be updated, depending on how much the {@code Loot} value is. 
 * @author Rhys McGuire
 * @version 1.1
 */
public class Loot extends PickUp {
    private int val;

    /** 
     * A constructor for the {@code Loot} class which 
     * inherits from {@link PickUp} and {@link Entity}.
     * @param posX 
     *      the horizontal position on the level.
     * @param posY 
     *      the vertical position on the level.
     * @param val 
     *      the points value of the {@link Loot}.
     */
    public Loot(int posX, int posY, int val) {
        super(posX, posY);
        this.val = val;
        setLoot();
    }


    /**
     * Sets the Image and Points Value of the {@link Loot}
     */
    public void setLoot() {
    
        switch(val) {
            case 50:
                this.getSprite().setImage("item/50.png");
                break;
            case 100:
                this.getSprite().setImage("item/100.png"); 
                break;
            case 150:
                this.getSprite().setImage("item/150.png");
                break;
            case 200:
                this.getSprite().setImage("item/200.png");
                break;    
        }
    }

    
    /** 
     * Returns the points value of the {@link Loot}
     * @return int
     */
    public int getValue() { 
        return this.val;
    }

    
    /** 
     * Activates the effect of the 
     * {@link LandMover}'s interaction with the {@link Loot}.
     * @param landMover
     *      the {@link LandMover} which is interacting with the {@link Loot}
     */
    public void activatePickUpEffect(LandMover landMover) {
        if (landMover instanceof Player) {
            Game.getInstance().incrementScore(this.val);
        }


        Game.getInstance().removeEntity(this);
    }

    /**
     * Returns a {@link String} containing the loot position and value.
     * @return {@link String}
     */
    @Override
    public String toString(){
        return ("loot " + getX() + " " + getY() + " " + getValue());
    }
}

package com.group22;

/**
 * The {@code Loot} class is an extension of the class PickUp.
 * There can be multiple types of Loot in the game, with a variety of 
 * points values. 
 * Loot can be interacted with by LandMovers, and if interacted with by a Player,
 * the score will be updated depending on how much the Loot value is. 
 * @author Rhys McGuire
 * @version 1.0
 */

public class Loot extends PickUp {
    private int val;

    /** 
     * Creates Loot entity.
     */
    public Loot(int posX, int posY, Sprite lootSprite, int val){
        super(posX, posY, lootSprite);
    }


    /** 
     * Sets the points value of the loot entity
     * @param val
     */
    public void setValue(int val) {
        this.val = val;
    }

    
    /** 
     * Returns the points value of the loot entity
     * @return int
     */
    public int getValue() { 
        return this.val;
    }

    
    /** 
     * Activates the effect of the LandMover's interaction with the loot entity.
     * @param landMover
     */
    public void activatePickUpEffect(LandMover landMover) {
        if (landMover == Game.getInstance().getPlayer()){
            Game.getInstance().addPoints(val);        
        }
        //will we need a list/count of loot as need all to open door?

    }

}

/**ADDED TO GAME: Is this right? 
 *  private int score; 
 * 
 *  public void addPoints(int val) {
 *    this.score += val;
 *  }
 * 
 * NEED TO ADD TO GAME??
 * private void updateScore() {
 * 
 * }
*/
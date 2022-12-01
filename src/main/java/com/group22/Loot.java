package com.group22;

/**
 * The Loot class is an extension of the class PickUp.
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
     * A constructor for the Loot class which inherits from PickUp and Entity.
     * @param posX the horizontal position on the map
     * @param posY the vertical position on the map
     * @param val the points value of the Loot entity
     */
    public Loot(int posX, int posY, int val){
        super(posX, posY);
        this.val = val;
        setLoot();
    }

    /**
     * Sets the Image and Points Value of the Loot
     * @param val the points value of the Loot entity
     */
    public void setLoot(){
    
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
     * Returns the points value of the Loot entity
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
        if(landMover instanceof Player) {
            Game.getInstance().incrementScore(this.val);
        }

        Game.getInstance().removeEntity(this);
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
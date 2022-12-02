package com.group22;

public class Bomb extends Entity{

    private int countdown = 3;

    /**
     *
     * @param x the horizontal position of a bomb on the map
     * @param y the vertical poisiton of a bomb on the map
     * @param countdown the countdown timer of the bomb's detonation
     */

    public Bomb(int x, int y, int countdown) {
        super(x, y);
        this.countdown = countdown;
        this.getSprite().setImage("character/farron0.png");
    }

    /* public void checkNeighbourTiles(){
        while {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (i != x || j != y) { //ignore the bomb tile
                    }

                }
            }
        }
    } */

    public void activateBomb(){

    }

    @Override
    protected void updateMovement() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void update() {
        // TODO Auto-generated method stub
        
    }
}

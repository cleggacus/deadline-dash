package com.group22;

import java.util.ArrayList;

public class Bomb extends Entity{

    private final int  countdown;
    private double time = 0;
    private double bombStart;
    boolean fuze;
    private int fuzeCount = 1;

    /**
     *
     * @param x the horizontal position of a bomb on the map
     * @param y the vertical position of a bomb on the map
     * @param countdown the countdown timer of the bomb's detonation
     */

    public Bomb(int x, int y, int countdown) {
        super(x, y);
        this.countdown = countdown;
        this.fuze = false;
        this.fuzeCount = fuzeCount;
        this.getSprite().addImageSet("tick", Sprite.createImageFade(
            "item/farron/farron0.png",
            "item/farron/farron5.png",
            12*3
        ));
        this.getSprite().setImage("item/farron/farron0.png");

    }

    public void checkNeighbourTiles(){

        /*
        while {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (i != x || j != y) { //ignore the bomb tile
                    }

                }
            }
        }

         */
    }

    public void activateBomb() {
        double shakeAmountX = 0.05 * Math.sin(1.1 * countdown * Math.pow(this.time, 3));
        double shakeAmountY = 0.05 * Math.sin(0.9 * countdown * Math.pow(this.time, 3));
        this.setSpriteOffset(shakeAmountX, shakeAmountY);
        this.time += Game.getInstance().getDelta();
        this.getSprite().setAnimationSpeed(countdown);
        this.getSprite().setImageSet("tick");
        this.getSprite().setAnimationType(AnimationType.SINGLE);


        if ((this.bombStart - 3) >= Game.getInstance().getTime()){
            Game.getInstance().removeEntity(this);
        }
    }


    @Override
    protected void updateMovement() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void update() {
        ArrayList<LandMover> landMovers = Game.getInstance().getEntities(LandMover.class);
        ArrayList<FlyingAssassin> flyingAssassins = Game.getInstance().getEntities(FlyingAssassin.class);
        for(LandMover landMover : landMovers) {
            if(
                    landMover.getX() == this.getX() &&
                            landMover.getY() == (this.getY()-1)
            ) {
                if (this.fuzeCount == 1){
                    this.bombStart = Game.getInstance().getTime();
                    this.fuze = true;
                    fuzeCount--;
                }
            }
        }

        for(FlyingAssassin flyingAssassin : flyingAssassins) {
            if(
                    flyingAssassin.getX() == this.getX() &&
                            flyingAssassin.getY() == (this.getY()-1)
            ) {
                this.fuze = true;
            }
        }

        if (fuze){
            activateBomb();
        }
    }

        // TODO Auto-generated method stub


}
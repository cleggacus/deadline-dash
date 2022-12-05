package com.group22;

import java.util.ArrayList;

public class Bomb extends Entity{

    private final int  countdown;
    private boolean start = false;
    private double time = 0;
    boolean fuze;

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

        /**
         * CODE BELOW WILL MAKE YOU WANT TO UNALIVE YOURSELF


        int counter = countdown;

        while(counter>0){
            try {
                Thread.sleep(1000);
                System.out.println(counter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter--;
            if (counter == 1){
                Game.getInstance().removeEntity(this);
            }
        }

         * CODE ABOVE WILL MAKE YOU WANT TO UNALIVE YOURSELF
         */
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
                start = true;
            }
        }

        for(FlyingAssassin flyingAssassin : flyingAssassins) {
            if(
                    flyingAssassin.getX() == this.getX() &&
                            flyingAssassin.getY() == (this.getY()-1)
            ) {
                start = true;
            }
        }

        if(start)
            activateBomb();
    }

        // TODO Auto-generated method stub


}
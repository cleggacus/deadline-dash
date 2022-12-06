package com.group22;

import java.util.ArrayList;

public class Bomb extends Entity{

    private final int  countdown;
    private double time = 0;
    private double bombStart;
    private boolean fuze;

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

    public void detonateBomb() {
        ArrayList<Bomb> bombThings = Game.getInstance().getEntities(Bomb.class);
        ArrayList<LandMover> landMovers = Game.getInstance().getEntities(LandMover.class);
        ArrayList<FlyingAssassin> flyingAssassins = Game.getInstance().getEntities(FlyingAssassin.class);
        ArrayList<PickUp> pickUps = Game.getInstance().getEntities(PickUp.class);

        Game.getInstance().removeEntity(this);

        for(LandMover landMover : landMovers) {
            if (landMover.getX() == this.getX()){
                Game.getInstance().removeEntity(landMover);
            } else if (landMover.getY() == this.getY()){
                Game.getInstance().removeEntity(landMover);
            }
        }

        for(FlyingAssassin flyingAssassin : flyingAssassins) {
            if (flyingAssassin.getX() == this.getX()){
                Game.getInstance().removeEntity(flyingAssassin);
            } else if (flyingAssassin.getY() == this.getY()){
                Game.getInstance().removeEntity(flyingAssassin);
            }
        }

        for(PickUp pickUp : pickUps) {
            if (pickUp.getX() == this.getX()){
                Game.getInstance().removeEntity(pickUp);
            } else if (pickUp.getY() == this.getY()){
                Game.getInstance().removeEntity(pickUp);
            }
        }

        for(Bomb bombThing : bombThings) {
            if (bombThing.getX() == this.getX()){
                if (!(bombThing == this)) {
                    bombThing.detonateBomb();
                    Game.getInstance().removeEntity(bombThing);
                }
            } else if (bombThing.getY() == this.getY()){
                if (!(bombThing == this)) {
                    bombThing.detonateBomb();
                    Game.getInstance().removeEntity(bombThing);
                }
            }
        }
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
            detonateBomb();
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
            if (landMover.getX() == this.getX() && landMover.getY() == (this.getY()-1)) {
                if (!fuze){
                    this.bombStart = Game.getInstance().getTime();
                    this.fuze = true;
                }
            } else if (landMover.getX() == this.getX() && landMover.getY() == (this.getY()+1)) {
                if (!fuze){
                    this.bombStart = Game.getInstance().getTime();
                    this.fuze = true;
                }
            } else if (landMover.getX() == (this.getX()-1) && landMover.getY() == (this.getY())) {
                if (!fuze){
                    this.bombStart = Game.getInstance().getTime();
                    this.fuze = true;
                }
            } else if (landMover.getX() == (this.getX()+1) && landMover.getY() == (this.getY())) {
                if (!fuze){
                    this.bombStart = Game.getInstance().getTime();
                    this.fuze = true;
                }
            }
        }

        for(FlyingAssassin flyingAssassin : flyingAssassins) {
            if (flyingAssassin.getX() == this.getX() && flyingAssassin.getY() == (this.getY()-1)) {
                if (!fuze){
                    this.bombStart = Game.getInstance().getTime();
                    this.fuze = true;
                }
            } else if (flyingAssassin.getX() == this.getX() && flyingAssassin.getY() == (this.getY()+1)) {
                if (!fuze){
                    this.bombStart = Game.getInstance().getTime();
                    this.fuze = true;
                }
            } else if (flyingAssassin.getX() == (this.getX()-1) && flyingAssassin.getY() == (this.getY())) {
                if (!fuze){
                    this.bombStart = Game.getInstance().getTime();
                    this.fuze = true;
                }
            } else if (flyingAssassin.getX() == (this.getX()+1) && flyingAssassin.getY() == (this.getY())) {
                if (!fuze){
                    this.bombStart = Game.getInstance().getTime();
                    this.fuze = true;
                    activateBomb();
                }
            }
        }

        if (fuze){
            activateBomb();
        }
    }

        // TODO Auto-generated method stub


}
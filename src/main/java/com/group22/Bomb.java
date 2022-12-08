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
        ArrayList<Entity> allEntity = new ArrayList();
        ArrayList<Bomb> bombThings = Game.getInstance().getEntities(Bomb.class);
        ArrayList<LandMover> landMovers = Game.getInstance().getEntities(LandMover.class);
        ArrayList<FlyingAssassin> flyingAssassins = Game.getInstance().getEntities(FlyingAssassin.class);
        ArrayList<PickUp> pickUps = Game.getInstance().getEntities(PickUp.class);

        allEntity.addAll(bombThings);
        allEntity.addAll(landMovers);
        allEntity.addAll(flyingAssassins);
        allEntity.addAll(pickUps);

        Game.getInstance().removeEntity(this);

        for(Entity entity : allEntity) {
            if (entity.getX() == this.getX()){
                if(entity instanceof Bomb){
                    ((Bomb) entity).detonateBomb();
                }
                Game.getInstance().removeEntity(entity);
            } else if (entity.getY() == this.getY()){
                if(entity instanceof Bomb){
                    ((Bomb) entity).detonateBomb();
                }
                Game.getInstance().removeEntity(entity);
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

        if(!fuze){
            for(LandMover landMover : landMovers) {
                if (landMover.getX() == this.getX() && landMover.getY() == (this.getY()-1)) {
                    this.bombStart = Game.getInstance().getTime();
                    this.fuze = true;
                } else if (landMover.getX() == this.getX() && landMover.getY() == (this.getY()+1)) {
                    this.bombStart = Game.getInstance().getTime();
                    this.fuze = true;
                } else if (landMover.getX() == (this.getX()-1) && landMover.getY() == (this.getY())) {
                    this.bombStart = Game.getInstance().getTime();
                    this.fuze = true;
                } else if (landMover.getX() == (this.getX()+1) && landMover.getY() == (this.getY())) {
                    this.bombStart = Game.getInstance().getTime();
                    this.fuze = true;
                }
            }

        }
        
        if (fuze){
            activateBomb();
        }
}
}
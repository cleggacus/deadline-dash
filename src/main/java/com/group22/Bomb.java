package com.group22;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Bomb extends Entity{

    private static final double ANIMATION_DURATION = 0.032;
    private final int COUNTDOWN;
    private double time = 0;
    private double bombStart;
    private boolean fuze;
    private boolean explosion;


    /**
     *
     * @param x the horizontal position of a bomb on the map
     * @param y the vertical position of a bomb on the map
     * @param countdown the countdown timer of the bomb's detonation
     */

    public Bomb(int x, int y) {
        super(x, y);
        this.COUNTDOWN = 3;
        this.fuze = false;
        this.explosion = false;
        this.getSprite().addImageSet("tick", Sprite.createImageFade(
            "item/farron/farron0.png",
            "item/farron/farron5.png",
            12*3
        ));
        this.getSprite().setImage("item/farron/farron0.png");
    }

    public void detonateBomb() {
        ArrayList<Entity> allEntity = new ArrayList();
        ArrayList<Bomb> bombs = Game.getInstance().getEntities(Bomb.class);
        ArrayList<LandMover> landMovers = Game.getInstance().getEntities(LandMover.class);
        ArrayList<FlyingAssassin> flyingAssassins = Game.getInstance().getEntities(FlyingAssassin.class);
        ArrayList<PickUp> pickUps = Game.getInstance().getEntities(PickUp.class);


        allEntity.addAll(bombs);
        allEntity.addAll(landMovers);
        allEntity.addAll(flyingAssassins);
        allEntity.addAll(pickUps);

        Game.getInstance().removeEntity(this);

        for(Entity entity : allEntity) {
            if (entity.getX() == this.getX()){
                if(entity instanceof Bomb){
                    Bomb bomb = (Bomb) entity;
                    bomb.bombStart = Game.getInstance().getTime() + COUNTDOWN - ANIMATION_DURATION;
                    bomb.fuze = true;
                } else {
                    Game.getInstance().removeEntity(entity);
                }
            } else if (entity.getY() == this.getY()){
                if(entity instanceof Bomb){
                    Bomb bomb = (Bomb) entity;
                    bomb.bombStart = Game.getInstance().getTime() + COUNTDOWN - ANIMATION_DURATION;
                    bomb.fuze = true;
                } else {
                    Game.getInstance().removeEntity(entity);
                }
            }
        }
    }

    public void activateBomb() {
        double shakeAmountX = 0.05 * Math.sin(1.1 * COUNTDOWN * Math.pow(this.time, 3));
        double shakeAmountY = 0.05 * Math.sin(0.9 * COUNTDOWN * Math.pow(this.time, 3));
        this.setSpriteOffset(shakeAmountX, shakeAmountY);
        this.time += Game.getInstance().getDelta();
        this.getSprite().setAnimationSpeed(COUNTDOWN);
        this.getSprite().setImageSet("tick");
        this.getSprite().setAnimationType(AnimationType.SINGLE);

        if ((this.bombStart - COUNTDOWN) >= Game.getInstance().getTime()){
            detonateBomb();
        }
    }

    @Override
    public String toString(){
        return ("bomb " + getX() + " " + getY());
    }

    @Override
    protected void updateMovement() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void draw(Renderer renderer) {
        super.draw(renderer);

        if (this.explosion) {
            for(int x = 0; x < renderer.getViewWidth(); x++) {
                renderer.drawRect(x, this.getY(), 1, 0.1, Color.RED);
            }

            for(int y = 0; y < renderer.getViewHeight(); y++) {
                renderer.drawRect(this.getX(), y, 0.1, 1, Color.RED);
            }
        }
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

        if ((this.bombStart - COUNTDOWN + ANIMATION_DURATION) >= Game.getInstance().getTime()){
            explosion = true;
        }


}
}
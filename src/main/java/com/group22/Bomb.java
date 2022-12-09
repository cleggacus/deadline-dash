package com.group22;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Bomb extends Entity {

    private double countdown;
    private boolean fuze = false;
    private boolean explosion = false;
    private double time;
    private double shakeTimer;

    /**
     * @param x the horizontal position of a bomb on the map
     * @param y the vertical position of a bomb on the map
     * @param c the countdown timer of the bomb's detonation
     */

    public Bomb(int x, int y, double c) {
        super(x, y);
        this.countdown = 3;
        this.time = 0;
        this.shakeTimer = 0.1;

        this.getSprite().addImageSet("tick", Sprite.createImageFade(
                "item/farron/farron0.png",
                "item/farron/farron5.png",
                12 * 3
        ));
        this.getSprite().addImageSet("tick2", Sprite.createImageFade(
                "item/farron/farron0.png",
                "item/farron/farron4.png",
                12 * 3
        ));
        this.getSprite().setImage("item/farron/farron0.png");
        this.getSprite().setImageSet("tick2");
    }

    private void updateTime() {
        this.time += Game.getInstance().getDelta();
    }

    public void detonationAnimation() {
        ArrayList<Bomb> bombs = new ArrayList<>(Game.getInstance().getEntities(Bomb.class));

        for (Bomb bomb : bombs) {
            if (this.getX() == bomb.getX() || this.getY() == bomb.getY()) {
                bomb.explosion = true;
            }
        }
    }

    private void shake(double timeshake){
        double shakeAmountX = 0.05 * Math.sin(1.1 * timeshake * Math.pow(this.countdown, 3));
        double shakeAmountY = 0.05 * Math.sin(0.9 * timeshake * Math.pow(this.countdown, 3));

        this.setSpriteOffset(shakeAmountX, shakeAmountY);
        this.getSprite().setAnimationSpeed(shakeTimer);
        this.getSprite().setAnimationType(AnimationType.SINGLE);
    }

    public void detonateBomb() {
        ArrayList<Entity> allEntity = new ArrayList<>(Game.getInstance().getEntities(LandMover.class));
        allEntity.addAll(Game.getInstance().getEntities(Bomb.class));
        allEntity.addAll(Game.getInstance().getEntities(FlyingAssassin.class));
        allEntity.addAll(Game.getInstance().getEntities(PickUp.class));

        Game.getInstance().removeEntity(this);

        for (Entity entity : allEntity) {
            if (entity instanceof Bomb && entity.getDrawX() == this.getX()) {
                Bomb bomb = ((Bomb) entity);
                bomb.time = 3;
                bomb.fuze = true;
            } else if (entity.getX() == this.getX()) {
                Game.getInstance().removeEntity(entity);
            } else if (entity instanceof Bomb && entity.getY() == this.getY()) {
                Bomb bomb = ((Bomb) entity);
                bomb.time = 3;
                bomb.fuze = true;
            } else if (entity.getY() == this.getY()) {
                Game.getInstance().removeEntity(entity);
            }
        }
    }


    @Override
    public String toString() {
        return ("bomb " + getX() + " " + getY() + " " + (this.countdown - this.time));
    }

    @Override
    protected void updateMovement() {
        // TODO Auto-generated method stub

    }

    @Override
    public void draw(Renderer renderer) {
        super.draw(renderer);

        if (this.explosion) {
            for (int x = 0; x < renderer.getViewWidth(); x++) {
                renderer.drawRect(x, (this.getY() - 0.05), 1, 0.05, Color.RED);
            }
            for (int x = 0; x < renderer.getViewWidth(); x++) {
                renderer.drawRect(x, (this.getY() + 0.05), 1, 0.05, Color.RED);
            }
            for (int y = 0; y < renderer.getViewHeight(); y++) {
                renderer.drawRect((this.getX() - 0.05), y, 0.05, 1, Color.RED);
            }
            for (int y = 0; y < renderer.getViewHeight(); y++) {
                renderer.drawRect((this.getX() + 0.05), y, 0.05, 1, Color.RED);
            }
        }
    }

    @Override
    protected void update() {
        ArrayList<LandMover> landMovers = Game.getInstance().getEntities(LandMover.class);

        if (!fuze) {
            for (LandMover landMover : landMovers) {
                if (landMover.getX() == this.getX() && landMover.getY() == (this.getY() - 1)) {
                    this.fuze = true;
                } else if (landMover.getX() == this.getX() && landMover.getY() == (this.getY() + 1)) {
                    this.fuze = true;
                } else if (landMover.getX() == (this.getX() - 1) && landMover.getY() == (this.getY())) {
                    this.fuze = true;
                } else if (landMover.getX() == (this.getX() + 1) && landMover.getY() == (this.getY())) {
                    this.fuze = true;
                }
            }
        }
 
        if (fuze) {
            this.getSprite().setImageSet("tick");
            if(time >= countdown - 0.3){
                detonationAnimation();
            }
    
            if(time >= countdown){
                this.time = 3;
                detonateBomb();
            }
            this.shake(this.time);
            updateTime();
        }
    }
}
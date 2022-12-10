package com.group22;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Bomb extends Entity {

    private static final double ANIMATION_DURATION = 0.032;
    private final int countdown;
    private double time = 0;
    private double bombExpl;
    private boolean fuze;
    private boolean explosion;


    /**
     * @param x         the horizontal position of a bomb on the map
     * @param y         the vertical position of a bomb on the map
     * @param countdown the countdown timer of the bomb's detonation
     */
    public Bomb(int x, int y) {
        super(x, y);
        this.countdown = 3;
        this.fuze = false;
        this.explosion = false;
        this.getSprite().addImageSet("tick", Sprite.createImageFade(
                "item/farron/farron0.png",
                "item/farron/farron5.png",
                12 * 3
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
        for (Entity entity : allEntity) {
            if (entity.getX() == this.getX()) {
                if (entity instanceof Bomb) {
                    Bomb bomb = (Bomb) entity;
                    bomb.detonateBomb();
                } else {
                    Game.getInstance().removeEntity(entity);
                }
            } else if (entity.getY() == this.getY()) {
                if (entity instanceof Bomb) {
                    Bomb bomb = (Bomb) entity;
                    bomb.explosion = true;
                    bomb.detonateBomb();
                } else {
                    Game.getInstance().removeEntity(entity);
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

        ArrayList<Bomb> bombs = Game.getInstance().getEntities(Bomb.class);
        if (this.time >= countdown - ANIMATION_DURATION) {
            for (Bomb bomb : bombs) {
                if (bomb.getX() == this.getX()) {
                    bomb.explosion = true;
                } else if (bomb.getY() == this.getY()) {
                    bomb.explosion = true;
                }
            }
        }


        if (this.time >= countdown) {
            detonateBomb();
        }
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
            activateBomb();
        }

        if (this.time >= countdown - ANIMATION_DURATION){
            explosion = true;
        }

    }
}
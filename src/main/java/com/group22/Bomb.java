package com.group22;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.concurrent.*;

public class Bomb extends Entity {

    private static final int FRAME_OFFSET = 20;
    private double countdown;
    private double countUp = 0;
    private boolean fuze = false;
    private boolean explosion = false;
    private boolean doneOnce = false;
    private double time;
    private ScheduledExecutorService scheduler;


    /**
     * @param x the horizontal position of a bomb on the map
     * @param y the vertical position of a bomb on the map
     * @param c the countdown timer of the bomb's detonation
     */

    public Bomb(int x, int y, double c) {
        super(x, y);
        this.countdown = c;
        scheduler
                = Executors.newScheduledThreadPool(4);
        this.getSprite().addImageSet("tick", Sprite.createImageFade(
                "item/farron/farron0.png",
                "item/farron/farron5.png",
                12 * 3
        ));
        this.getSprite().setImage("item/farron/farron0.png");
    }

    public void countUp() {
        countUp += 0.0001;
    }

    public void detonationAnimation() {
        ArrayList<Bomb> bombs = new ArrayList<>(Game.getInstance().getEntities(Bomb.class));

        for (Bomb bomb : bombs) {
            if (this.getX() == bomb.getX() || this.getY() == bomb.getY()) {
                bomb.explosion = true;
            }
        }
    }

    private void shake(){
        double shakeAmountX = 0.05 * Math.sin(1.1 * this.countdown * Math.pow(this.time, 3));
        double shakeAmountY = 0.05 * Math.sin(0.9 * this.countdown * Math.pow(this.time, 3));

        this.setSpriteOffset(shakeAmountX, shakeAmountY);
        this.time += Game.getInstance().getDelta();
        this.getSprite().setAnimationSpeed(this.countdown);
        this.getSprite().setImageSet("tick");
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
                bomb.countUp = countdown;
            } else if (entity.getX() == this.getX()) {
                Game.getInstance().removeEntity(entity);
            } else if (entity instanceof Bomb && entity.getY() == this.getY()) {
                Bomb bomb = ((Bomb) entity);
                bomb.countUp = countdown;
            } else if (entity.getY() == this.getY()) {
                Game.getInstance().removeEntity(entity);
            }
        }
    }


    @Override
    public String toString() {
        return ("bomb " + getX() + " " + getY() + " " + (this.countdown - this.countUp));
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

        if (this.countdown - countUp <= 0.3) {
            detonationAnimation();
        }
        if (this.countUp >= this.countdown) {
            scheduler.shutdown();
            detonateBomb();
        }

        if (fuze) {
            this.shake();
            if (!doneOnce) {
                doneOnce = true;
                for (int i = (int) this.countdown * 10000 + FRAME_OFFSET; i >= 0; i--) {
                    scheduler.schedule(new Task(this), i * 100,
                            TimeUnit.MICROSECONDS);
                }
            }
        }
    }

    class Task implements Runnable {
        private Bomb bomb;

        public Task(Bomb bomb) {
            this.bomb = bomb;
        }

        public void run() {
            bomb.countUp();
        }
    }
}
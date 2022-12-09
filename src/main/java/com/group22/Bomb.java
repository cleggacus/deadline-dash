package com.group22;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.concurrent.*;

public class Bomb extends Entity{

<<<<<<< HEAD
    private static final int COUNTDOWN = 3;
    private int countUp = 0;
    private boolean fuze = false;
    private boolean explosion = false;
    private boolean doneOnce = false;
    private double time;
    private ScheduledExecutorService scheduler;
=======
    private static final double ANIMATION_DURATION = 0.032;
    private double countdown;
    private double time = 0;
    private double bombStart;
    private boolean fuze;
    private boolean explosion;
>>>>>>> 9beaa0d6f7937ed67fffed91acf59e637f0054a1


    /**
     *
     * @param x the horizontal position of a bomb on the map
     * @param y the vertical position of a bomb on the map
     * @param countdown the countdown timer of the bomb's detonation
     */

    public Bomb(int x, int y, double timeRemaining) {
        super(x, y);
<<<<<<< HEAD
        scheduler
            = Executors.newScheduledThreadPool(4);
=======
        this.explosion = false;
>>>>>>> 9beaa0d6f7937ed67fffed91acf59e637f0054a1
        this.getSprite().addImageSet("tick", Sprite.createImageFade(
            "item/farron/farron0.png",
            "item/farron/farron5.png",
            12*3
        ));
        this.getSprite().setImage("item/farron/farron0.png");
        
        if(timeRemaining < 3){
            this.countdown = timeRemaining;
            this.bombStart = Game.getInstance().getTime() + countdown - ANIMATION_DURATION;
            this.fuze = true;
        } else {
            this.countdown = 3;
            this.fuze = false;
        }
    }

    public void countUp(){
        countUp++;
    }
    public void detonateBomb() {
        ArrayList<Entity> allEntity = new ArrayList<>(Game.getInstance().getEntities(Bomb.class));
        allEntity.addAll(Game.getInstance().getEntities(LandMover.class));
        allEntity.addAll(Game.getInstance().getEntities(FlyingAssassin.class));
        allEntity.addAll(Game.getInstance().getEntities(PickUp.class));

        Game.getInstance().removeEntity(this);

        for(Entity entity : allEntity) {
            if (entity.getX() == this.getX()){
                if(entity instanceof Bomb){
                    Bomb bomb = (Bomb) entity;
<<<<<<< HEAD
                    bomb.countUp = COUNTDOWN;
=======
                    bomb.bombStart = Game.getInstance().getTime() + countdown - ANIMATION_DURATION;
>>>>>>> 9beaa0d6f7937ed67fffed91acf59e637f0054a1
                    bomb.fuze = true;
                } else {
                    Game.getInstance().removeEntity(entity);
                }
            } else if (entity.getY() == this.getY()){
                if(entity instanceof Bomb){
                    Bomb bomb = (Bomb) entity;
<<<<<<< HEAD
                    bomb.countUp = COUNTDOWN;
=======
                    bomb.bombStart = Game.getInstance().getTime() + countdown - ANIMATION_DURATION;
>>>>>>> 9beaa0d6f7937ed67fffed91acf59e637f0054a1
                    bomb.fuze = true;
                } else {
                    Game.getInstance().removeEntity(entity);
                }
            }
        }
    }

<<<<<<< HEAD
=======
    public void activateBomb() {
        double shakeAmountX = 0.05 * Math.sin(1.1 * countdown * Math.pow(this.time, 3));
        double shakeAmountY = 0.05 * Math.sin(0.9 * countdown * Math.pow(this.time, 3));
        this.setSpriteOffset(shakeAmountX, shakeAmountY);
        this.time += Game.getInstance().getDelta();
        this.getSprite().setAnimationSpeed(countdown);
        this.getSprite().setImageSet("tick");
        this.getSprite().setAnimationType(AnimationType.SINGLE);

        if ((this.bombStart - countdown) >= Game.getInstance().getTime()){
            detonateBomb();
        }
    }

>>>>>>> 9beaa0d6f7937ed67fffed91acf59e637f0054a1
    @Override
    public String toString(){
        return ("bomb " + getX() + " " + getY() + " " + (3 - this.time));
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
                    this.fuze = true;
                } else if (landMover.getX() == this.getX() && landMover.getY() == (this.getY()+1)) {
                    this.fuze = true;
                } else if (landMover.getX() == (this.getX()-1) && landMover.getY() == (this.getY())) {
                    this.fuze = true;
                } else if (landMover.getX() == (this.getX()+1) && landMover.getY() == (this.getY())) {
                    this.fuze = true;
                }
            }

        }

<<<<<<< HEAD
        if (countUp >= COUNTDOWN){
            scheduler.shutdown();
            this.explosion = true;
            detonateBomb();
=======
        if (fuze){
            activateBomb();
        }

        if ((this.bombStart - countdown + ANIMATION_DURATION) >= Game.getInstance().getTime()){
            explosion = true;
>>>>>>> 9beaa0d6f7937ed67fffed91acf59e637f0054a1
        }

        if (fuze && !doneOnce){
            doneOnce = true;
            double shakeAmountX = 0.05 * Math.sin(1.1 * COUNTDOWN * Math.pow(this.time, 3));
            double shakeAmountY = 0.05 * Math.sin(0.9 * COUNTDOWN * Math.pow(this.time, 3));
            this.setSpriteOffset(shakeAmountX, shakeAmountY);
            this.time += Game.getInstance().getDelta();
            this.getSprite().setAnimationSpeed(COUNTDOWN);
            this.getSprite().setImageSet("tick");
            this.getSprite().setAnimationType(AnimationType.SINGLE);
            for (int i = 3; i >= 0; i--) {
                scheduler.schedule(new Task(this), 4 - i,
                                   TimeUnit.SECONDS);        }

}
}

class Task implements Runnable {
    private Bomb bomb;
    public Task(Bomb bomb) { this.bomb = bomb; }
    public void run()
    {
        bomb.countUp();
    }
}
}
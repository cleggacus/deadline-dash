package com.group22;

import java.util.ArrayList;

import javafx.scene.paint.Color;

/**
 * {@code Bomb} is an extension of {@link Entity}.
 * {@code Bomb} can be interacted with by {@link LandMover} moving adjacent to a {@code Bomb},
 * upon which a 3-second countdown is begun. After which the {@code Bomb} will detonate
 * and an explosion along the {@code Bomb} X and Y position, removing any {@link Entity}
 * from the game, excluding {@link Door}. If another {@code Bomb} lies within the explosion,
 * that {@code Bomb} will immediately detonate.
 * @author Steffan William Borland
 * @version 1.0
 */
public class Bomb extends Entity {
    private static final double ANIMATION_DURATION = 0.2;
    private static final int COUNTDOWN = 3;
    private ArrayList<Laser> lasers = new ArrayList<>();
    private Countdown countdownEntity;
    private double time = 0;
    private boolean fuze;
    private boolean explosion;

    /**
     * {@code Countdown} is an extension of {@link Entity}.
     * It will create an additional sprite containing a number (time)
     * till a {@link Bomb} entity will explode in seconds.
     * @author Liam Clegg
     * @version 1.0
     */
    public class Countdown extends Entity {
        /**
         * @param bomb the bomb entity to create the countdown sprite for.
         */
        public Countdown(Bomb bomb) {
            super(bomb.getX(), bomb.getY());

            this.getSprite().addImageSet("tick", new String[] {
                "item/farron/countdown3.png",
                "item/farron/countdown2.png",
                "item/farron/countdown1.png"
            });
        }

        /**
         * When called will set the countdown sprite to the activated bomb.
         */
        public void activateCountdown() {
            this.getSprite().setAnimationSpeed(COUNTDOWN);
            this.getSprite().setImageSet("tick");
            this.getSprite().setAnimationType(AnimationType.SINGLE);
        }

        /**
         * This method is unused by Countdown.
         */
        @Override
        protected void updateMovement() {}

        /**
         * This method is unused by Countdown.
         */
        @Override
        protected void update() {} 
    }

    /**
     * {@code Laser} is an extension of {@link Entity}.
     * {@code Laser} is used to generate an animation that visualises a
     * {@link Bomb} explosion.
     * @author Liam Clegg
     * @version 1.0
     */
    public class Laser extends Entity {

        private double time = 0;
        private double lightTimer = 0;
        private int bombEdgeDistance;
        private Bomb bomb;
        private boolean isVertical;
        private boolean isHorizantal;
        private boolean isActive;

        /**
         * @param x the horizontal position of a laser on the map.
         * @param y the vertical position of a laser on the map.
         * @param bomb the bomb for which the laser is for.
         */
        public Laser(int x, int y, Bomb bomb) {
            super(x, y);
            this.bomb = bomb;
            this.isHorizantal = y == bomb.getY();
            this.isVertical = x == bomb.getX();
            boolean isCenter = this.isVertical && this.isHorizantal;

            String suffix = 
                isCenter ? "center" :
                isHorizantal ? "h" : "v";

            this.getSprite().setImage(
                "item/farron/lazer_" + suffix + ".png");

            int bombEdgeDistanceX = Math.max(
                Game.getInstance().getRenderer().getViewWidth() - 
                this.bomb.getX(), this.bomb.getX());

            int bombEdgeDistanceY = Math.max(
                Game.getInstance().getRenderer().getViewHeight() - 
                this.bomb.getY(), this.bomb.getY());

            this.bombEdgeDistance = Math.max(
                bombEdgeDistanceX, bombEdgeDistanceY);
        }

        /**
         * @param renderer
         * Will add a light effect to the laser as it travels if
         * the player has a torch.
         */
        @Override
        public void draw(Renderer renderer) {
            if (this.isActive) {
                super.draw(renderer);
                if (
                    Game.getInstance().getPlayer().hasTorch() &&
                    this.lightTimer <= ANIMATION_DURATION/bombEdgeDistance
                ) {
                    renderer.setLightPosition(
                        this.getDrawX(), 
                        this.getDrawY(),
                        0.5,
                        Color.PINK);
                }
            }
        }

        /**
         * This method is unused by Laser.
         */
        @Override
        protected void updateMovement() {}

        /**
         * Updates the time change since last frame in seconds, if the player
         * has a torch wil also update the lightTimer in seconds.
         */
        @Override
        protected void update() {
            this.time += Game.getInstance().getDelta();

            if (this.isActive) {
                this.lightTimer += Game.getInstance().getDelta();
            }
            
            this.updateShake();
            this.updateIsActive();
        }

        /**
         * Updates how much the laser animations shake.
         */
        private void updateShake() {
            double shakeAmount = 0.1*Math.sin(
                this.time * Math.PI * 2 * 
                (1/ANIMATION_DURATION) * 5);

            this.setSpriteOffset(
                isVertical ? shakeAmount : 0, 
                isHorizantal ? shakeAmount : 0);
        }

        /**
         * Updates the lasers for when they should/should not be active.
         */
        private void updateIsActive() {
            double animationTime = time / ANIMATION_DURATION;

            int bombDisplacementX = Math.abs(this.getX() - this.bomb.getX());
            int bombDisplacementY = Math.abs(this.getY() - this.bomb.getY());
            int bombDisplacement = bombDisplacementX + bombDisplacementY;

            double visibleFrame = 
                (double)bombDisplacement / (double)bombEdgeDistance;

            this.isActive = animationTime >= visibleFrame;
        }
    }

    /**
     * Creates a Bomb with X and Y position.
     * @param x the horizontal position of a bomb on the map
     * @param y the vertical position of a bomb on the map
     * @param c the amount of time elapsed since the bomb is triggered,
     *          default is 0 so when you save the bomb keeps its info.
     */
    public Bomb(int x, int y, double c) {
        super(x, y);
        this.countdownEntity = new Countdown(this);
        this.fuze = false;
        if (c > 0 && c < COUNTDOWN) {
            this.time = c;
            this.fuze = true;
            this.activateBomb();
        }
        this.explosion = false;
        this.getSprite().addImageSet("tick", Sprite.createImageFade(
            "item/farron/farron0.png",
            "item/farron/farron5.png",
            12 * 3
        ));
        this.getSprite().setImage("item/farron/farron0.png");
    }

    /** 
     * @return double time in seconds.
     */
    public double getTime() {
        return time;
    }

    /**
     * Creates an ArrayList of all entities that bomb can interact with (LandMover, Flying Assassin &
     * PickUps), checks whether these entities lie upon the bomb's X & Y axes. If that entity
     * is a bomb then it will cause that bomb to trigger its explosion animation,
     * as well as detonate. Otherwise, it will remove any entity that is lies upon the X & Y axes.
     */
    public void detonateBomb() {
        ArrayList<Entity> allEntity = new ArrayList<>();
        ArrayList<Bomb> bombs = Game.getInstance().getEntities(Bomb.class);
        ArrayList<LandMover> landMovers = 
            Game.getInstance().getEntities(LandMover.class);
        ArrayList<FlyingAssassin> flyingAssassins = 
            Game.getInstance().getEntities(FlyingAssassin.class);
        ArrayList<PickUp> pickUps = 
            Game.getInstance().getEntities(PickUp.class);

        allEntity.addAll(bombs);
        allEntity.addAll(landMovers);
        allEntity.addAll(flyingAssassins);
        allEntity.addAll(pickUps);

        Game.getInstance().removeEntity(this);

        for (Entity entity : allEntity) {
            if (entity.getX() == this.getX() ||
                entity.getY() == this.getY()) {

                if (entity instanceof Bomb bomb) {
                    bomb.explosion = true;
                    bomb.detonateBomb();
                } else {
                    Game.getInstance().removeEntity(entity);
                }
            }
        }
    }

    /**
     * Begins the activation for a bomb once its trigger condition is met,
     * and will trigger the explosion animation once enough time has elapsed
     * based on the COUNTDOWN of a bomb and the ANIMATION_DURATION. After which
     * it checks if any other bombs will be detonated and triggers their explosion
     * animations also.
     */
    public void activateBomb() {
        double shakeAmountX = 0.05 * 
            Math.sin(1.1 * COUNTDOWN * Math.pow(this.time, 3));

        double shakeAmountY = 0.05 * 
            Math.sin(0.9 * COUNTDOWN * Math.pow(this.time, 3));

        this.countdownEntity.activateCountdown();
        this.setSpriteOffset(shakeAmountX, shakeAmountY);
        this.time += Game.getInstance().getDelta();
        this.getSprite().setAnimationSpeed(COUNTDOWN);
        this.getSprite().setImageSet("tick");
        this.getSprite().setAnimationType(AnimationType.SINGLE);

        ArrayList<Bomb> bombs = Game.getInstance().getEntities(Bomb.class);

        if (this.time >= COUNTDOWN - ANIMATION_DURATION) {
            for (Bomb bomb : bombs) {
                if (
                    bomb.getX() == this.getX() || 
                    bomb.getY() == this.getY()
                ) {
                    bomb.explosion = true;
                }
            }
        }

        if (this.time >= COUNTDOWN) {
            detonateBomb();
        }
    }

    /** 
     * @param renderer
     * Draws the countdown sprite. Creates the lasers if they do not exist,
     * then draws the lasers.
     */
    @Override
    public void draw(Renderer renderer) {
        super.draw(renderer);

        this.countdownEntity.draw(renderer);

        if (this.explosion && this.lasers.isEmpty()) {
            createLasers();
        }

        for (Laser laser : lasers) {
            laser.draw(renderer);
        }
    }

    /**
     * This method is unused by Bomb.
     */
    @Override
    protected void updateMovement() {}

    /**
     * Checks every frame whether a bombs fuze has been triggered (LandMover
     * moving adjacent by 1 tile in X & Y directions). After which it will call
     * the activateBomb method for the activated bomb. Will set the bombs' explosion
     * animation once enough time has elapsed based on the COUNTDOWN of a bomb and
     * the ANIMATION_DURATION.
     */
    @Override
    protected void update() {
        for (Laser laser : lasers) {
            laser.callUpdate();
        }

        this.countdownEntity.callUpdate();

        ArrayList<LandMover> landMovers = 
            Game.getInstance().getEntities(LandMover.class);

        if (!fuze) {
            for (LandMover landMover : landMovers) {
                int displacementX = Math.abs(landMover.getX() - this.getX());
                int displacementY = Math.abs(landMover.getY() - this.getY());
                int displacement = displacementX + displacementY;

                if (displacement == 1) {
                    this.fuze = true;
                }
            }
        } else {
            activateBomb();
        }

        if (this.time >= COUNTDOWN - ANIMATION_DURATION) {
            this.explosion = true;
        }
    }

    /** 
     * @return String of bomb X position, Y position and the elapsed time
     * if it has been activated to be saved/loaded.
     */
    public String toString(){
        return ("bomb " + getX() + " " + getY() + " " + getTime());
    }

    /**
     * Creates the lasers for a bomb's detonation animation.
     */
    private void createLasers() {
        int width = Game.getInstance().getRenderer().getViewWidth();
        int height = Game.getInstance().getRenderer().getViewHeight();

        for (int x = 0; x < width; x++) {
            if (x != this.getX()) {
                this.lasers.add(
                    new Laser(x, this.getY(), this));
            }
        }

        for (int y = 0; y < height; y++) {
            if (y != this.getY()) {
                this.lasers.add(
                    new Laser(this.getX(), y, this));
            }
        }

        this.lasers.add(
            new Laser(this.getX(), this.getY(), this));
    }
}
package com.group22;

import java.util.ArrayList;

import javafx.scene.paint.Color;

/**
 * 
 */
public class Bomb extends Entity {
    private static final double ANIMATION_DURATION = 0.2;
    private static final int COUNTDOWN = 3;
    private ArrayList<Lazer> lazers = new ArrayList<>();
    private Countdown countdownEntity;
    private double time = 0;
    private boolean fuze;
    private boolean explosion;

    /**
     * 
     */
    public class Countdown extends Entity {
        /**
         * 
         * @param bomb
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
         * 
         */
        public void activateCountdown() {
            this.getSprite().setAnimationSpeed(COUNTDOWN);
            this.getSprite().setImageSet("tick");
            this.getSprite().setAnimationType(AnimationType.SINGLE);
        }

        /**
         * 
         */
        @Override
        protected void updateMovement() {}

        /**
         * 
         */
        @Override
        protected void update() {} 
    }

    /**
     * 
     */
    public class Lazer extends Entity {
        private double time = 0;
        private double lightTimer = 0;
        private int bombEdgeDistance;
        private Bomb bomb;
        private boolean isVertical;
        private boolean isHorizantal;
        private boolean isActive;

        /**
         * 
         * @param x
         * @param y
         * @param bomb
         */
        public Lazer(int x, int y, Bomb bomb) {
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
         * 
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
         * 
         */
        @Override
        protected void updateMovement() {}

        /**
         * 
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
         * 
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
         * 
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
     * @param x         the horizontal position of a bomb on the map
     * @param y         the vertical position of a bomb on the map
     * @param c         the amount of time elapsed since the bomb is triggered,
     *                  default is 0 so when you save the bomb keeps its info.
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
     * @return double
     */
    public double getTime() {
        return time;
    }

    /**
     * 
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

    /**
     * 
     */
    public void activateBomb() {
        this.countdownEntity.activateCountdown();

        double shakeAmountX = 0.05 * 
            Math.sin(1.1 * COUNTDOWN * Math.pow(this.time, 3));

        double shakeAmountY = 0.05 * 
            Math.sin(0.9 * COUNTDOWN * Math.pow(this.time, 3));

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
     */
    @Override
    public void draw(Renderer renderer) {
        super.draw(renderer);

        this.countdownEntity.draw(renderer);

        if (this.explosion && this.lazers.isEmpty()) {
            createLazers();
        }

        for (Lazer lazer : lazers) {
            lazer.draw(renderer);
        }

        // if (this.explosion) {
        //     for (int x = 0; x < renderer.getViewWidth(); x++) {
        //         renderer.drawRect(x, (this.getY() - 0.05), 1, 0.05, Color.RED);
        //         renderer.drawRect(x, (this.getY() + 0.05), 1, 0.05, Color.RED);
        //     }

        //     for (int y = 0; y < renderer.getViewHeight(); y++) {
        //         renderer.drawRect((this.getX() - 0.05), y, 0.05, 1, Color.RED);
        //         renderer.drawRect((this.getX() + 0.05), y, 0.05, 1, Color.RED);
        //     }
        // }
    }

    /**
     * 
     */
    @Override
    protected void update() {
        for (Lazer lazer : lazers) {
            lazer.callUpdate();
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
     *
     */
    @Override
    protected void updateMovement() {}

    
    /** 
     * @return String
     */
    public String toString(){
        return ("bomb " + getX() + " " + getY() + " " + getTime());
    }

    /**
     * 
     */
    private void createLazers() {
        int width = Game.getInstance().getRenderer().getViewWidth();
        int height = Game.getInstance().getRenderer().getViewHeight();

        for (int x = 0; x < width; x++) {
            if (x != this.getX()) {
                this.lazers.add(
                    new Lazer(x, this.getY(), this));
            }
        }

        for (int y = 0; y < height; y++) {
            if (y != this.getY()) {
                this.lazers.add(
                    new Lazer(this.getX(), y, this));
            }
        }

        this.lazers.add(
            new Lazer(this.getX(), this.getY(), this));
    }
}
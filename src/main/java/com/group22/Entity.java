package com.group22;



/**
 * 
 * The class {@code Entity} is used for anything 
 * that is drawn or updated in the {@code Engine}.
 * 
 * An extending class has to override the methods 
 * {@link #update()} and {@link #updateMovement()}.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public abstract class Entity {
    /** Time between moveUpdates in seconds. */
    protected double moveEvery = 0;

    /** Time since last move in seconds. */
    private double timeSinceMove = 0;

    private boolean shadow = false;

    /** Entity X position. */
    private int x = 0;

    /** Entity Y position. */
    private int y = 0;

    /** Offset Y position added to {@link #y} when drawing sprite. */
    private double spriteOffsetY = 0;

    /** Offset X position added to {@link #x} when drawing sprite. */
    private double spriteOffsetX = 0;

    /** Image sprites drawn to show for entity. */
    private Sprite sprite = new Sprite();

    private TransitionType animationType = TransitionType.NONE;

    /** X position the entitiy is animating from. */
    private double fromSpriteOffsetX = 0;
    /** Y position the entitiy is animating from. */
    private double fromSpriteOffsetY = 0;
    /** X position the entitiy is animating from. */
    private int fromX = 0;
    /** Y position the entitiy is animating from. */
    private int fromY = 0;



    /**
     * Creates an Entity.
     */
    public Entity(){}

    /**
     * Creates an Entity with X and Y
     * @param x the horizontal position of an entity on the map
     * @param y the vertical position of an entity on the map
     */
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Creates an Entity with X, Y and a sprite.
     */
    public Entity(int x, int y, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    /**
     * 
     * @return the X position of an entity.
     */
    public int getX() {
        return this.x;
    }

    /**
     * 
     * @return the Y position of an entity.
     */
    public int getY() {
        return this.y;
    }

    /**
     * 
     * @param shadow
     */
    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    /**
     * Publically exposed method which runs abstract update method.
     * This method is used by the engine to update the entities.
     */
    public void callUpdate() {
        this.sprite.update();
        this.update();
    }

    /**
     * 
     */
    public void resetMovementUpdate() {
        this.timeSinceMove = 0;
    }


    /**
     * Publically exposed method which runs abstract updateMovement method.
     * This method is used by the engine to update the movement of the entities.
     * This ensures that the entity only moves every {@link #moveEvery} seconds.
     */
    public void callUpdateMovement() {
        double delta = Game.getInstance().getDelta();
        this.timeSinceMove += delta;

        if (this.timeSinceMove >= this.moveEvery) {
            this.animationType = TransitionType.NONE;
            this.fromX = this.x;
            this.fromY = this.y;
            this.fromSpriteOffsetX = this.spriteOffsetX;
            this.fromSpriteOffsetY = this.spriteOffsetY;
            this.updateMovement();
            this.timeSinceMove -= this.moveEvery;
        }

        if (this.moveEvery <= delta){
            this.timeSinceMove = 0;
        }
    }

    
    /** 
     * Draws the {@link #sprite} at the position and draw offset of the entity.
     * 
     * @param renderer
     *      The renderer used to draw to.
     */
    public void draw(Renderer renderer) {
        if (this.sprite.getCurrentImage() == null) {
            return;
        }

        if (this.shadow) {
            renderer.drawShadow(getDrawX(), getDrawY(), 0.7);
        }

        renderer.drawImage(this.sprite.getCurrentImage(), 
            getDrawX(), getDrawY(), getDrawScale());
    }

    /**
     * 
     * @return
     */
    public double getDrawScale() {
        if (this.animationType == TransitionType.SCALE) {
            return Math.abs((this.timeSinceMove / this.moveEvery) * 2 - 1);
        } else {
            return 1;
        }
    }

    /**
     * Returns the sprite of the entitiy.
     * 
     * @return
     *      The current sprite element.
     */
    public Sprite getSprite() {
        return sprite;
    }


    /**
     * Moves the entitiy by (x, y) with a linear animation.
     * 
     * @param x
     *      How much to increment x position by.
     * 
     * @param y
     *      How much to increment y position by.
     */
    protected void move(int x, int y) {
        move(x, y, TransitionType.LINEAR);
    }


    /**
     * Moves the entitiy by (x, y) with a given animation.
     * 
     * @param x
     *      How much to increment x position by.
     * 
     * @param y
     *      How much to increment y position by.
     * 
     * @param type
     *      The transition aniamtion for the entity to move with.
     */
    protected void move(int x, int y, TransitionType type) {
        this.animationType = type;
        this.fromX = this.x;
        this.fromY = this.y;

        this.x += x;
        this.y += y;
    }

    /**
     * 
     * @param x
     * @param y
     */
    protected void setSpriteOffset(double x, double y) {
        this.fromSpriteOffsetY = this.spriteOffsetY;
        this.fromSpriteOffsetX = this.spriteOffsetX;
        this.spriteOffsetY = y;
        this.spriteOffsetX = x;
    }

    /**
     * This method needs to be overridden by extending {@code Entity}.
     * This is used for movement and is called every {@link #moveEvery} seconds.
     */
    protected abstract void updateMovement();


    /**
     * This method needs to be overridden by extending {@code Entity}.
     * This method is called every frame.
     */
    protected abstract void update();

    /**
     * Gets the Y position the renderer 
     * should render based on animation and offset.
     * 
     * @return
     *      Y position for renderering
     */
    public double getDrawY() {
        double percent = this.timeSinceMove / this.moveEvery;
        double offsetDistance = this.spriteOffsetY - this.fromSpriteOffsetY;
        double animOffsetY = this.fromSpriteOffsetY + offsetDistance*percent;

        switch(this.animationType) {
            case LINEAR:
                double distance = this.y - this.fromY;
                double animY = this.fromY + distance*percent;
                return animOffsetY + animY;
            case BOB:
                double period = 2;
                double amount = 0.1;
                double bobDistance = this.y - this.fromY;
                double bobAnimY = this.fromY + bobDistance*percent;
                return animOffsetY + bobAnimY + amount * 
                    Math.sin(2 * Math.PI * percent * period);
            case SCALE:
                return animOffsetY + (percent > 0.5 ? this.y : fromY);
            default:
                return this.spriteOffsetY + this.y;
        }
    }


    /**
     * Gets the X position the renderer should 
     * render based on animation and offset.
     * 
     * @return
     *      X position for renderering
     */
    public double getDrawX() {
        double percent = this.timeSinceMove / this.moveEvery;

        double offsetDistance = this.spriteOffsetX - this.fromSpriteOffsetX;
        double animOffsetX = this.fromSpriteOffsetX + offsetDistance * percent;

        switch(this.animationType) {
            case LINEAR: 
            case BOB: 
                double distance = this.x - this.fromX; 
                double animX = this.fromX + distance*percent; 
                return animOffsetX + animX;
            case SCALE:
                return animOffsetX + (percent > 0.5 ? this.x : fromX);
            default:
                return this.spriteOffsetX + this.x;
        }
    }
}

package com.group22;

/**
 * 
 * The class {@code Entity} is used for anything that is drawn or updated in the {@code Engine}.
 * 
 * An extending class has to override the methods {@link #update()} and {@link #updateMovement()}.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public abstract class Entity {
    /** Time between moveUpdates in seconds. */
    protected double moveEvery = 0;

    /** Time since last move in seconds. */
    protected double timeSinceMove = 0;

    /** Entity X position. */
    protected int x = 0;

    /** Entity Y position. */
    protected int y = 0;

    /** Offset Y position added to {@link #y} when drawing sprite. */
    protected double spriteOffsetY = 0;

    /** Offset X position added to {@link #x} when drawing sprite. */
    protected double spriteOffsetX = 0;

    /** Image sprites drawn to show for entity. */
    protected Sprite sprite = new Sprite();

    private AnimationType animationType = AnimationType.None;

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
     * Publically exposed method which runs abstract update method.
     * This method is used by the engine to update the entities.
     */
    public void callUpdate() {
        this.sprite.update();
        this.update();
    }


    /**
     * Publically exposed method which runs abstract updateMovement method.
     * This method is used by the engine to update the movement of the entities.
     * This ensures that the entity only moves every {@link #moveEvery} seconds.
     */
    public void callUpdateMovement() {
        double delta = Game.getInstance().getDelta();
        this.timeSinceMove += delta;

        if(this.timeSinceMove >= this.moveEvery) {
            this.animationType = AnimationType.None;
            this.updateMovement();
            this.timeSinceMove -= this.moveEvery;
        }

        if(this.moveEvery <= delta)
            this.timeSinceMove = 0;
    }

    
    /** 
     * Draws the {@link #sprite} at the position and draw offset of the entity.
     * 
     * @param renderer
     *      The renderer used to draw to.
     */
    public void draw(Renderer renderer) {
        if(this.sprite.getCurrentImage() == null)
            return;

        if(this.animationType == AnimationType.Scale) {
            double scale = Math.abs((this.timeSinceMove / this.moveEvery)*2-1);
            renderer.drawImage(this.sprite.getCurrentImage(), getDrawX(), getDrawY(), scale);
        } else {
            renderer.drawImage(this.sprite.getCurrentImage(), getDrawX(), getDrawY());
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
        move(x, y, AnimationType.Linear);
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
    protected void move(int x, int y, AnimationType type) {
        this.animationType = type;
        this.fromX = this.x;
        this.fromY = this.y;
        this.x += x;
        this.y += y;
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
     * Gets the Y position the renderer should renderer based on animation and offset.
     * 
     * @return
     *      Y position for renderering
     */
    private double getDrawY() {
        double percent = this.timeSinceMove / this.moveEvery;

        switch(this.animationType) {
            case Linear:
                double distance = this.y - this.fromY;
                double animY = this.fromY + distance*percent;
                return this.spriteOffsetY + animY;
            case Scale:
                return this.spriteOffsetY + (percent > 0.5 ? this.y : fromY);
            default:
                return this.spriteOffsetY + this.y;
        }
    }


    /**
     * Gets the X position the renderer should renderer based on animation and offset.
     * 
     * @return
     *      X position for renderering
     */
    private double getDrawX() {
        double percent = this.timeSinceMove / this.moveEvery;

        switch(this.animationType) {
            case Linear:
                double distance = this.x - this.fromX;
                double animX = this.fromX + distance*percent;
                return this.spriteOffsetX + animX;
            case Scale:
                return this.spriteOffsetX + (percent > 0.5 ? this.x : fromX);
            default:
                return this.spriteOffsetX + this.x;
        }
    }
}

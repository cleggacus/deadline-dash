package com.group22;

import java.util.HashMap;

import javafx.scene.image.Image;

/**
 * 
 * The class {@code Sprite} allows mutiple sets of images to be added to an objects with given names.
 * 
 * This class allows for animation and an abstraction for switching between the different image sets.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class Sprite {
    private double timeSinceLastSpriteFrame = 0;
    private double spriteAnimationSpeed = -1;
    private int currentFrame = 0;
    private String currentImageSet = "default";
    private HashMap<String, Image[]> images = new HashMap<>();

    /**
     * Creates a new Sprite.
     */
    public Sprite() {}

    /**
     * Gets the current image in the current selected set and frame.
     * 
     * @return
     *      The current image.
     */
    public Image getCurrentImage() {
        if(
            this.images.get(currentImageSet) == null || 
            this.images.get(currentImageSet).length == 0
        ) {
            return null;
        }

        if(currentFrame >= this.images.get(currentImageSet).length)
            currentFrame = 0;

        return this.images.get(currentImageSet)[currentFrame];
    }

    /**
     * Updates the current image to the next frame in the set based on the {@link #spriteAnimationSpeed}.
     * 
     */
    public void update() {
        if(spriteAnimationSpeed == -1)
            return;

        double delta = Game.getInstance().getDelta();
        this.timeSinceLastSpriteFrame += delta;

        if(this.timeSinceLastSpriteFrame >= this.spriteAnimationSpeed) {
            this.incrementSprite();
            this.timeSinceLastSpriteFrame -= this.spriteAnimationSpeed;
        }

        if(this.spriteAnimationSpeed <= delta)
            this.timeSinceLastSpriteFrame = 0;
    }

    /**
     * Increments the current frame in the current set by on and loops if its at the end of the set.
     * 
     */
    public void incrementSprite() {
        if(this.images.get(this.currentImageSet) == null)
            return;

        this.currentFrame++;

        if(this.currentFrame >= this.images.get(this.currentImageSet).length - 1)
            this.currentFrame = 0;
    }


    /**
     * Changes the currently loaded image set based on its name.
     * 
     * @param tag
     *      The desired image set tag.
     */
    public void setImageSet(String tag) {
        currentImageSet = tag;
    }

    /**
     * Adds a new image set with a given tag and an array of paths relative to the resources directory.
     * 
     * @param tag
     *      The name of the set.
     * 
     * @param paths
     *      The paths of the images to be added to the set.
     */
    public void addImageSet(String tag, String[] paths) {
        Image images[] = new Image[paths.length];

        for(int i = 0; i < paths.length; i++) {
            images[i] = new Image (getClass().getResource("/com/group22/" + paths[i]).toString());
        }

        addImageSet(tag, images);
    }


    /**
     * Adds a new image set with a given tag and an array of Image objects.
     * 
     * @param tag
     *      The name of the set.
     * 
     * @param images
     *      Array of image objects in set.
     */
    public void addImageSet(String tag, Image[] images) {
        this.images.put(tag, images);
    }

    /**
     * Removes an images set with a given name.
     * 
     * @param tag
     *      The name of the set to be removed.
     */
    public void removeImageSet(String tag) {
        this.images.remove(tag);
    }
    
    /**
     * If only one image is used in the sprite setImage will add a given image to a set of length one with the tag "default".
     * This method also will set the current image set to "default"
     * 
     * @param image
     *      The image object that will be used for the sprite.
     */
    public void setImage(Image image) {
        addImageSet("default", new Image[] { image });
        setImageSet("default");
    }

    /**
     * If only one image is used in the sprite setImage will add a given image to a set of length one with the tag "default".
     * This method also will set the current image set to "default"
     * 
     * @param image
     *      The path of the image relative to resources that will be used for the sprite.
     */
    public void setImage(String path) {
        addImageSet("default", new String[] { path });
        setImageSet("default");
    }

    /**
     * Sets the speed of the animation based on how many seconds between each frame.
     * 
     * @param speed
     *      The number of seconds between each frame.
     */
    public void setAnimationSpeed(double speed) {
        this.spriteAnimationSpeed = speed;
    }
}

package com.group22;

import java.util.HashMap;

import javafx.scene.image.Image;

public class Sprite {
    private double timeSinceLastSpriteFrame = 0;
    private double spriteAnimationSpeed = -1;
    private int currentFrame = 0;
    private String currentImageSet = "default";
    private HashMap<String, Image[]> images = new HashMap<>();

    public Sprite() {}

    public Image getCurrentImage() {
        if(this.images.get(currentImageSet) == null)
            return null;

        return this.images.get(currentImageSet)[currentFrame];
    }

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

    public void incrementSprite() {
        if(this.images.get(this.currentImageSet) == null)
            return;

        this.currentFrame++;

        if(this.currentFrame >= this.images.get(this.currentImageSet).length - 1)
            this.currentFrame = 0;
    }

    public void setImageSet(String tag) {
        currentImageSet = tag;
    }

    public void addImageSet(String tag, String[] paths) {
        Image images[] = new Image[paths.length];

        for(int i = 0; i < paths.length; i++) {
            images[i] = new Image (getClass().getResource("/com/group22/" + paths[i]).toString());
        }

        addImageSet(tag, images);
    }

    public void removeImageSet(String tag) {
        this.images.remove(tag);
    }

    public void addImageSet(String tag, Image[] images) {
        this.images.put(tag, images);
    }

    public void setImage(Image image) {
        addImageSet("default", new Image[] { image });
        setImageSet("default");
    }

    public void setImage(String path) {
        addImageSet("default", new String[] { path });
        setImageSet("default");
    }

    public void setAnimationSpeed(double speed) {
        this.spriteAnimationSpeed = speed;
    }
}

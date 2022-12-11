package com.group22;

import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * 
 * The class {@code Sprite} allows mutiple sets of images to be 
 * added to an objects with given names.
 * 
 * This class allows for animation and an abstraction for 
 * switching between the different image sets.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class Sprite {
    private static HashMap<String, Image> imageCache = new HashMap<>();
    private AnimationType animationType = AnimationType.INFINITE;
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
     * 
     * @param path
     * @return
     */
    public static Image imageFromPath(String path) {
        String resourcePath = Sprite.class.getResource(path).toString();

        Image image = imageCache.get(resourcePath);

        if (image != null) {
            return image;
        }

        image = new Image(resourcePath);
        imageCache.put(resourcePath, image);
        return image;
    }

    /**
     * Gets the current image in the current selected set and frame.
     * 
     * @return
     *      The current image.
     */
    public Image getCurrentImage() {
        if (this.images.get(currentImageSet) == null || 
            this.images.get(currentImageSet).length == 0) {
            return null;
        }

        if (currentFrame >= this.images.get(currentImageSet).length) {
            currentFrame = 0;
        }

        return this.images.get(currentImageSet)[currentFrame];
    }

    /**
     * Updates the current image to the next frame in the 
     * set based on the {@link #spriteAnimationSpeed}.
     * 
     */
    public void update() {
        if (spriteAnimationSpeed <= 0 || 
            this.animationType == AnimationType.NONE) {
            return;
        }

        double delta = Game.getInstance().getDelta();
        this.timeSinceLastSpriteFrame += delta;

        double frameSpeed = spriteAnimationSpeed / 
            (double) getCurrentImageCount();

        if (this.timeSinceLastSpriteFrame >= frameSpeed) {
            boolean loop = this.animationType == AnimationType.INFINITE;
            this.incrementSprite(loop);
            this.timeSinceLastSpriteFrame -= frameSpeed;
        }
    }

    /**
     * Increments the current frame in the current set by 
     * on and loops if its at the end of the set.
     * 
     */
    public void incrementSprite(boolean loop) {
        if (this.images.get(this.currentImageSet) == null) {
            return;
        }

        this.currentFrame++;

        if (this.currentFrame >= getCurrentImageCount()) {
            this.currentFrame = loop ? 0 : this.currentFrame - 1;
        }
    }

    
    /** 
     * @return int
     */
    public int getCurrentImageCount() {
        if (this.images.get(this.currentImageSet) == null) {
            return 0;
        }

        return this.images.get(this.currentImageSet).length;
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
     * Adds a new image set with a given tag and an array 
     * of paths relative to the resources directory.
     * 
     * @param tag
     *      The name of the set.
     * 
     * @param paths
     *      The paths of the images to be added to the set.
     */
    public void addImageSet(String tag, String[] paths) {
        Image images[] = new Image[paths.length];

        for (int i = 0; i < paths.length; i++) {
            images[i] = Sprite.imageFromPath(paths[i]);
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
     * If only one image is used in the sprite setImage will 
     * add a given image to a set of length one with the tag "default".
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
     * If only one image is used in the sprite setImage will 
     * add a given image to a set of length one with the tag "default".
     * This method also will set the current image set to "default"
     * 
     * @param image
     *      The path of the image relative to resources that 
     *      will be used for the sprite.
     */
    public void setImage(String path) {
        addImageSet("default", new String[] { path });
        setImageSet("default");
    }

    /**
     * Sets the speed of the animation based on 
     * how many seconds between each frame.
     * 
     * @param speed
     *      The number of seconds between each frame.
     */
    public void setAnimationSpeed(double speed) {
        this.spriteAnimationSpeed = speed;
    }

    
    /** 
     * @param animationType
     */
    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }

    
    /** 
     * @param applyColor
     * @return Image
     */
    public Image applyColor(Color applyColor) {
        Image loaded = this.getCurrentImage();

        PixelReader pixelReader = loaded.getPixelReader();
        WritableImage writableImage = new WritableImage(
            (int) loaded.getWidth(), (int) loaded.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < loaded.getHeight(); y++) {
            for (int x = 0; x < loaded.getWidth(); x++) {
                Color color = pixelReader.getColor(x, y);

                Color tint = new Color(
                    Math.min(1, 2 * applyColor.getRed() * color.getRed()),
                    Math.min(1, 2 * applyColor.getGreen() * color.getGreen()), 
                    Math.min(1, 2 * applyColor.getBlue() * color.getBlue()),
                    Math.min(1, color.getOpacity())
                );

                pixelWriter.setColor(x, y, tint);
            }
        }

        return this.images.get(currentImageSet)[currentFrame] = writableImage;
    }
    
    /** 
     * @param path1
     * @param path2
     * @param imageCount
     * @return Image[]
     */
    public static Image[] createImageFade(
        String path1, String path2, int imageCount) {
        Image image1 = Sprite.imageFromPath(path1);
        Image image2 = Sprite.imageFromPath(path2);
        return createImageFade(image1, image2, imageCount);
    }

    
    /** 
     * @param image1
     * @param image2
     * @param imageCount
     * @return Image[]
     */
    public static Image[] createImageFade(
        Image image1, Image image2, int imageCount) {
        Image[] images = new Image[imageCount];

        for (int i = 0; i < imageCount; i++) {
            images[i] = createFade(image1, image2, i / (double) imageCount);
        }

        return images;
    }

    
    /** 
     * @param image1
     * @param image2
     * @param amount
     * @return Image
     */
    private static Image createFade(Image image1, Image image2, double amount) {
        if (image1.getHeight() != image2.getHeight() || 
            image1.getWidth() != image2.getWidth()) {
            return image1;
        }

        PixelReader pixelReader1 = image1.getPixelReader();
        PixelReader pixelReader2 = image2.getPixelReader();

        int width = (int)image1.getWidth();
        int height = (int)image1.getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color1 = pixelReader1.getColor(x, y);
                Color color2 = pixelReader2.getColor(x, y);

                Color result = new Color(
                    color1.getRed() * (1 - amount) + 
                        color2.getRed() * amount, 
                    color1.getGreen() * (1 - amount) + 
                        color2.getGreen() * amount,
                    color1.getBlue() * (1 - amount) + 
                        color2.getBlue() * amount, 
                    color1.getOpacity() * (1 - amount) + 
                        color2.getOpacity() * amount
                );

                pixelWriter.setColor(x, y, result);
            }
        }

        return writableImage;

    }

}

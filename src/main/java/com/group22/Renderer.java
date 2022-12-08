package com.group22;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Light.Point;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

/**
 * 
 * The class {@code Renderer} is an abstraction for drawing tiles to the Graphics Context.
 * 
 * Renderer allows for images to be drawn at positions in a grid and be resized accoding to the canvas size.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class Renderer {
    private static final double DEFAULT_WIDTH = 15;
    private static final double DEFAULT_HEIGHT = 10;

    private ArrayList<Lighting> effects = new ArrayList<>();

    private double viewWidth = DEFAULT_WIDTH;
    private double viewHeight = DEFAULT_HEIGHT;

    private double topPadding = 0;
    private double rightPadding = 0;
    private double bottomPadding = 0;
    private double leftPadding = 0;

    private double tileSize;
    private double offsetX;
    private double offsetY;

    private GraphicsContext graphicsContext;

    /**
     * Creates a renderer.
     * 
     * @param graphicsContext
     */
    public Renderer(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        this.graphicsContext.setImageSmoothing(false);
    }

    
    /** 
     * Sets the width and height of the grid used for drawing images.
     * 
     * @param width 
     *      The new width in tiles.
     * 
     * @param height
     *      The new height in tiles.
     */
    public void setViewSize(int width, int height) {
        this.viewWidth = width;
        this.viewHeight = height;
    }

    
    /** 
     * Gets the height to the grid to render images.
     * 
     * @return
     *      The height in tiles of the grid.
     */
    public int getViewHeight() {
        return (int)viewHeight;
    }

    
    /** 
     * Gets the width to the grid to render images.
     * 
     * @return int
     *      The width in tiles of the grid.
     */
    public int getViewWidth() {
        return (int)viewWidth;
    }

    /**
     * Gets the amount the canvas is offset due to canvas and grid size.
     * 
     * @return
     *      Amount the grid is offset in the canvas on the x axis.
     */
    public double getOffsetX() {
        return Math.floor(offsetX);
    }


    /**
     * Gets the amount the canvas is offset due to canvas and grid size.
     * 
     * @return
     *      Amount the grid is offset in the canvas on the y axis.
     */
    public double getOffsetY() {
        return Math.floor(offsetY);
    }

    /**
     * Sets the padding that should be added to the canvas so empty space can be added around the grid.
     * 
     * @param top
     *      The number of pixels that should be added to the top of the grid.
     * 
     * @param right
     *      The number of pixels that should be added to the right of the grid.
     * 
     * @param bottom
     *      The number of pixels that should be added to the bottom of the grid.
     * 
     * @param left
     *      The number of pixels that should be added to the top left the grid.
     */
    public void setPadding(double top, double right, double bottom, double left) {
        this.topPadding = top;
        this.bottomPadding = bottom;
        this.leftPadding = left;
        this.rightPadding = right;
    }

    
    /** 
     * Draws an image at the position (x, y) in the grid at a scale of one tile.
     * 
     * @param image
     *      The image that will be drawn.
     * 
     * @param x
     *      The x tile position. 
     * 
     * @param y
     *      The y tile positoin.
     */
    public void drawImage(Image image, double x, double y) {
        this.drawImage(image, x, y, 1);
    }

    /**
     * Draws an image at the position (x, y) in the grid at a given scale proportional to the tile.
     * 
     * @param image
     *      The image that will be drawn.
     * 
     * @param x
     *      The x tile position. 
     * 
     * @param y
     *      The y tile positoin.
     * 
     * @param scale
     *      The scale the image should be drawn proportional to a tile.
     */
    public void drawImage(Image image, double x, double y, double scale) {
        double scaleOffset = (this.tileSize - this.tileSize * scale) / 2;
        double drawX = x*this.tileSize + this.offsetX + scaleOffset;
        double drawY = y*this.tileSize + this.offsetY + scaleOffset;

        this.graphicsContext.drawImage(
            image, 
            // 0, 0, 
            // image.getWidth(), image.getHeight(), 
            drawX, drawY, 
            tileSize * scale, tileSize * scale
        );
    }

    public void drawRect(double x, double y, double width, double height, Color color) {
        this.graphicsContext.setFill(color);

        width *= tileSize;
        height *= tileSize;

        double drawX = x*this.tileSize + this.offsetX + (tileSize - width)/2;
        double drawY = y*this.tileSize + this.offsetY + (tileSize - height)/2;

        this.graphicsContext.fillRect(drawX, drawY, width, height);
    }

    public void drawShadow(double x, double y, double scale) {
        double shadowOffset = 0.1;

        this.graphicsContext.setGlobalAlpha(0.5);

        this.drawImage(
            new Image(getClass().getResource("shadow.png").toString()), 
            x, y + shadowOffset);

        this.graphicsContext.setGlobalAlpha(1);
    }

    public void setLightPosition(double x, double y, double amount) {
        setLightPosition(x, y, amount, Color.rgb(255, 190, 158, 1));
    }

    public void setLightPosition(double x, double y, double amount, Color color) {
        Screen screen = Screen.getPrimary();
        double scaleX = screen.getOutputScaleX();
        double scaleY = screen.getOutputScaleY();

        x = (x+0.5)*this.tileSize + this.offsetX;
        y = (y+0.5)*this.tileSize + this.offsetY;

        x *= scaleX;
        y *= scaleY;

        Canvas canvas = graphicsContext.getCanvas();

        Light.Point light = new Light.Point();
        light.setX(x);
        light.setY(y);
        light.setZ(amount * 0.2 * Math.sqrt(scaleX * canvas.getWidth() * canvas.getHeight()));
        light.setColor(color);

        Lighting lighting = new Lighting();
        lighting.setLight(light);

        effects.add(lighting);
    }

    /**
     * To be run at the start of every frame.
     * Clears the canvas and updates the Renderer details if canvas has been resized
     */
    public void newFrame() {
        Canvas canvas = graphicsContext.getCanvas();

        if(canvas.getEffect() != null) {
            this.graphicsContext.setFill(Color.BLACK);
            this.graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        } else {
            this.graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }

        this.getViewInfo();
        this.drawOutline();


        if(effects.size() > 0) {
            Lighting brightest = effects.get(0);

            for(Lighting lighting : effects) {
                if(((Point)lighting.getLight()).getZ() > ((Point)brightest.getLight()).getZ()) {
                    brightest = lighting;
                }
            }

            // brightest.setSpecularExponent(10);

            Effect blend = this.effects.get(0);

            for(int i = 1; i < effects.size(); i++) {
                blend = new Blend(
                    BlendMode.ADD,
                    blend, 
                    this.effects.get(i));
            }

            canvas.setEffect(blend);
        }

        this.effects.clear();
    }

    private void drawOutline() {
        Image top = new Image(getClass().getResource("brick_top.png").toString());
        Image bottom = new Image(getClass().getResource("brick_bottom.png").toString());
        Image left = new Image(getClass().getResource("brick_left.png").toString());
        Image right = new Image(getClass().getResource("brick_right.png").toString());
        Image bottomLeft = new Image(getClass().getResource("brick_bottom_left.png").toString());
        Image bottomRight = new Image(getClass().getResource("brick_bottom_right.png").toString());

        for(int x = 0; x < this.viewWidth; x++) {
            this.drawImage(top, x, -1);
            this.drawImage(bottom, x, this.viewHeight);
        }

        for(int y = -1; y < this.viewHeight; y++) {
            this.drawImage(left, -1, y);
            this.drawImage(right, this.viewWidth, y);
        }

        this.drawImage(bottomRight, this.viewWidth, this.viewHeight);
        this.drawImage(bottomLeft, -1, this.viewHeight);
    }

    /**
     * Updates the info for resizing the canvas and setting the offsets.
     */
    private void getViewInfo() {
        Canvas canvas = graphicsContext.getCanvas();

        double canvasHeight = canvas.getHeight() - (topPadding + bottomPadding);
        double canvasWidth = canvas.getWidth() - (leftPadding + rightPadding);

        double viewWidth = this.viewWidth + (8/24.0);
        double viewHeight = this.viewHeight + (28/24.0);

        double canvasRatio = canvasWidth / canvasHeight;
        double viewRatio = viewWidth / viewHeight;


        if(canvasRatio > viewRatio) {
            this.tileSize = canvasHeight / viewHeight;
            this.offsetX = (canvasWidth - (this.tileSize * viewWidth)) / 2;
            this.offsetY = 0;
        } else {
            this.tileSize = canvasWidth / viewWidth;
            this.offsetX = 0;
            this.offsetY = (canvasHeight - (this.tileSize * viewHeight)) / 2;
        }

        this.offsetY += topPadding + tileSize;
        this.offsetX += leftPadding + tileSize * (4/24.0);
    }
}

package com.group22;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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
        return offsetX;
    }


    /**
     * Gets the amount the canvas is offset due to canvas and grid size.
     * 
     * @return
     *      Amount the grid is offset in the canvas on the y axis.
     */
    public double getOffsetY() {
        return offsetY;
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
        if(x < -1 || y < -1 || x > viewWidth || y > viewHeight)
            return;

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

    /**
     * To be run at the start of every frame.
     * Clears the canvas and updates the Renderer details if canvas has been resized
     */
    public void newFrame() {
        Canvas canvas = graphicsContext.getCanvas();

        this.graphicsContext.setFill(Color.BLACK);
        this.graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        this.getViewInfo();
    }

    /**
     * Updates the info for resizing the canvas and setting the offsets.
     */
    private void getViewInfo() {
        Canvas canvas = graphicsContext.getCanvas();
        double canvasHeight = canvas.getHeight() - (topPadding + bottomPadding);
        double canvasWidth = canvas.getWidth() - (leftPadding + rightPadding);

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

        offsetY += topPadding;
        offsetX += leftPadding;
    }
}

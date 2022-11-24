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
     * @param height
     */
    public void setViewSize(int width, int height) {
        this.viewWidth = width;
        this.viewHeight = height;
    }

    
    /** 
     * Gets the height to the grid to render images.
     * 
     * @return int
     */
    public int getViewHeight() {
        return (int)viewHeight;
    }

    
    /** 
     * Gets the width to the grid to render images.
     * 
     * @return int
     */
    public int getViewWidth() {
        return (int)viewWidth;
    }

    
    /** 
     * Draws an image at the position (x, y) in the grid.
     * 
     * @param image
     * @param x
     * @param y
     */
    public void drawImage(Image image, double x, double y) {
        if(x < -1 || y < -1 || x > viewWidth || y > viewHeight)
            return;

        double drawX = x*this.tileSize + this.offsetX;
        double drawY = y*this.tileSize + this.offsetY;

        this.graphicsContext.drawImage(
            image, 
            // 0, 0, 
            // image.getWidth(), image.getHeight(), 
            drawX, drawY, 
            tileSize, tileSize
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

    private void getViewInfo() {
        Canvas canvas = graphicsContext.getCanvas();
        double canvasRatio = canvas.getWidth() / canvas.getHeight();
        double viewRatio = viewWidth / viewHeight;

        if(canvasRatio > viewRatio) {
            this.tileSize = canvas.getHeight() / viewHeight;
            this.offsetX = (canvas.getWidth() - (this.tileSize * viewWidth)) / 2;
            this.offsetY = 0;
        } else {
            this.tileSize = canvas.getWidth() / viewWidth;
            this.offsetX = 0;
            this.offsetY = (canvas.getHeight() - (this.tileSize * viewHeight)) / 2;
        }
    }
}

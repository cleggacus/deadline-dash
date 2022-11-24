package com.gorup22;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Renderer {
    private static final double DEFAULT_WIDTH = 15;
    private static final double DEFAULT_HEIGHT = 10;

    private double viewWidth = DEFAULT_WIDTH;
    private double viewHeight = DEFAULT_HEIGHT;
    private double tileSize;
    private double offsetX;
    private double offsetY;

    private GraphicsContext graphicsContext;

    public Renderer(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        this.graphicsContext.setImageSmoothing(false);
    }

    public void setViewSize(int width, int height) {
        this.viewWidth = width;
        this.viewHeight = height;
    }

    public int getViewHeight() {
        return (int)viewHeight;
    }

    public int getViewWidth() {
        return (int)viewWidth;
    }

    public void drawImage(Image image, double x, double y) {
        if(x < 0 || y < 0 || x > viewWidth-1 || y > viewHeight-1)
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

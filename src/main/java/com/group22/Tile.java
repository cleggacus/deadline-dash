package com.group22;

import java.util.HashMap;
import java.util.Objects;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * 
 * The class {@code Tile} is an {@code Entity} which is used to render the tiles in the game.
 * 
 * The tile images are procedurally generated from 4 colors and a grey scale tile sprite.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class Tile extends Entity {
    private static final TileColor DEFAULT_TILE_COLOR = TileColor.BLUE;

    private static Image[] tileSprites;
    private static HashMap<TileLayout, Image> cachedSprites = new HashMap<>();

    private TileLayout tileLayout = new TileLayout();

    public class TileLayout {
        public TileColor topLeft = DEFAULT_TILE_COLOR;
        public TileColor topRight = DEFAULT_TILE_COLOR;
        public TileColor bottomLeft = DEFAULT_TILE_COLOR;
        public TileColor bottomRight = DEFAULT_TILE_COLOR;

        public void setValues(TileColor topLeft, TileColor topRight, TileColor bottomLeft, TileColor bottomRight) {
            this.topLeft = topLeft == null ? DEFAULT_TILE_COLOR : topLeft;
            this.topRight = topRight == null ? DEFAULT_TILE_COLOR : topRight;
            this.bottomLeft = bottomLeft == null ? DEFAULT_TILE_COLOR : bottomLeft;
            this.bottomRight = bottomRight == null ? DEFAULT_TILE_COLOR : bottomRight;
        }

        public boolean matches(TileLayout tileLayout) {
            char[] arr1 = this.toString().toCharArray();
            char[] arr2 = tileLayout.toString().toCharArray();

            for(char c1 : arr1) {
                for(char c2 : arr2) {
                    if(c1 == c2) {
                        return true;
                    }
                }
            }

            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(topLeft, topRight, bottomLeft, bottomRight);
        }

        @Override
        public String toString() {
            return 
                topLeft.toString() + 
                topRight.toString() +
                bottomLeft.toString() +
                bottomRight.toString();
        }
    }

    /**
     * Creates a tile based on its position and colors.
     * 
     * @param x
     *      X position of the tile
     * @param y
     *      Y position of the tile
     * @param colors
     *      The color of the tile
     */
    public Tile(int x, int y, String colors) {
        super(x, y);

        if(tileSprites == null)
            loadTileSprites();

        setTileLayout(colors);
    }

    /**
     * Creates a tile based on its position with a default color.
     * Note: do not run this if you are going to set TileLayout directly after since 2 images will have to be rendered slowing down performance.
     * 
     * @param x
     *      X position of the tile.
     * @param y
     *      Y position of the tile.
     */
    public Tile(int x, int y) {
        super(x, y);

        if(tileSprites == null)
            loadTileSprites();

        renderTileImage();
    }

    
    /** 
     * Sets the tile layout to the provided string of color labels set in {@code TileColor} and renders a sprite images correspondingly.
     * 
     * @param colors
     *      The colors of the tile given by a string containing each color tag.
     */
    public void setTileLayout(String colors) {
        if(colors.length() != 4)
            return;

        this.tileLayout.setValues(
            TileColor.getFromLabel(colors.charAt(0)),
            TileColor.getFromLabel(colors.charAt(1)),
            TileColor.getFromLabel(colors.charAt(2)),
            TileColor.getFromLabel(colors.charAt(3))
        );

        renderTileImage();
    }


    /**
     * Checks if theres a color in other thats in this.
     * 
     * @param other
     *      The tile to compare the colors to.
     * 
     * @return
     *      Weather there is a common color.
     */
    public boolean colorMatch(Tile other) {
        return this.tileLayout.matches(other.tileLayout);
    }

    public boolean hasColor(TileColor tileColor) {
        return 
            this.tileLayout.topLeft == tileColor ||
            this.tileLayout.topRight == tileColor ||
            this.tileLayout.bottomLeft == tileColor ||
            this.tileLayout.bottomRight == tileColor;
    }

    /**
     * Not implimented.
     */
    @Override
    protected void updateMovement() {}

    /**
     * Not implimented.
     */
    @Override
    protected void update() {}

    /**
     * Renders the image for the current tile based on its colors.
     * Used a sin wave to apply a color to the correct quadrant of the sprite.
     * 
     */
    private void renderTileImage() {
        Image cachedImage = Tile.cachedSprites.get(this.tileLayout);

        if(cachedImage != null) {
            this.getSprite().setImage(cachedImage);
            return;
        }

        Image sprite = getTileSprite();
        PixelReader pixelReader = sprite.getPixelReader();
        WritableImage writableImage = new WritableImage((int)sprite.getWidth(), (int)sprite.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < sprite.getHeight(); y++){
            for (int x = 0; x < sprite.getWidth(); x++){
                boolean top = isTopHalfTile(x, y, sprite.getWidth(), sprite.getHeight());
                boolean left = isLeftHalfTile(x, y, sprite.getWidth(), sprite.getHeight());

                TileColor c = 
                    top ? (
                        left ? tileLayout.topLeft : tileLayout.topRight
                    ) : left ? tileLayout.bottomLeft : tileLayout.bottomRight;

                Color color = pixelReader.getColor(x, y);

                Color tint = new Color(
                    c.color.getRed() * color.getRed(), 
                    c.color.getGreen() * color.getGreen(), 
                    c.color.getBlue() * color.getBlue(),
                    c.color.getOpacity() * color.getOpacity()
                );

                pixelWriter.setColor(x, y, tint);
            }
        }

        this.getSprite().setImage(writableImage);

        Tile.cachedSprites.put(this.tileLayout, writableImage);
    }

    
    /**
     * Gets the tile sprite that will be used at the tile position.
     * 
     * @return
     *      The image of the tile.
     */
    private Image getTileSprite() {
        int tileCount = tileSprites.length;
        int spriteIndex = ((this.getY()%tileCount) + (this.getX()%tileCount))%tileCount;
        return tileSprites[spriteIndex];
    }

    /**
     * Checks if the pixel position in a tile is in the top half when the top and bottom are sepereated by a sine wave.
     * 
     * @param x
     *      The x position of the pixel.
     * @param y
     *      The y position of the pixel.
     * @param tileWidth
     *      The width of the tile.
     * @param tileHeight
     *      The height of the tile.
     * @return
     *      Weather the pixel is in the top half.
     */
    private boolean isTopHalfTile(double x, double y, double tileWidth, double tileHeight) {
        double realY = tileHeight * 0.1 * Math.sin(Math.PI * 2 * x / tileWidth) + (tileHeight/2);
        return y < realY;
    }

    /**
     * Checks if the pixel position in a tile is in the left half when the top and bottom are sepereated by a vertical sine wave.
     * 
     * @param x
     *      The x position of the pixel.
     * @param y
     *      The y position of the pixel.
     * @param tileWidth
     *      The width of the tile.
     * @param tileHeight
     *      The height of the tile.
     * @return
     *      Weather the pixel is in the left half.
     */
    private boolean isLeftHalfTile(double x, double y, double tileWidth, double tileHeight) {
        double realX = tileWidth * 0.1 * Math.sin(Math.PI + Math.PI * 2 * y / tileHeight) + (tileWidth/2);
        return x < realX;
    }

    /**
     * Loads all the posible sprite images into a static varible so images only have to be loaded once for writing.
     */
    private void loadTileSprites() {
        tileSprites = new Image[] {
            Sprite.imageFromPath("tile0.png"),
            Sprite.imageFromPath("tile1.png"),
            Sprite.imageFromPath("tile2.png")
        };
    }
}


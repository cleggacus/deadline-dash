package com.group22;

import java.util.Arrays;
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
    private static Image[] tileSprites;

    private TileColor[] tileLayout = new TileColor[4];

    /**
     * Creates a tile based on its position and colors.
     * 
     * @param x
     * @param y
     * @param colors
     */
    public Tile(int x, int y, String colors) {
        this.x = x;
        this.y = y;

        if(tileSprites == null)
            loadTileSprites();

        setTileLayout(colors);
    }

    /**
     * Creates a tile based on its position with a default color.
     * Note: do not run this if you are going to set TileLayout directly after since 2 images will have to be rendered slowing down performance.
     * 
     * @param x
     * @param y
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;

        if(tileSprites == null)
            loadTileSprites();

        Arrays.fill(tileLayout, TileColor.DARK_BLUE);
        renderTileImage();
    }

    
    /** 
     * Sets the tile layout to the provided string of color labels set in {@code TileColor} and renders a sprite images correspondingly.
     * 
     * @param colors
     */
    public void setTileLayout(String colors) {
        if(colors.length() != 4)
            return;

        for(int i = 0; i < 4; i++) {
            TileColor tileColor = TileColor.getFromLabel(colors.charAt(i));
            this.tileLayout[i] = tileColor == null ? TileColor.DARK_BLUE : tileColor;
        }

        renderTileImage();
    }

    
    /** 
     * Sets the tile layout to the provided tile colors and renders a sprite images correspondingly. 
     * 
     * @param tl Top left TileColor
     * @param tr Top right TileColor
     * @param bl Bottom left TileColor
     * @param br Bottom rigtht TileColor
     */
    public void setTileLayout(TileColor tl, TileColor tr, TileColor bl, TileColor br) {
        this.tileLayout[0] = tl;
        this.tileLayout[1] = tr;
        this.tileLayout[2] = bl;
        this.tileLayout[3] = br;

        renderTileImage();
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

    private void renderTileImage() {
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
                        left ? tileLayout[0] : tileLayout[1]
                    ) : left ? tileLayout[2] : tileLayout[3];

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

        this.setSprite(writableImage);
    }

    
    private Image getTileSprite() {
        int tileCount = tileSprites.length;
        int spriteIndex = ((this.y%tileCount) + (this.x%tileCount))%tileCount;
        return tileSprites[spriteIndex];
    }

    private boolean isTopHalfTile(double x, double y, double tileWidth, double tileHeight) {
        double realY = tileHeight * 0.1 * Math.sin(Math.PI * 2 * x / tileWidth) + (tileHeight/2);
        return y >= realY;
    }

    private boolean isLeftHalfTile(double x, double y, double tileWidth, double tileHeight) {
        double realX = tileWidth * 0.1 * Math.sin(Math.PI + Math.PI * 2 * y / tileHeight) + (tileWidth/2);
        return x < realX;
    }

    private void loadTileSprites() {
        tileSprites = new Image[] {
            new Image(getClass().getResource("/com/group22/tile0.png").toString()),
            new Image(getClass().getResource("/com/group22/tile1.png").toString()),
            new Image(getClass().getResource("/com/group22/tile2.png").toString())
        };
    }
}


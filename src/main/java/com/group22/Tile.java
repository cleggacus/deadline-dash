package com.group22;

import java.util.Arrays;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Tile extends Entity{
    private static Image[] tileSprites;

    private TileColor[] tileLayout = new TileColor[4];

    public Tile(int x, int y, String colors) {
        this.x = x;
        this.y = y;

        if(tileSprites == null)
            loadTileSprites();

        setTileLayout(colors);
    }

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;

        if(tileSprites == null)
            loadTileSprites();

        Arrays.fill(tileLayout, TileColor.DARK_BLUE);
        renderTileImage();
    }

    public static Tile random(int x, int y) {
        Tile tile = new Tile(x, y);

        tile.setTileLayout(
            TileColor.getRandomColor(), 
            TileColor.getRandomColor(), 
            TileColor.getRandomColor(), 
            TileColor.getRandomColor()
        );

        return tile;
    }

    public void setTileLayout(String colors) {
        if(colors.length() != 4)
            return;

        for(int i = 0; i < 4; i++) {
            TileColor tileColor = TileColor.getFromLabel(colors.charAt(i));
            this.tileLayout[i] = tileColor == null ? TileColor.DARK_BLUE : tileColor;
        }

        renderTileImage();
    }

    public void setTileLayout(TileColor tl, TileColor tr, TileColor bl, TileColor br) {
        this.tileLayout[0] = tl;
        this.tileLayout[1] = tr;
        this.tileLayout[2] = bl;
        this.tileLayout[3] = br;

        renderTileImage();
    }

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

    @Override
    protected void updateMovement() {}

    @Override
    protected void update() {}
}


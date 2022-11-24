package com.group22;

import javafx.scene.paint.Color;

public enum TileColor {
    DARK_RED(Color.rgb(103, 28, 12, 1), 'R'), 
    LIGHT_RED(Color.rgb(230, 150, 100, 1), 'r'),
    LIGHT_BLUE(Color.rgb(200, 253, 255, 1), 'b'), 
    DARK_BLUE(Color.rgb(40, 60, 80, 1), 'B');

    public final Color color;
    public final char label;

    TileColor(Color color, char label){
        this.color = color;
        this.label = label;
    }

    public static TileColor getFromLabel(char label) {
        for (TileColor tileColor : TileColor.values()) {
            if(tileColor.label == label) {
                return tileColor;
            }
        }

        return null;
    }

    public static TileColor getRandomColor() {
        TileColor[] colors = TileColor.values();
        return colors[(int)(Math.random()*colors.length)];
    }
}

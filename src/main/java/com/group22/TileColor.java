package com.group22;

import javafx.scene.paint.Color;

/**
 * 
 * The enum {@code TileColor} is used to represent the tile colors and store the corrisponding color and label.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public enum TileColor {
    DARK_RED(Color.rgb(103, 28, 12, 1), 'R'), 
    LIGHT_RED(Color.rgb(230, 150, 100, 1), 'r'),
    LIGHT_BLUE(Color.rgb(200, 253, 255, 1), 'b'), 
    DARK_BLUE(Color.rgb(40, 60, 80, 1), 'B');

    /** Color of the TileColor */
    public final Color color;
    /** Label of the TileColor */
    public final char label;

    /**
     * Constructor for enum to store color and label
     * 
     * @param color
     * @param label
     */
    TileColor(Color color, char label){
        this.color = color;
        this.label = label;
    }

    /**
     * Gets the tile color with the corrisponding label
     * 
     * @param label
     * @return TileColor
     */
    public static TileColor getFromLabel(char label) {
        for (TileColor tileColor : TileColor.values()) {
            if(tileColor.label == label) {
                return tileColor;
            }
        }

        return null;
    }
}

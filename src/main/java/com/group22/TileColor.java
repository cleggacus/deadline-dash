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
    GREEN(Color.rgb(138, 244, 144, 1), 'G'), 
    RED(Color.rgb(244, 138, 160, 1), 'R'),
    YELLOW(Color.rgb(244, 223, 138, 1), 'Y'), 
    BLUE(Color.rgb(138, 160, 244, 1), 'B'),
    MAGENTA(Color.rgb(244, 138, 239, 1), 'M'), 
    CYAN(Color.rgb(138, 244, 223, 1), 'C');

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

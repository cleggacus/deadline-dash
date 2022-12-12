package com.group22;

/**
 * The {@code LevelFormatException} class extends {@code Exception}
 * to allow for custom exception handling for the {@link Level} 
 * and {@link LevelManager} classes.
 * 
 * @author Sam Austin
 * @version 1.1
 */
public class LevelFormatException extends Exception  {
    /**
     * 
     * @param message
     */
    public LevelFormatException(String message) {
        super(message);
    }
}

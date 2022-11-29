package com.group22;

import java.util.List;

/**
 * The Level class is a class that represents a level in the game. It contains the level's title, time
 * to complete, height, width, tiles, entities and scores and implements getters for all of the above.
 * 
 * @author Sam Austin
 * @version 1.0
 */
public class Level {
    private int timeToComplete;
    private String title;
    private int height;
    private int width;
    private Tile[][] tiles;
    private List<Entity> entities;
    private String[][] scores;
    

    public Level(String title, int timeToComplete, int height, int width,  Tile[][] tiles, List<Entity> entities, String[][] scores){
        this.title = title;
        this.timeToComplete = timeToComplete;
        this.height = height;
        this.width = width;
        this.tiles = tiles;
        this.entities = entities;
        this.scores = scores;
    }
    
    /** 
     * Getter for the level's title
     * @return String
     */
    public String getTitle(){
        return title;
    }

    
    /** 
     * Getter for the total time to complete the level at the begining
     * @return int
     */
    public int getTimeToComplete(){
        return timeToComplete;
    }

    
    /** 
     * This will change due to the requirement of player profiles.....
     * @return String[][]
     */
    public String[][] getHighscores(){
        return scores;
    }

    
    /** 
     * Getter for retrieving the Tile objects for the level
     * @return Tile[][]
     */
    public Tile[][] getTiles(){
        return tiles;
    }

    
    /** 
     * Getter for retrieving an ArrayList of entities. This may change
     * @return List<? super Entity>
     */
    public List<Entity> getEntities(){
        return entities;
    }

    
    /** 
     * Getter for retrieving the height of the level
     * @return int
     */
    public int getHeight(){
        return height;
    }

    
    /** 
     * Getter for retrieving the width of the level
     * @return int
     */
    public int getWidth(){
        return width;
    }

    /* setters to be implemented? */
}

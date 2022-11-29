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
    

    public Level(String title, int timeToComplete, int height, int width,  String[][] tiles, String[][] entities, String[][] scores){
        this.title = title;
        this.timeToComplete = timeToComplete;
        this.height = height;
        this.width = width;
        this.tiles = parseTiles(tiles);
        this.entities = parseEntities(entities);
        this.scores = scores;
    }

    
    /** 
     * Takes input from the LevelLoader class, and parses a 2D array of strings to a 2D array of Tile objects.
     * 
     * @param tiles
     * @return Tile[][]
     */
    private Tile[][] parseTiles(String[][] tiles){
        Tile[][] levelTiles = new Tile[width][height];

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                levelTiles[x][y] = new Tile(x, y, tiles[x][y]);
            }
        }
        return levelTiles;
    }

    
    /** 
     * Takes input from the LevelLoader class, and parses a 2D array of strings to an ArrayList of Entity objects.
     * 
     * @param entities
     * @return List<Entity>
     */
    private List<Entity> parseEntities(String[][] entities){
        for(int i=0; i<entities.length; i++){
            switch(entities[i][0]){
                case("player"):
                    /* Awaiting Player class implementation

                    this.entities.add(new Player(
                        Integer.parseInt(entities[i][1]),
                        Integer.parseInt(entities[i][2])
                        ));
                    */
                    break;
                case("door"):
                    /* Awaiting Door class implementation

                    this.entities.add(new Door(
                        Integer.parseInt(entities[i][1]),
                        Integer.parseInt(entities[i][2]),
                        new Image("door_closed.png")));
                    */
                    break;
                case("clock"):
                    this.entities.add(new Clock(
                        Integer.parseInt(entities[i][1]),
                        Integer.parseInt(entities[i][2]),
                        Integer.parseInt(entities[i][3])
                        ));
                    break;
                case("bomb"):
                    /* Awaiting Bomb class implementation

                    this.entities.add(new Bomb(
                        Integer.parseInt(entities[i][1]),
                        Integer.parseInt(entities[i][2]),
                        Integer.parseInt(entities[i][3])
                        ));
                    */
                    break;
                case("followingthief"):
                    /* Awaiting FollowingThief class implementation

                    this.entities.add(new FollowingThief(
                        Integer.parseInt(entities[i][1]),
                        Integer.parseInt(entities[i][2]),
                        new Sprite("character/following_thief.png"), //shouldn't there be a constructor without the sprite attribute for NPC's?
                        entities[i][3]
                        ));
                    
                    break;
                case("loot"):
                    /* Awaiting Loot class implementation

                    this.entities.add(new Loot(
                        Integer.parseInt(entities[i][1]),
                        Integer.parseInt(entities[i][2]),
                        new Sprite("item/{entities[i][0]}.png"),
                        Integer.parseInt(entities[i][3])
                        ));
                    */
                    break;

            }
        }

        return null;
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

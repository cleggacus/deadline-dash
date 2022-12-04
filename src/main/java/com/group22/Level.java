package com.group22;

import java.util.ArrayList;

/**
 * The Level class is a class that represents a level in the game. It contains the level's title, time
 * to complete, height, width, tiles, entities and scores and implements getters for all of the above.
 * 
 * @author Sam Austin
 * @version 1.0
 */
public class Level {
    private String title;
    private int timeToComplete;
    private int height;
    private int width;
    private Tile[][] tiles;
    private ArrayList<String[]> entities;
    private String[][] scores;
    private boolean playerPresent = false;
    private boolean doorPresent = false;
    

    public Level(String title, int timeToComplete, int height, int width,  Tile[][] tiles, ArrayList<String[]> entities, String[][] scores){
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
    public ArrayList<String[]> getEntities(){
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

    private void isValidEntity(Entity entity) throws Exception{
        if(entity instanceof Player){
            this.playerPresent = true;
        }
        if(entity instanceof Door){
            this.doorPresent = true;
        }
        if(entity.getX() > this.width-1)
            throw new LevelFormatException("Entity out of bounds in x");
        if(entity.getY() > this.height-1)
            throw new LevelFormatException("Entity out of bounds in y");
    }

    private void isValidLevel() throws LevelFormatException{
        if(!this.playerPresent)
            throw new LevelFormatException("Player not present for level " + this.getTitle());
        if(!this.doorPresent)
            throw new LevelFormatException("Door not present for level " + this.getTitle());
    }

    public ArrayList<Entity> createEntities() throws Exception {
        ArrayList<Entity> entities = new ArrayList<>();

        entities.addAll(this.tilesToEntities());
        
        for(String[] entityData : this.getEntities()) {
            Entity entity = parseEntity(entityData);
            isValidEntity(entity);
            entities.add(entity);
        }
        isValidLevel();
        
        return entities;
    }

    private ArrayList<Entity> tilesToEntities(){
        ArrayList<Entity> entities = new ArrayList<Entity>();

        for(Tile[] yTiles : tiles){
            for(Tile xTiles : yTiles){
                entities.add(xTiles);
            }
        }
        return entities;
    }

    private Entity parseEntity(String[] entity){
        switch(entity[0]){
            case("player"):
                return new Player(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]));
            case("door"):
                return new Door(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]));
            case("clock"):
                return new Clock(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    Integer.parseInt(entity[3])
                    );
            case("bomb"):
                return new Bomb(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    Integer.parseInt(entity[3])
                    );
            case("followingthief"):
                return new FollowingThief(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]), 
                    null);
            case("lever"):
                return new Lever(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    entity[3]);
            case("gate"):
                return new Gate(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    entity[3]);
            case("loot"):
                return new Loot(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    Integer.parseInt(entity[3]));
            case("flyingassassin"):
                return new FlyingAssassin(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    entity[3].equals("v"));
            case("smartmover"):
                return new SmartMover(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]));

        }
        return null;
    }
}

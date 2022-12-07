package com.group22;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * The {@code Level} class represents an instance of a level in the game.
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
    private int levelIndex;

    /**
     * Creates a new level from params listed.
     * 
     * @param title             level title
     * @param timeToComplete    time to complete level
     * @param height            height of the level in tiles
     * @param width             width of the level in tiles
     * @param tiles             2d array of the tiles
     * @param entities          ArrayList of strings of entities
     * @param scores            2d array of highscores for the level
     * @param levelIndex        the index of the level in the level file
     */
    public Level(String title, int timeToComplete, int height,
    int width, Tile[][] tiles, ArrayList<String[]> entities,
    String[][] scores, int levelIndex){
        this.title = title;
        this.timeToComplete = timeToComplete;
        this.height = height;
        this.width = width;
        this.tiles = tiles;
        this.entities = entities;
        this.scores = scores;
        this.levelIndex = levelIndex;
    }

    /**
     * This function loops through an ArrayList of Entities and returns the 
     * Player when found.
     * 
     * @param entities An ArrayList of all the entities in the level.
     * @return The player object.
     */
    public Player getPlayerFromEntities(ArrayList<Entity> entities){
        for(Entity entity : entities){
            if(entity instanceof Player){
                return (Player) entity;
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
     * Getter for the level index
     * @return String
     */
    public int getIndex(){
        return levelIndex;
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
     * Gets the position of the players score in the levels.txt file.
     * 
     * @param score The players score as an integer value.
     * @return The position of the score in the highscores array. 11 if 
     * the player didn't qualify for the top 10.
     */
    public int getScorePosition(int score){
        final String[][] LEVEL_HIGHSCORES = this.getHighscores();
        if(LEVEL_HIGHSCORES.length == 0){
            return 1;
        }
        for(int i = 0; i < 10; i++){
            if(LEVEL_HIGHSCORES.length == i)
                return i + 1;
            final int LEVEL_HIGHSCORE = Integer.parseInt(LEVEL_HIGHSCORES[i][1]);
            if(LEVEL_HIGHSCORE <= score)
                return i + 1;
        }
        return 11;
    }

    /**
     * Called when the player wins the level. If they haven't
     * unlocked any further levels, then this unlocks the next one.
     * If the score is in the top 10, then write the score to
     * the leaderboard for the level.
     * 
     * @param profile The profile of the player
     * @param score the score the player got
     */
    public void completeLevel(Profile profile, int score){
        final int PROFILE_UNLOCKED_INDEX = profile.getMaxUnlockedLevelIndex();
        if(PROFILE_UNLOCKED_INDEX == this.getIndex()){
            profile.setUnlockedLevelIndex(this.getIndex()+1);
        }
        final int SCORE_POS = this.getScorePosition(score);
        if(SCORE_POS <= 10){
            this.writeScore(profile, score, SCORE_POS);
        }
    }

    /**
     * It takes a profile, score, and score position, and writes the score to the level file.
     * If there is less than 10 scores for the level, it inserts at the end of the scores.
     * If there is 10 scores for the level, it inserts it to the correct position and moves
     * the rest of the scores down one, removing the 10th item.
     * 
     * @param profile The profile of the player who got the score
     * @param score the score to be written
     * @param scorePos The position in the high score list to insert the score
     */
    public void writeScore(Profile profile, int score, int scorePos){
        LevelLoader levelLoader = new LevelLoader();
        boolean scoreSet = false;
        int fileLevelIndex = 0;
        try{
            ArrayList<String> levelData = levelLoader.getLevelFileData();
            BufferedWriter wr = new BufferedWriter(
                new FileWriter(levelLoader.getLevelFile(), false));
            for(int i = 0; i < levelData.size(); i++){
                if(levelData.get(i).equals("-")){
                    fileLevelIndex = fileLevelIndex + 1;
                    if(fileLevelIndex > 0){
                        i += 1;
                    }
                }
                if(this.getIndex() == fileLevelIndex && !scoreSet){
                    final int TILE_NUM_INDEX = i + 2;
                    final int TILE_END_INDEX = Integer.parseInt(
                        levelData.get(TILE_NUM_INDEX).split(" ")[1])
                        + TILE_NUM_INDEX + 1;

                    final int ENTITY_END_INDEX = Integer.parseInt(
                        levelData.get(TILE_END_INDEX)) + TILE_END_INDEX;

                    final int NUM_SCORES_INDEX = ENTITY_END_INDEX + 1;
                    final int NUM_SCORES = Integer.parseInt(
                        levelData.get(NUM_SCORES_INDEX));

                    final String SCORE_TO_INSERT = profile.getName() + " "
                        + String.valueOf(score) + " " 
                        + LocalDateTime.now().toString();

                    if(NUM_SCORES == 10){
                        levelData.add(NUM_SCORES_INDEX + scorePos, 
                        SCORE_TO_INSERT);
                        levelData.remove(NUM_SCORES_INDEX + NUM_SCORES + 1);
                        scoreSet = true;
                    } else {
                        levelData.set(NUM_SCORES_INDEX,
                            String.valueOf(NUM_SCORES + 1));
                        levelData.add(NUM_SCORES_INDEX + scorePos,
                        SCORE_TO_INSERT);
                        scoreSet = true;
                    }
                }

            }
            for(int i = 0; i<levelData.size(); i++){
                wr.write(levelData.get(i) + "\n");
            }
            wr.close();

            } catch(Exception e){
                System.out.println(e.getMessage());

        }
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

    /**
     * It checks if the entity is a player or a door, and if it is, it sets the corresponding boolean
     * to true. Checks if the entity is out of bounds for the level and throws
     * a LevelFormatException exception if it is out of bounds.
     * 
     * @param entity The entity to be checked
     * @throws LevelFormatException
     */
    private void isValidEntity(Entity entity) throws LevelFormatException{
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

    /**
     * This function checks if the level has a player and a door.
     * If it doesn't, it throws a LevelFormatException exception.
     * 
     * @throws LevelFormatException
     */
    private void isValidLevel() throws LevelFormatException{
        if(!this.playerPresent)
            throw new LevelFormatException("Player not present for level "
            + this.getTitle());
        if(!this.doorPresent)
            throw new LevelFormatException("Door not present for level "
            + this.getTitle());
    }

    /**
     * It takes the data from the level file and creates the entities that
     * will be used in the game.
     * Calls {@code isValidEntity()} when the entity is created 
     * and {@code isValidLevel()} once all entities have been added to the
     * ArrayList entities.
     * 
     * @return An ArrayList of Entities.
     */
    public ArrayList<Entity> createEntities() throws LevelFormatException {
        ArrayList<Entity> entities = new ArrayList<>();

        entities.addAll(this.tilesToEntities());
        
        for(String[] entityData : this.getEntities()) {
            final Entity entity = parseEntity(entityData);
            isValidEntity(entity);
            entities.add(entity);
        }
        isValidLevel();
        
        return entities;
    }

    /**
     * It takes a 2D array of Tile objects
     * and returns the ArrayList of entities with these
     * tiles added to them.
     * 
     * @return An ArrayList of Entities.
     */
    private ArrayList<Entity> tilesToEntities(){
        ArrayList<Entity> entities = new ArrayList<Entity>();

        for(Tile[] yTiles : tiles){
            for(Tile xTiles : yTiles){
                entities.add(xTiles);
            }
        }
        return entities;
    }

    /**
     * It takes an array of strings, and returns an object of the type specified
     * by the first string in the array
     * 
     * @param entity the entity to be parsed
     * @return returns an Entity object.
     */
    private Entity parseEntity(String[] entity){
        switch(entity[0]){
            case("player"):
                return new Player(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    entity.length >= 4 && entity[3].equals("torch"));
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
                    TileColor.getFromLabel(entity[3].toCharArray()[0]),
                    Boolean.parseBoolean(entity[4]));
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
            default:
                return null;

        }
    }
}

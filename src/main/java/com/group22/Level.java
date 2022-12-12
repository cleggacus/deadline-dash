package com.group22;

import java.util.ArrayList;

/**
 * The {@code Level} class represents an instance of a level in the game.
 * 
 * @author Sam Austin
 * @version 1.1
 */
public class Level {
    private String title = "";
    private int timeToComplete = 0;
    private int height = 0;
    private int width = 0;
    private Tile[][] tiles = new Tile[0][0];
    private ArrayList<String[]> entities = new ArrayList<>();
    private ArrayList<Replay> replays = new ArrayList<>();
    private boolean playerPresent = false;
    private boolean doorPresent = false;    
    private int levelIndex = 0;
    private ReplayManager replayManager = new ReplayManager();

    /**
     * Creates a new level from params listed.
     * 
     * @param title             level title string
     * @param timeToComplete    time to complete level as double
     * @param height            height of the level in tiles as in
     * @param width             width of the level in tiles as int
     * @param tiles             2D array of {@link Tile} objects
     * @param entities          ArrayList of strings of entities
     * @param replays           ArrayList of {@link Replay}'s for the level
     * @param levelIndex        the index of the level in the level file
     */
    public Level(String title, int timeToComplete, 
        Tile[][] tiles, ArrayList<String[]> entities, 
        int levelIndex) {

        this.title = title;
        this.timeToComplete = timeToComplete;
        this.height = tiles[0].length;
        this.width = tiles.length;
        this.tiles = tiles;
        this.entities = entities;
        this.levelIndex = levelIndex;
    }

    
    /** 
     * Setter for the replays for the level
     * @param replays
     */
    public void setReplays(ArrayList<Replay> replays) {
        this.replays = replays;
    }

    /**
     * This function loops through an {@link ArrayList} of type {@link Entity}
     * and returns the {@link Player} when found.
     * 
     * @param entities An {@link ArrayList} of type {@link Entity}.
     * @return The {@link Player} object.
     */
    public Player getPlayerFromEntities(ArrayList<Entity> entities){
        for (Entity entity : entities) {
            if (entity instanceof Player) {
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
     * @return int
     */
    public int getIndex(){
        return levelIndex;
    }

    /**
     * Getter for the level's {@link Replay}'s.
     * @return {@link ArrayList} of type {@link Replay}'s
     */
    public ArrayList<Replay> getReplays() {
        return replays;
    }
    
    /** 
     * Getter for the total time to complete the level at the begining
     * @return int
     */
    public int getTimeToComplete() {
        return timeToComplete;
    }

    /**
     * Gets the position of the {@link Player}'s score in the
     * {@code levels.txt} file.
     * 
     * @param score The players score as an integer value.
     * @return The position of the score in the {@link Replay}'s
     * {@link ArrayList}, returns 11 if the player didn't
     * qualify for the top 10.
     */
    public int getScorePosition(int score) {
        if (this.replays.size() == 0) {
            return 1;
        }

        for (int i = 0; i < 10; i++) {
            if (this.replays.size() == i)
                return i + 1;
            final int LEVEL_HIGHSCORE = this.replays.get(i).getScore();
            if (LEVEL_HIGHSCORE <= score)
                return i + 1;
        }
        return 11;
    }

    /**
     * Called when the player wins the level. If they haven't
     * unlocked any further levels, then this unlocks the next one.
     * If the score is in the top 10, then write the replay to
     * the replays file.
     * 
     * @param profile The {@link Profile} of the user
     * @param score the score the user ended the level with
     */
    public void completeLevel(Profile profile, Replay replay, int score) {
        final int PROFILE_UNLOCKED_INDEX = profile.getMaxUnlockedLevelIndex();

        // unlock the next level for the current profile
        if (PROFILE_UNLOCKED_INDEX == this.getIndex()) {
            profile.setUnlockedLevelIndex(this.getIndex() + 1);
        }
        final int SCORE_POS = this.getScorePosition(score);

        // save the replay if in the top 10
        if (SCORE_POS <= 10) {
            this.replayManager.saveReplay(this, replay, score);
        }
    }
    
    /** 
     * Getter for retrieving the {@link Tile} objects for the level
     * @return Tile[][]
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    
    /** 
     * Getter for retrieving an {@link ArrayList} of a {@link String}
     * array of entity data.
     * @return {@link ArrayList} of a {@link String} array
     */
    public ArrayList<String[]> getEntities() {
        return entities;
    }

    
    /** 
     * Getter for retrieving the height of the level
     * @return int
     */
    public int getHeight() {
        return height;
    }

    
    /** 
     * Getter for retrieving the width of the level
     * @return int
     */
    public int getWidth() {
        return width;
    }


    /**
     * It checks if the entity is a player or a door, and if it is,
     * it sets the corresponding boolean to true. Checks if the
     * entity is out of bounds for the level and throws
     * a {@link LevelFormatException} if it is out of bounds.
     * 
     * @param entity The entity to be checked
     * @throws LevelFormatException
     */
    private void isValidEntity(Entity entity) throws LevelFormatException {
        if (entity instanceof Player) {
            this.playerPresent = true;
        }

        if (entity instanceof Door) {
            this.doorPresent = true;
        }

        if (entity.getX() > this.width - 1) {
            throw new LevelFormatException("Entity out of bounds in x");
        }

        if (entity.getY() > this.height - 1) {
            throw new LevelFormatException("Entity out of bounds in y");
        }
    }


    /**
     * This function checks if the level has a {@link Player}
     * and a {@link Door}. If it doesn't, it throws a
     * {@link LevelFormatException} exception.
     * 
     * @throws LevelFormatException
     */
    private void isValidLevel() throws LevelFormatException{
        if (!this.playerPresent)
            throw new LevelFormatException("Player not present for level "
            + this.getTitle());
        if (!this.doorPresent)
            throw new LevelFormatException("Door not present for level "
            + this.getTitle());
    }


    /**
     * It takes the data from the level file and creates an {@link ArrayList}
     * of {@link Entity}'s using the {@link tilesToEntities} function for
     * {@link Tile}'s and the {@link #parseEntity()} function for other
     * {@link Entity}'s.
     * Calls {@link #isValidEntity()} when the entity is created 
     * and {@link #isValidLevel()} once all entities have been added to the
     * ArrayList entities.
     * 
     * @return {@link ArrayList} of type {@link Entity}.
     * @throws LevelFormatException
     */
    public ArrayList<Entity> createEntities() throws LevelFormatException {
        ArrayList<Entity> entities = new ArrayList<>();

        entities.addAll(this.tilesToEntities());
        
        // for each entity, create it and check if it is valid
        for (String[] entityData : this.getEntities()) {
            final Entity entity = parseEntity(entityData);
            isValidEntity(entity);
            entities.add(entity);
        }

        // check the level is valid after creating all entities
        isValidLevel();
        
        return entities;
    }

    /**
     * It takes a 2D array of {@link Tile} objects from the level
     * and returns the {@link ArrayList} of type {@link Entity} with these
     * tiles added to them.
     * 
     * @return {@link ArrayList} of type {@link Entity}
     */
    private ArrayList<Entity> tilesToEntities() {
        ArrayList<Entity> entities = new ArrayList<Entity>();

        for (Tile[] yTiles : tiles) {
            for (Tile xTiles : yTiles) {
                entities.add(xTiles);
            }
        }
        return entities;
    }

    /**
     * Returns an ArrayList of Entity's of type Tile by
     * calling {@link tilesToEntities}
     * @return
     */
    public ArrayList<Entity> getTilesAsEntities() {
        return tilesToEntities();
    }


    /**
     * It takes an array of strings, and returns an {@link Entity} of the
     * type specified by the first string in the array. Performs error
     * checking on each entity to ensure all details are present.
     * 
     * @param entity the entity to be parsed as a String
     * @return returns an {@link Entity} object.
     * @throws LevelFormatException
     */
    public Entity parseEntity(String[] entity) 
        throws LevelFormatException {
        
        /* 
         * Switch through all possible entities, throw an exception
         * if not in the list.
         */
        switch(entity[0]){
            case("player"):
                // throw exception if formatted incorrectly in the level file
                if (entity.length < 3) 
                    throw new LevelFormatException(
                        "player has " 
                        + (entity.length < 3 ? "too few" : "too many")
                        + " applicable attributes in " + this.getTitle());

                // return the new object
                return new Player(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    entity.length >= 4 && entity[3].equals("torch"));
            case("door"):
                if (entity.length != 3) 
                    throw new LevelFormatException(
                        "door has " 
                        + (entity.length < 3 ? "too few" : "too many")
                        + " applicable attributes in " + this.getTitle());

                return new Door(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2])
                    );

            case("clock"):
                if (entity.length != 4)
                    throw new LevelFormatException(
                        "clock has " 
                        + (entity.length < 4 ? "too few" : "too many")
                        + " applicable attributes in " + this.getTitle());

                return new Clock(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    Integer.parseInt(entity[3])
                    );

            case("bomb"):
                if (entity.length != 4)
                    throw new LevelFormatException(
                        "bomb has" 
                        + (entity.length < 4 ? "too few" : "too many")
                        + " applicable attributes in " + this.getTitle());

                return new Bomb(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    Double.parseDouble(entity[3]));

            case("followingthief"):
                if (entity.length != 5)
                    throw new LevelFormatException(
                        "followingthief has" 
                        + (entity.length < 5 ? "too few" : "too many")
                        + " applicable attributes in " + this.getTitle());

                return new FollowingThief(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]), 
                    TileColor.getFromLabel(entity[3].toCharArray()[0]),
                    Boolean.parseBoolean(entity[4]));

            case("lever"):
                if (entity.length != 4)
                    throw new LevelFormatException(
                        "lever has" 
                        + (entity.length < 4 ? "too few" : "too many")
                        + " applicable attributes in " + this.getTitle());

                return new Lever(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    entity[3]);

            case("gate"):
                if (entity.length != 5)
                    throw new LevelFormatException(
                        "gate has" 
                        + (entity.length < 5 ? "too few" : "too many")
                        + " applicable attributes in " + this.getTitle());

                return new Gate(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    entity[3],
                    Boolean.parseBoolean(entity[4]));

            case("loot"):
                if (entity.length != 4)
                    throw new LevelFormatException(
                        "loot has" 
                        + (entity.length < 4 ? "too few" : "too many")
                        + " applicable attributes in " + this.getTitle());

                return new Loot(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    Integer.parseInt(entity[3]));

            case("flyingassassin"):
                if (entity.length != 4)
                    throw new LevelFormatException(
                        "flyingassassin has" 
                        + (entity.length < 4 ? "too few" : "too many")
                        + " applicable attributes in " + this.getTitle());

                return new FlyingAssassin(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]),
                    entity[3].equals("v"));

            case("smartmover"):
            if (entity.length != 3)
                throw new LevelFormatException(
                    "smartmover has" 
                    + (entity.length < 3 ? "too few" : "too many")
                    + " applicable attributes in " + this.getTitle());

                return new SmartMover(
                    Integer.parseInt(entity[1]),
                    Integer.parseInt(entity[2]));
                    
            default:
                throw  new LevelFormatException(
                    "Entity name is not applicable."
                );
        }
    }
}

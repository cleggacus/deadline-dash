package com.group22;

import java.time.LocalDateTime;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.scene.input.KeyCode;

/**
 * The {@code Game} class acts as a game manager handling all the game logic.
 * Since there is only one game and it extends Engine it uses the singleton pattern and can be used with the {@link #getInstance()} method.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class Game extends Engine {
    private double time;
    private int score; 

    private Tile[][] tiles;
    private Entity player;
    private List<Level> levels;
    private int currentLevelIndex;
    private LevelLoader levelLoader;
    private Level level;
    private Profile profile;
    private ReplayManager replayManager;
    private ReplayFrame currentFrame;
    private double timeElapsed;
    private Replay replay;
    private int framesElapsed;
    private boolean replaying;

    private static Game instance;

    /**
     * Creates Game.
     * Set to private so multiple instances cannot be created.
     */
    private Game() {
        super();
    }

    /** 
     * This method is used so the public game data can be accessed
     * in other parts of the applicaiton. The instance of Game is created
     * on its first call and is returned in the method.
     * 
     * @return Game
     */
    
    public static synchronized Game getInstance() {
        if(Game.instance == null) {
            Game.instance = new Game();
            Game.instance.onInitialized();
        }

        return Game.instance;
    }
    
    public void newReplayFrame(int x, int y, double keyTime){
        ReplayFrame frame = new ReplayFrame(x, y, keyTime);
        this.replay.storeFrame(frame);
        currentFrame = frame;

    }

    public void saveReplay(){
        this.replayManager.saveReplay(replay);
    }

    public Entity getPlayer() {
        // Returning the player entity.
        return this.player;
    }

    /**
     * Checks if theres a color at (x1, y1) thats in (x2, y2).
     * 
     * @param x1 The x coordinate of the first tile
     * @param y1 The y-coordinate of the first tile
     * @param x2 The x coordinate of the second tile
     * @param y2 The y coordinate of the second tile
     * @return
     */
    public boolean colorMatch(int x1, int y1, int x2, int y2) {
        return this.tiles[x1][y1].colorMatch(this.tiles[x2][y2]);
    }

    public boolean tileHasColor(int x, int y, TileColor color) {
        return this.tiles[x][y].hasColor(color);
    }

    /**
     * This function adds the given number of seconds to the time variable
     * 
     * @param seconds The amount of time to add to the timer.
     */
    public void addTime(double seconds) {
        this.time += seconds;
    }

    /**
     * This function returns the score of the player
     * 
     * @return The score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * This function returns the time left of a level
     * 
     * @return The time variable is being returned.
     */
    public double getTime() {
        return time;
    }

    /**
     * This function returns the first door in the list of entities
     * 
     * @return The door entity.
     */
    public double getTimeElapsed() {
        return timeElapsed;
    }

    public int getFramesElapsed() {
        return framesElapsed;
    }

    public Entity getDoor() {
        for(Entity entity : this.entities){
            if(entity instanceof Door){
                return entity;
            }
        }
        return null;
    }

    /**
     * This function takes an integer as an argument and
     * adds it to the score variable
     * 
     * @param amount The amount to increment the score by.
     */
    public Level getLevel() {
        return this.level;
    }

    public void incrementScore(int amount) {
        this.setScore(this.score + amount);
    }

    /**
     * This function sets the score parameter in the game class
     * 
     * @param score the score to be set
     */
    public void setScore(int score) {
        this.score = score;
        this.getGamePane().getPlaying().setGameScore(score);
    }

    @Override
    /**
     *  Sets the score to 0, sets the level to the one selected
     *  sets the width and height, sets the time, and
     *  adds the entities.
     */
    protected void startReplay(Replay replay, int levelIndex) {
        this.replaying = true;
        this.setScore(0);

        Level currentLevel = this.levels.get(levelIndex);
        this.level = currentLevel;

        int width = currentLevel.getWidth();
        int height = currentLevel.getHeight();

        this.getGamePane().getPlaying().setGameLevel(currentLevel.getTitle());

        this.tiles = currentLevel.getTiles();
        this.time = currentLevel.getTimeToComplete();
        this.timeElapsed = 0;
        this.framesElapsed = 0;

        this.setViewSize(width, height);
        try {
            this.addEntities(currentLevel.createEntities());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Player player = this.level.getPlayerFromEntities(this.entities);
        ReplayPlayer replayPlayer =  new ReplayPlayer(player.getX(), player.getY(),  replay.getFrames());
        this.addEntity(replayPlayer);
        this.removeEntity(player);
        

    }

    @Override
    protected void start() {
        this.replaying = false;

        this.setScore(0);

        Level currentLevel = this.levels.get(this.currentLevelIndex);
        this.level = currentLevel;

        int width = currentLevel.getWidth();
        int height = currentLevel.getHeight();

        this.getGamePane().getFinish().setIsLastLevel(this.isLastLevel());
        this.getGamePane().getPlaying().setGameLevel(currentLevel.getTitle());

        this.tiles = currentLevel.getTiles();
        this.time = currentLevel.getTimeToComplete();
        this.timeElapsed = 0;
        this.framesElapsed = 0;

        this.setViewSize(width, height);

        try {
            this.addEntities(currentLevel.createEntities());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.replayManager = new ReplayManager();
        this.replay = new Replay(this.level.getTitle(), this.getUsername());
    }

    public void setGameOver(){
        this.getGamePane().getGameOver().setStats(this.score, this.time);
        this.setGameState(GameState.GameOver);
    }

    public void setLevelFinish(){
        this.getGamePane().getFinish().setStats(this.score, this.time);
        this.level.completeLevel(this.getProfile(), this.score);
        this.setProfile(this.getProfile());
        this.setGameState(GameState.LevelComplete);
    }

    /**
     * Overridden update method from {@code Engine}.
     */
    @Override
    protected void update() {
        updateTime();
        this.getGamePane().getPlaying().setGameScore(this.score);
        if(this.level.getPlayerFromEntities(this.getEntities()) == null){
            if(!this.replaying)
                this.setGameOver();
        }
    }

    /**
     * This function updates the time in the game
     * and if the time is less than or equal to 0, it sets
     * the game state to GameOver.
     */
    private void updateTime() {
        this.time -= this.getDelta();
        this.timeElapsed += this.getDelta();
        this.framesElapsed += 1;

        if(this.time <= 0) {
            this.time = 0;
            this.setGameState(GameState.GameOver);
        }

        this.getGamePane().getPlaying().setGameTime(this.time);
    }

    /**
     * This function sets the current level index 
     * to the level passed in, and sets the game state to
     * playing
     * 
     * @param level The level to start from.
     */
    public void startFromLevel(int level){
        this.currentLevelIndex = level;
        this.setGameState(GameState.Playing);
    }

    public void startFromNextLevel() {
        this.currentLevelIndex++;
        this.setGameState(GameState.Playing);
    }

    public boolean isLastLevel() {
        return this.currentLevelIndex >= this.levels.size() - 1;
    }

    private void onInitialized() {
        this.setUpProfiles();
    }

    /**
     * It loads all the levels from the levelLoader
     * and adds them to the levelSelector
     */
    private void setUpLeveles() {
        this.levelLoader = new LevelLoader();
        this.levelLoader.setUp();
        this.levels = Game.instance.levelLoader.getAllLevels();
        this.getGamePane().getLevelSelector().addLevels(this.levels);
    }

    private void setUpProfiles() {
        Profile checkProfiles = new Profile();

        this.getGamePane().getProfileSelector().setProfileAddedEvent(username -> {
            Profile profile = new Profile(username, LocalDateTime.now());

            if(!profile.exists()){
                profile.saveToFile();
            } else {
                profile.updateTimeActive();
            }
        });

        this.getGamePane().getProfileSelector().setOnProfileRemoved(username -> {
            Profile deleteProfile = new Profile();
            deleteProfile.delete(username);
        });

        this.getGamePane().getProfileSelector().setOnProfileSelectEvent(username -> {
            checkProfiles.loadAllProfiles();
            Profile profile = checkProfiles.getFromName(username);
            this.setProfile(profile);
        });

        List<Profile> profiles = checkProfiles.getAllProfiles();

        for(Profile profile : profiles) {
            this.getGamePane().getProfileSelector().addProfile(profile.getName());
        }
    }

    private void setProfile(Profile profile) {
        this.profile = profile;
        this.getGamePane().getLevelSelector().clearLevels();
        this.getGamePane().getLevelSelector().setProfile(profile);
        this.getGamePane().getStartMenu().setUsername(profile.getName());
        this.setUpLeveles();
    }

    public Profile getProfile(){
        return this.profile;
    }
}

package com.group22;

import java.time.LocalDateTime;
import java.util.List;

import com.group22.gui.ProfileSelector;

/**
 * The {@code Game} class acts as a game manager handling all the game logic.
 * Since there is only one game and it extends Engine it uses the singleton 
 * pattern and can be used with the {@link #getInstance()} method.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class Game extends Engine {
    private double time;
    private int score; 
    private Tile[][] tiles;
    private List<Level> levels;
    private int currentLevelIndex;
    private Level level;
    private Profile profile;
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
        if (Game.instance == null) {
            Game.instance = new Game();
            Game.instance.onInitialized();
        }

        return Game.instance;
    }
    
    
    /** 
     * @param x
     * @param y
     * @param keyTime
     */
    public void newReplayFrame(int x, int y, double keyTime) {
        ReplayFrame frame = new ReplayFrame(x, y, keyTime);
        this.replay.storeFrame(frame);

    }

    
    /** 
     * @return Player
     */
    public Player getPlayer() {
        return this.getEntities(Player.class).get(0);
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

    
    /** 
     * @param x
     * @param y
     * @param color
     * @return boolean
     */
    public boolean tileHasColor(int x, int y, TileColor color) {
        return this.tiles[x][y].hasColor(color);
    }

    
    /** 
     * @param x
     * @param y
     */
    public void lightUpTile(int x, int y) {
        this.tiles[x][y].lightUp();
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
     * @return int
     */
    public int getFramesElapsed() {
        return framesElapsed;
    }

    
    /** 
     * @return Door
     */
    public Door getDoor() {
        return this.getEntities(Door.class).get(0);
    }


    
    /** 
     * @return Level
     */
    public Level getLevel() {
        return this.level;
    }

    /**
     * This function takes an integer as an argument and
     * adds it to the score variable
     *
     * @param amount The amount to increment the score by.
     */
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
        this.replay = replay;
        this.setScore(0);

        Level currentLevel = this.levels.get(levelIndex);
        this.level = currentLevel;

        int width = currentLevel.getWidth();
        int height = currentLevel.getHeight();

        this.getGamePane().getPlaying().setGameLevel(currentLevel.getTitle());

        this.tiles = currentLevel.getTiles();
        this.time = currentLevel.getTimeToComplete();
        this.framesElapsed = 0;

        this.setViewSize(width, height);

        try {
            this.addEntities(currentLevel.createEntities());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Player player = this.level.getPlayerFromEntities(this.entities);
        ReplayPlayer replayPlayer =  
            new ReplayPlayer(player.getX(), player.getY(),  replay.getFrames());
        this.addEntity(replayPlayer);
        this.removeEntity(player);

    }

    
    /** 
     * @param savedState
     */
    @Override
    protected void startSavedState(SavedState savedState) {
        this.replaying = false;
        this.setScore(savedState.getScore());
        Level currentLevel = this.levels.get(savedState.getLevelIndex());
        this.level = currentLevel;

        int width = currentLevel.getWidth();
        int height = currentLevel.getHeight();

        this.getGamePane().getLevelComplete()
            .setIsLastLevel(this.isLastLevel());
        this.getGamePane().getPlaying().setGameLevel(currentLevel.getTitle());

        this.tiles = currentLevel.getTiles();
        this.time = savedState.getTime();
        this.framesElapsed = 0;

        this.setViewSize(width, height);

        try {
            this.addEntities(savedState.getEntities());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.replay = new Replay(this.level.getTitle(), this.getUsername());
    }


    /**
     * 
     */
    @Override
    protected void start() {
        this.replaying = false;

        this.setScore(0);

        Level currentLevel = this.levels.get(this.currentLevelIndex);
        this.level = currentLevel;

        int width = currentLevel.getWidth();
        int height = currentLevel.getHeight();

        this.getGamePane().getLevelComplete()
            .setIsLastLevel(this.isLastLevel());
        this.getGamePane().getPlaying().setGameLevel(currentLevel.getTitle());

        this.tiles = currentLevel.getTiles();
        this.time = currentLevel.getTimeToComplete();
        this.framesElapsed = 0;

        this.setViewSize(width, height);

        try {
            this.addEntities(currentLevel.createEntities());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.replay = new Replay(this.level.getTitle(), this.getUsername());
    }


    /**
     * 
     */
    public void setGameOver() {
        this.getGamePane().getGameOver().setStats(this.score, this.time);
        this.setGameState(GameState.GAME_OVER);
    }


    /**
     * 
     */
    public void setReplayOver() {
        this.getGamePane().getReplayOver().setStats(
            replay.getScore(), this.time);

        this.setGameState(GameState.REPLAY_OVER);
    }

    /**
     * 
     */
    public void setLevelFinish(){
        if (this.replaying) {
            setReplayOver();
        } else {
            this.getGamePane().getLevelComplete().setStats(
                this.score, this.time);
            this.level.completeLevel(this.profile, replay, score);
            this.setProfile(this.getProfile());
            this.setGameState(GameState.LEVEL_COMPLETE);
        }
    }

    /**
     * Overridden update method from {@code Engine}.
     */
    @Override
    protected void update() {
        updateTime();
        this.getGamePane().getPlaying().setGameScore(this.score);
        Player player = this.level.getPlayerFromEntities(this.getEntities());

        if (player == null && !this.replaying) {
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
        this.framesElapsed += 1;

        if (this.time <= 0) {
            this.time = 0;
            this.setGameState(GameState.GAME_OVER);
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
    public void startFromLevel(int level) {
        this.currentLevelIndex = level;
        this.setGameState(GameState.PLAYING);
    }

    public void startFromNextLevel() {
        this.currentLevelIndex++;
        this.setGameState(GameState.PLAYING);
    }

    
    /** 
     * @return boolean
     */
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
        this.levels = LevelManager.getInstance().getAllLevels();
        this.getGamePane().getLevelSelector().addLevels(this.levels);
    }

    /**
     * 
     */
    private void setUpProfiles() {
        Profile checkProfiles = new Profile();
        ProfileSelector profileSelector = 
            this.getGamePane().getProfileSelector();

        profileSelector.setProfileAddedEvent(username -> {
                Profile profile = new Profile(username, LocalDateTime.now());

                if (!profile.exists()){
                    profile.saveToFile();
                } else {
                    profile.updateTimeActive();
                }
            });

        profileSelector.setOnProfileRemoved(username -> {
                Profile deleteProfile = new Profile();
                deleteProfile.delete(username);
            });

        profileSelector.setOnProfileSelectEvent(username -> {
            checkProfiles.loadAllProfiles();
            Profile profile = checkProfiles.getFromName(username);
            this.setProfile(profile);
        });

        List<Profile> profiles = checkProfiles.getAllProfiles();

        for (Profile profile : profiles) {
            profileSelector.addProfile(profile.getName());
        }
    }

    
    /** 
     * @param profile
     */
    private void setProfile(Profile profile) {
        this.profile = profile;
        this.getGamePane().getLevelSelector().clearLevels();
        this.getGamePane().getLevelSelector().setProfile(profile);
        this.getGamePane().getStartMenu().setUsername(profile.getName());
        this.setUpLeveles();
    }

    
    /** 
     * @return Profile
     */
    public Profile getProfile() {
        return this.profile;
    }

    /**
     * 
     */
    public void saveState() {
        SavedStateManager stateManager = new SavedStateManager();

        stateManager.createState(
            getLevel().getTitle(), 
            getUsername(), 
            getEntities(), 
            score, 
            time, 
            getLevel().getIndex());
    }
    
}

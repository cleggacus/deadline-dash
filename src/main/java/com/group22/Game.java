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
    private static Game instance;

    private double time;
    private int score; 
    private Tile[][] tiles;
    private List<Level> levels;
    private Level level;
    private Profile profile;
    private Replay replay;
    private SavedState savedState;
    private int framesElapsed;

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
     * @return Player
     */
    public Player getPlayer() {
        return this.getEntities(Player.class).get(0);
    }

    public String getUsername() {
        return this.getGamePane().getProfileSelector().getUsername();
    }

    public Tile getTile(int x, int y) {
        return this.tiles[x][y];
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

    private void setupLevel() {
        this.setScore(0);

        int width = this.level.getWidth();
        int height = this.level.getHeight();

        this.getGamePane().getLevelComplete()
            .setIsLastLevel(this.isLastLevel());

        this.getGamePane()
            .getPlaying().setGameLevel(this.level.getTitle());

        this.tiles = this.level.getTiles();
        this.time = this.level.getTimeToComplete();
        this.framesElapsed = 0;

        this.setViewSize(width, height);

        try {
            if (savedState != null) {
                this.addEntities(this.savedState.getEntities());
            } else {
                this.addEntities(this.level.createEntities());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startPlaying() {
        this.getGamePane().getLevelComplete()
            .setIsLastLevel(this.isLastLevel());

        if (this.savedState != null) {
            this.setScore(savedState.getScore());
            this.time = savedState.getTime();
        }

        this.replay = new Replay(this.level.getTitle(), this.getUsername());
    }

    /**
     *  Sets the score to 0, sets the level to the one selected
     *  sets the width and height, sets the time, and
     *  adds the entities.
     */
    private void startReplaying() {
        Player player = this.level.getPlayerFromEntities(getEntities());

        ReplayPlayer replayPlayer =  
            new ReplayPlayer(player.getX(), player.getY(),  replay.getFrames());

        this.addEntity(replayPlayer);
        this.removeEntity(player);
    }

    /**
     * 
     */
    @Override
    protected void start() {
        this.setupLevel();

        // Playing and replaying starts
        switch (this.getGameState()) {
            case PLAYING:
                startPlaying();
                break;
            case REPLAYING:
                startReplaying();
                break;
            default:
                break;
        }
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
        if (this.getGameState() == GameState.REPLAYING) {
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

        if (player == null && this.getGameState() != GameState.REPLAYING) {
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
        this.savedState = null;
        this.level = this.levels.get(level);
        this.setGameState(GameState.PLAYING);
    }

    /** 
     * @param savedState
     */
    public void startSavedState(SavedState savedState) {
        this.savedState = savedState;
        this.replay = new Replay(this.level.getTitle(), this.getUsername());
        this.setGameState(GameState.PLAYING);
    }

    public void replayFromLevel(int level, Replay replay) {
        this.savedState = null;
        this.level = this.levels.get(level);
        this.replay = replay;
        this.setGameState(GameState.REPLAYING);
    }

    public void startFromNextLevel() {
        if (!this.isLastLevel()) {
            int newLevelIndex = this.levels.indexOf(this.level) + 1;
            this.level = this.levels.get(newLevelIndex);
            this.setGameState(GameState.PLAYING);
        }
    }

    /** 
     * @return boolean
     */
    public boolean isLastLevel() {
        return this.levels.indexOf(this.level) >= this.levels.size() - 1;
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

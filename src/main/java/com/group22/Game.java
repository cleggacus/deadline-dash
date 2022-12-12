package com.group22;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import com.group22.gui.ProfileSelector;

/**
 * The {@code Game} class acts as a game manager handling all the game logic.
 * Since there is only one game and it extends Engine it uses the singleton 
 * pattern and can be used with the {@link #getInstance()} method.
 * 
 * @author Liam Clegg
 * @version 1.1
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
     * Gets the player entity in the currently loaded level.
     * 
     * @return the player in the current level.
     */
    public Player getPlayer() {
        return this.getEntities(Player.class).get(0);
    }


    /**
     * Gets the tile entity at a given coordinate.
     * 
     * @param x tile x position
     * @param y tile y position
     * @return the tile at postiion (x, y)
     */
    public Tile getTile(int x, int y) {
        return this.tiles[x][y];
    }

    /** 
     * Gets the door entity in the currently loaded level.
     * 
     * @return the door in the current level.
     */
    public Door getDoor() {
        return this.getEntities(Door.class).get(0);
    }

    /**
     * This method returns the score of the player
     * 
     * @return The score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * This method returns the time left of a level
     * 
     * @return The time variable is being returned.
     */
    public double getTime() {
        return time;
    }
    
    /** 
     * Gets the current profile
     * 
     * @return current profile
     */
    public Profile getProfile() {
        return this.profile;
    }

    /**
     * Saves the current state of the game.
     */
    public void saveState() {
        SavedStateManager stateManager = new SavedStateManager();

        stateManager.createState(
            this.level.getTitle(), 
            getProfile().getName(),
            getEntities(), 
            score, 
            time, 
            this.level.getIndex());
    }
    
    /** 
     * Adds a new frame to the current replay instance.
     * Used to construct the game replay.
     * 
     * @param frame the frame to be added to replay.
     */
    public void addFrameToReplay(ReplayFrame frame) {
        this.replay.storeFrame(frame);
    }

    /**
     * This methods adds the given number of seconds to the time variable
     * 
     * @param seconds The amount of time to add to the timer.
     */
    public void addTime(double seconds) {
        this.time += seconds;
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

    /**
     * This method takes an integer as an argument and
     * adds it to the score variable
     *
     * @param amount The amount to increment the score by.
     */
    public void incrementScore(int amount) {
        this.setScore(this.score + amount);
    }

    /**
     * Sets state to game over at the end of game.
     */
    public void setGameOver() {
        File musicFile = 
    new File("src/main/resources/com/group22/music/resuscitation-123871.mp3");
        MusicManager.setTrack(musicFile);
        MusicManager.playOnRepeat();
        this.getGamePane().getGameOver().setStats(this.score, this.time);
        this.setGameState(GameState.GAME_OVER);
    }

    /**
     * Sets state to replay over at the end of a replay.
     */
    public void setReplayOver() {
        this.getGamePane().getReplayOver().setStats(
            replay.getScore(), this.time);

        this.setGameState(GameState.REPLAY_OVER);
    }

    /**
     * Sets state to level finish when level has been completed
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
     * This function sets the current level index to that of the saved state
     * loaded and sets the game state to playing
     * @param savedState The save state being loaded.
     */
    public void startSavedState(SavedState savedState) {
        this.savedState = savedState;
        this.level = this.levels.get(savedState.getLevelIndex());
        this.setGameState(GameState.PLAYING);
    }

    /**
     * This function sets the replay of a level and then sets
     * the gamestate to replaying
     * @param level The level being replayed.
     * @param replay The replay of that level
     */
    public void replayFromLevel(int level, Replay replay) {
        this.savedState = null;
        this.level = this.levels.get(level);
        this.replay = replay;
        this.setGameState(GameState.REPLAYING);
    }

    /**
     * Upon completion of a level you can access the level's
     * successor as long as it is not the last level.
     */
    public void startFromNextLevel() {
        if (!this.isLastLevel()) {
            int newLevelIndex = this.levels.indexOf(this.level) + 1;
            this.level = this.levels.get(newLevelIndex);
            this.setGameState(GameState.PLAYING);
        }
    }

    /** 
     * Gets if the current level is the last level.
     * 
     * @return 
     */
    public boolean isLastLevel() {
        return this.levels.indexOf(this.level) >= this.levels.size() - 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void start() {
        this.setupLevel();
        File musicFile = 
        new File("src/main/resources/com/group22/music/game-music-7408.mp3");
        switch (this.getGameState()) {
            case PLAYING:
                MusicManager.setTrack(musicFile);
                MusicManager.playOnRepeat();
                startPlaying();
                break;
            case REPLAYING:
                MusicManager.setTrack(musicFile);
                MusicManager.playOnRepeat();
                startReplaying();
                break;
            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
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

        if (this.time <= 0) {
            this.time = 0;
            this.setGameState(GameState.GAME_OVER);
        }

        this.getGamePane().getPlaying().setGameTime(this.time);
    }

    /**
     * Sets up the level to be played from start.
     */
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

        this.setViewSize(width, height);

        try {
            if (savedState == null) {
                this.addEntities(this.level.createEntities());
            } else {
                this.addEntities(this.savedState.getEntities());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts game given Playing game state
     */
    private void startPlaying() {
        this.getGamePane().getLevelComplete()
            .setIsLastLevel(this.isLastLevel());

        if (this.savedState != null) {
            this.setScore(savedState.getScore());
            this.time = savedState.getTime();
        }

        this.replay = new Replay(this.level.getTitle(), getProfile().getName());
    }

    /**
     * Starts game given Replaying game state
     */
    private void startReplaying() {
        Player player = this.level.getPlayerFromEntities(getEntities());

        ReplayPlayer replayPlayer =  
            new ReplayPlayer(player.getX(), player.getY(),  replay.getFrames());

        this.addEntity(replayPlayer);
        this.removeEntity(player);
    }

    private void onInitialized() {
        this.setUpProfiles();
    }

    /**
     * It loads all the levels from the levelLoader
     * and adds them to the levelSelector
     */
    private void setUpLeveles() {
        File musicFile = 
new File("src/main/resources/com/group22/music/sinnesloschen-beam-117362.mp3");
        MusicManager.setTrack(musicFile);
        MusicManager.playOnRepeat();
        this.levels = LevelManager.getInstance().getAllLevels();
        this.getGamePane().getLevelSelector().addLevels(this.levels);
    }

    /**
     * Called to setup the profile selector data and initialize the 
     * game profile data.
     */
    private void setUpProfiles() {
        File musicFile = 
new File("src/main/resources/com/group22/music/sinnesloschen-beam-117362.mp3");
        MusicManager.setTrack(musicFile);
        MusicManager.playOnRepeat();
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
     * Sets the current profile value.
     * 
     * @param profile the currently selected profile.
     */
    private void setProfile(Profile profile) {
        this.profile = profile;
        this.getGamePane().getLevelSelector().clearLevels();
        this.getGamePane().getLevelSelector().setProfile(profile);
        this.getGamePane().getStartMenu().setUsername(profile.getName());
        this.setUpLeveles();
    }
}

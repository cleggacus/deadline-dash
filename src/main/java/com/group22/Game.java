package com.group22;

import java.time.LocalDateTime;
import java.util.List;

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

    private static Game instance;

    /**
     * Creates Game.
     * Set to private so multiple instances cannot be created.
     */
    private Game() {
        super();
    }

    /** 
     * This method is used so the public game data can be accessed in other parts of the applicaiton. 
     * The instance of Game is created on its first call and is returned in the method.
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



    public Entity getPlayer() {
        return this.player;
    }

    /**
     * Checks if theres a color at (x1, y1) thats in (x2, y2).
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public boolean colorMatch(int x1, int y1, int x2, int y2) {
        return this.tiles[x1][y1].colorMatch(this.tiles[x2][y2]);
    }

    public void addTime(double seconds) {
        this.time += seconds;
    }

    public int getScore() {
        return score;
    }

    public double getTime() {
        return time;
    }

    public Entity getDoor() {
        for(Entity entity : this.entities){
            if(entity instanceof Door){
                return entity;
            }
        }
        return null;
    }

    public void incrementScore(int amount) {
        this.setScore(this.score + amount);
    }

    public void setScore(int score) {
        this.score = score;
        this.getGamePane().getPlaying().setGameScore(score);
    }

    @Override
    protected void start() {
        this.setScore(0);

        Level currentLevel = this.levels.get(this.currentLevelIndex);
        this.level = currentLevel;

        int width = currentLevel.getWidth();
        int height = currentLevel.getHeight();

        this.getGamePane().getPlaying().setGameLevel(currentLevel.getTitle());

        this.tiles = currentLevel.getTiles();
        this.time = currentLevel.getTimeToComplete();

        this.setViewSize(width, height);

        try {
            this.addEntities(currentLevel.createEntities());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setGameOver(){
        this.getGamePane().getGameOver().setStats(this.score, this.time);
        this.setGameState(GameState.GameOver);
    }

    /**
     * Overridden update method from {@code Engine}.
     */
    @Override
    protected void update() {
        updateTime();
        this.getGamePane().getPlaying().setGameScore(this.score);
        if(this.level.getPlayerFromEntities(this.getEntities()) == null){
            this.setGameOver();
        }
    }

    private void updateTime() {
        this.time -= this.getDelta();

        if(this.time <= 0) {
            this.time = 0;
            this.setGameState(GameState.GameOver);
        }

        this.getGamePane().getPlaying().setGameTime(this.time);
    }

    public void startFromLevel(int level){
        this.currentLevelIndex = level;
        this.setGameState(GameState.Playing);
    }

    private void onInitialized() {
        this.setUpProfiles();
    }

    private void setUpLeveles() {
        this.levelLoader = new LevelLoader();
        this.levelLoader.setUp();
        this.levels = Game.instance.levelLoader.getAllLevels();
        this.getGamePane().getLevelSelector().addLevels(this.levels);
    }

    private void setUpProfiles() {
        Profile checkProfiles = new Profile();
        List<Profile> profiles = checkProfiles.getAllProfiles();

        for(Profile profile : profiles) {
            this.getGamePane().getProfileSelector().addProfile(profile.getName());
        }

        this.getGamePane().getProfileSelector().setProfileAddedEvent(username -> {
            Profile profile = new Profile(username, LocalDateTime.now());

            if(!profile.exists()){
                profile.saveToFile();
            } else {
                profile.updateTimeActive();
            }
        });

        this.getGamePane().getProfileSelector().setOnProfileRemoved(profile -> {
            Profile deleteProfile = new Profile();
            deleteProfile.delete(profile);
        });

        this.getGamePane().getProfileSelector().setOnProfileSelectEvent(profile -> {
            this.getGamePane().getLevelSelector().clearLevels();
            this.getGamePane().getLevelSelector().setProfile(checkProfiles.getFromName(profile));
            this.getGamePane().getStartMenu().setUsername(profile);
            this.setUpLeveles();
        });
    }
}

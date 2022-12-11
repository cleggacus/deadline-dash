package com.group22.gui;

import com.group22.GameState;
import com.group22.gui.base.StatePane;

import javafx.scene.effect.GaussianBlur;

/**
 * The class {@code GamePane} extends state pane and uses GameState 
 * to determine which panes should be visible.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class GamePane extends StatePane<GameState> {
    /** GameOver pane, shown with GAME_OVER state. */
    private GameOver gameOver;
    /** LevelComplete pane, shown with LEVEL_COMPLETE state. */
    private LevelComplete levelComplete;
    /** ReplayOver pane, shown with REPLAY_OVER state. */
    private ReplayOver replayOver;
    /** Paused pane, shown with PAUSED state. */
    private Paused paused;
    /** Playing pane, shown with PLAYING, PAUSED and GAME_OVER states. */
    private Playing playing;
    /** ProfileSelector pane, shown with PROFILE_SELECTOR state. */
    private ProfileSelector profileSelector;
    /** LevelSelector pane, shown with LEVEL_SELECTOR state. */
    private LevelSelector levelSelector;
    /** StartMenu pane, shown with START_MENU state. */
    private StartMenu startMenu;
    /** ReplaysBrowser pane, shown with REPLAYS_BROWSER state. */
    private ReplaysBrowser replaysBrowser;
    /** SavesBrowser pane, shown with SAVES_BROWSER state. */
    private SavesBrowser savesBrowser;
    /** Credits pane, shown with CREDITS state. */
    private Credits credits;

    /**
     * Creates a GamePane.
     */
    public GamePane() {
        // Set initial state
        super(GameState.PROFILE_SELECTOR);

        this.setStyling();

        // Create panes
        this.profileSelector = new ProfileSelector();
        this.startMenu = new StartMenu();
        this.levelSelector = new LevelSelector(this);
        this.replaysBrowser = new ReplaysBrowser();
        this.savesBrowser = new SavesBrowser();
        this.replayOver = new ReplayOver();
        this.playing = new Playing();
        this.paused = new Paused();
        this.gameOver = new GameOver();
        this.levelComplete = new LevelComplete();
        this.credits = new Credits();

        // Add panes with there visible states
        this.addPane(this.profileSelector, GameState.PROFILE_SELECTOR);
        this.addPane(this.startMenu, GameState.START);
        this.addPane(this.replaysBrowser, GameState.REPLAYS_BROWSER);
        this.addPane(this.replayOver, GameState.REPLAY_OVER);
        this.addPane(this.levelSelector, GameState.LEVEL_SELECTOR);
        this.addPane(this.savesBrowser, GameState.SAVES_BROWSER);
        this.addPane(this.playing, 
            GameState.PLAYING, GameState.PAUSED, GameState.GAME_OVER);
        this.addPane(this.gameOver, GameState.GAME_OVER);
        this.addPane(this.levelComplete, GameState.LEVEL_COMPLETE);
        this.addPane(this.paused, GameState.PAUSED);
        this.addPane(this.credits, GameState.CREDITS);
    }

    /**
     * Used to set the GameState of the pane.
     * 
     * @param state The GameState which is set in GamePane.
     */
    @Override
    public void setState(GameState state) {
        super.setState(state);

        if (state != GameState.PLAYING) {
            GaussianBlur blur = new GaussianBlur(20);
            this.playing.setEffect(blur);
        } else {
            this.playing.setEffect(null);
        }
    }

    /**
     * Gets the {@link #gameOver} pane.
     * 
     * @return Instance of GameOver pane.
     */
    public GameOver getGameOver() {
        return gameOver;
    }

    /**
     * Gets the {@link #levelComplete} pane.
     * 
     * @return Instance of LevelComplete pane.
     */
    public LevelComplete getLevelComplete() {
        return levelComplete;
    }

    /**
     * Gets the {@link #playing} pane.
     * 
     * @return Instance of Playing pane.
     */
    public Playing getPlaying() {
        return playing;
    }

    /**
     * Gets the {@link #playing} pane.
     * 
     * @return Instance of StartMenu pane.
     */
    public StartMenu getStartMenu() {
        return startMenu;
    }

    /**
     * Gets the {@link #savesBrowser} pane.
     * 
     * @return Instance of SavesBrowser pane.
     */
    public SavesBrowser getSavesBrowser() {
        return savesBrowser;
    }

    /**
     * Gets the {@link #profileSelector} pane.
     * 
     * @return Instance of ProfileSelector pane.
     */
    public ProfileSelector getProfileSelector() {
        return profileSelector;
    }

    /**
     * Gets the {@link #levelSelector} pane.
     * 
     * @return Instance of LevelSelector pane.
     */
    public LevelSelector getLevelSelector() {
        return levelSelector;
    }

    /**
     * Gets the {@link #replaysBrowser} pane.
     * 
     * @return Instance of ReplaysBrowser pane.
     */
    public ReplaysBrowser getReplaysBrowser() {
        return replaysBrowser;
    }

    /**
     * Gets the {@link #replayOver} pane.
     * 
     * @return Instance of ReplayOver pane.
     */
    public ReplayOver getReplayOver(){
        return replayOver;
    }

    /**
     * Update method called in the game loop and required for animations.
     */
    public void update() {
        this.levelSelector.update();
    }

    /**
     * Loads and sets the GamePane styling.
     */
    private void setStyling() {
        this.getStylesheets().add(
            getClass().getResource("gamepane.css").toString());

        this.getStyleClass().add("game-pane");
    }
}

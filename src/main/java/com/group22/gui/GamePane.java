package com.group22.gui;

import com.group22.GameState;
import com.group22.gui.base.StatePane;

import javafx.scene.effect.GaussianBlur;

public class GamePane extends StatePane<GameState> {
    // Panes
    private GameOver gameOver;
    private LevelComplete levelComplete;
    private ReplayOver replayOver;
    private Paused paused;
    private Playing playing;
    private ProfileSelector profileSelector;
    private LevelSelector levelSelector;
    private StartMenu startMenu;
    private ReplaysBrowser replaysBrowser;
    private Credits credits;

    public GamePane() {
        // Set initial state
        super(GameState.ProfileSelector);

        this.setStyling();

        // Create panes
        this.profileSelector = new ProfileSelector(this);
        this.startMenu = new StartMenu(this);
        this.levelSelector = new LevelSelector(this);
        this.replaysBrowser = new ReplaysBrowser(this);
        this.replayOver = new ReplayOver(this);
        this.playing = new Playing(this);
        this.paused = new Paused(this);
        this.gameOver = new GameOver(this);
        this.levelComplete = new LevelComplete(this);
        this.credits = new Credits();

        // Add panes with there visible states
        this.addPane(this.profileSelector, GameState.ProfileSelector);
        this.addPane(this.startMenu, GameState.Start);
        this.addPane(this.replaysBrowser, GameState.ReplaysBrowser);
        this.addPane(this.replayOver, GameState.ReplayOver);
        this.addPane(this.levelSelector, GameState.LevelSelector);
        this.addPane(this.playing, GameState.Playing, GameState.Paused, GameState.GameOver);
        this.addPane(this.gameOver, GameState.GameOver);
        this.addPane(this.levelComplete, GameState.LevelComplete);
        this.addPane(this.paused, GameState.Paused);
        this.addPane(this.credits, GameState.CREDITS);
    }

    @Override
    public void setState(GameState state) {
        super.setState(state);

        if(state != GameState.Playing) {
            GaussianBlur blur = new GaussianBlur(20);
            this.playing.setEffect(blur);
        } else {
            this.playing.setEffect(null);
        }
    }

    public GameOver getGameOver() {
        return gameOver;
    }

    public LevelComplete getFinish() {
        return levelComplete;
    }

    public Playing getPlaying() {
        return playing;
    }

    public StartMenu getStartMenu() {
        return startMenu;
    }

    public ProfileSelector getProfileSelector() {
        return profileSelector;
    }

    public LevelSelector getLevelSelector() {
        return levelSelector;
    }

    public ReplaysBrowser getReplaysBrowser() {
        return replaysBrowser;
    }

    public ReplayOver getReplayOver(){
        return replayOver;
    }

    public void update() {
        this.levelSelector.update();
    }

    private void setStyling() {
        this.getStylesheets().add(
            getClass().getResource("gamepane.css").toString());

        this.getStyleClass().add("game-pane");
    }
}

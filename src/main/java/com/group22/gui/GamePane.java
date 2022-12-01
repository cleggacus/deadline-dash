package com.group22.gui;

import com.group22.GameState;
import com.group22.gui.base.StatePane;

public class GamePane extends StatePane<GameState> {
    // Panes
    private GameOver gameOver;
    private Paused paused;
    private Playing playing;
    private ProfileSelector profileSelector;
    private LevelSelector levelSelector;
    private StartMenu startMenu;

    public GamePane() {
        // Set initial state
        super(GameState.ProfileSelector);

        this.setStyling();

        // Create panes
        this.profileSelector = new ProfileSelector(this);
        this.startMenu = new StartMenu(this);
        this.levelSelector = new LevelSelector(this);
        this.playing = new Playing(this);
        this.paused = new Paused(this);
        this.gameOver = new GameOver(this);

        // Add panes with there visible states
        this.addPane(this.profileSelector, GameState.ProfileSelector);
        this.addPane(this.startMenu, GameState.Start);
        this.addPane(this.levelSelector, GameState.LevelSelector);
        this.addPane(this.playing, GameState.Playing, GameState.Paused, GameState.GameOver);
        this.addPane(this.gameOver, GameState.GameOver);
        this.addPane(this.paused, GameState.Paused);
    }

    public Playing getPlaying() {
        return playing;
    }

    public ProfileSelector getProfileSelector() {
        return profileSelector;
    }

    public LevelSelector getLevelSelector() {
        return levelSelector;
    }

    private void setStyling() {
        this.getStylesheets().add(
            getClass().getResource("gamepane.css").toString());

        this.getStyleClass().add("game-pane");
    }
}

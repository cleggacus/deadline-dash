package com.group22.game.gui;

import com.group22.base.gui.StatePane;
import com.group22.game.GameState;

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
        this.addPane(this.startMenu, GameState.StartMenu);
        this.addPane(this.levelSelector, GameState.LevelSelector);
        this.addPane(this.playing, GameState.Playing, GameState.Paused, GameState.GameOver);
        this.addPane(this.gameOver, GameState.GameOver);
        this.addPane(this.paused, GameState.Paused);
    }

    private void setStyling() {
        this.getStylesheets().add(
            getClass().getResource("gamepane.css").toString());

        this.getStyleClass().add("game-pane");
    }
}

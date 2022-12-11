package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.gui.base.MenuPane;

import javafx.stage.Stage;

/**
 * The {@code Paused} class extends MenuPane is a pause menu.
 */
public class Paused extends MenuPane {

    /**
     * Creates the Paused menu.
     */
    public Paused() {
        this.addH1("PAUSED");
        this.addButton("RESUME", 
            () -> Game.getInstance().setGameState(GameState.Playing));
        this.addButton("RESTART", () -> {
            Game.getInstance().setGameState(GameState.GameOver);
            Game.getInstance().setGameState(GameState.Playing);
        });
        this.addButton("SAVE", () -> {
            Game.getInstance().saveState();
        });
        this.addButton("TOGGLE FULLSCREEN", () -> {
            Stage stage = (Stage)this.getScene().getWindow();
            stage.setFullScreen(!stage.isFullScreen());
        });

        this.addButton("EXIT", 
            () -> Game.getInstance().setGameState(GameState.Start));

        this.getStyleClass().add("pause-menu");
    }
}

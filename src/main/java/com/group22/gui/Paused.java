package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.gui.base.MenuPane;

import javafx.stage.Stage;

/**
 * The {@code Paused} class extends MenuPane is a pause menu.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class Paused extends MenuPane {

    /**
     * Creates the Paused menu.
     */
    public Paused() {
        this.addH1("PAUSED");
        this.addButton("RESUME", 
            () -> Game.getInstance().setGameState(GameState.PLAYING));
        this.addButton("RESTART", () -> {
            Game.getInstance().setGameState(GameState.GAME_OVER);
            Game.getInstance().setGameState(GameState.PLAYING);
        });
        this.addButton("SAVE", () -> {
            Game.getInstance().saveState();
        });
        this.addButton("TOGGLE FULLSCREEN", () -> {
            Stage stage = (Stage)this.getScene().getWindow();
            stage.setFullScreen(!stage.isFullScreen());
        });

        this.addButton("EXIT", 
            () -> Game.getInstance().setGameState(GameState.START));

        this.getStyleClass().add("pause-menu");
    }
}

package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.gui.base.MenuPane;

import javafx.stage.Stage;

public class Paused extends MenuPane {
    public Paused(GamePane gamePane) {
        this.addH1("PAUSED");
        this.addButton("RESUME", () -> gamePane.setState(GameState.Playing));
        this.addButton("RESTART", () -> {
            Game.getInstance().setGameState(GameState.GameOver);
            Game.getInstance().setGameState(GameState.Playing);
        });
        this.addButton("TOGGLE FULLSCREEN", () -> {
            Stage stage = (Stage)this.getScene().getWindow();
            stage.setFullScreen(!stage.isFullScreen());
        });

        this.addButton("EXIT", () -> gamePane.setState(GameState.Start));

        this.getStyleClass().add("pause-menu");
    }
}

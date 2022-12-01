package com.group22.game.gui;

import com.group22.GameState;
import com.group22.base.gui.MenuPane;

public class Paused extends MenuPane {
    public Paused(GamePane gamePane) {
        this.addH1("PAUSED");
        this.addButton("RESUME", () -> gamePane.setState(GameState.Playing));
        this.addButton("GAMEOVER", () -> gamePane.setState(GameState.GameOver));
        this.addButton("EXIT", () -> gamePane.setState(GameState.Start));

        this.getStyleClass().add("pause-menu");
    }
}

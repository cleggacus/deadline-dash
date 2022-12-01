package com.group22.game.gui;

import com.group22.GameState;
import com.group22.base.gui.MenuPane;

public class GameOver extends MenuPane {
    public GameOver(GamePane gamePane) {
        this.addH1("GAMEOVER");
        this.addButton("RESTART", () -> gamePane.setState(GameState.Playing));
        this.addButton("EXIT", () -> gamePane.setState(GameState.Start));

        this.getStyleClass().add("gameover-menu");
    }
}

package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.gui.base.MenuPane;

public class GameOver extends MenuPane {
    public GameOver(GamePane gamePane) {
        this.addH1("GAMEOVER");
        this.addButton("RESTART", () -> Game.getInstance().setGameState(GameState.Playing));
        this.addButton("EXIT", () -> gamePane.setState(GameState.Start));

        this.getStyleClass().add("gameover-menu");
    }
}

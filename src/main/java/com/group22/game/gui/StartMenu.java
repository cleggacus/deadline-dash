package com.group22.game.gui;

import com.group22.GameState;
import com.group22.base.gui.MenuPane;

public class StartMenu extends MenuPane {
    public StartMenu(GamePane gamePane) {
        this.addH2("HEY, USERNAME");
        this.addH1("DEADLINE DASH");
        this.addButton("LEVELS", () -> gamePane.setState(GameState.LevelSelector));
        this.addButton("CHANGE USER", () -> gamePane.setState(GameState.ProfileSelector));
    }
}

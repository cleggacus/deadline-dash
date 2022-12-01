package com.group22.game.gui;

import com.group22.base.gui.MenuPane;
import com.group22.game.GameState;

public class LevelSelector extends MenuPane {
    private GamePane gamePane;
    private MenuPane levelMenu;

    public LevelSelector(GamePane gamePane) {
        this.gamePane = gamePane;
        this.levelMenu = new MenuPane();

        this.addH1("LEVELS");
        this.add(this.levelMenu.getAsScrollPane());
        this.addButton("GO BACK", () -> gamePane.setState(GameState.StartMenu));

        this.addLevel("LEVEL 1-1");
        this.addLevel("LEVEL 1-2");
        this.addLevel("LEVEL 1-3");
        this.addLevel("LEVEL 1-4");
        this.addLevel("LEVEL 2-1");
        this.addLevel("LEVEL 2-2");
        this.addLevel("LEVEL 2-3");
        this.addLevel("LEVEL 2-4");
    }

    public void addLevel(String level) {
        this.levelMenu.addButton(level, () -> {
            this.gamePane.setState(GameState.Playing);
        });
    }
}

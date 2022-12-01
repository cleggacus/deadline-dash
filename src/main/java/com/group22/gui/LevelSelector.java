package com.group22.gui;

import java.util.List;

import com.group22.Game;
import com.group22.GameState;
import com.group22.gui.base.MenuPane;

public class LevelSelector extends MenuPane {
    private MenuPane levelMenu;

    public LevelSelector(GamePane gamePane) {
        this.levelMenu = new MenuPane();

        this.addH1("LEVELS");
        this.add(this.levelMenu.getAsScrollPane());
        this.addButton("GO BACK", () -> gamePane.setState(GameState.Start));
    }

    public void addLevels(List<String> levels) {
        for(String level : levels)  {
            this.addLevel(level);
        }
    }

    public void addLevel(String level) {
        int i = this.levelMenu.getRowCount();

        this.levelMenu.addButton(level, () -> {
            Game.getInstance().startFromLevel(i);
        });
    }
}

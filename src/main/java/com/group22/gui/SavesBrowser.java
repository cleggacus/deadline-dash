package com.group22.gui;

import java.util.ArrayList;

import com.group22.Game;
import com.group22.GameState;
import com.group22.Level;
import com.group22.Replay;
import com.group22.ReplayManager;
import com.group22.SavedState;
import com.group22.SavedStateManager;
import com.group22.TimeUtil;
import com.group22.gui.base.MenuPane;

public class SavesBrowser extends MenuPane {

    private String username;
    private GamePane gamePane;
    private MenuPane savesMenu;
    private TimeUtil relativeTime;
    private SavedStateManager savedStateManager;

    public SavesBrowser(GamePane gamePane) {
        this.gamePane = gamePane;
        this.relativeTime = new TimeUtil();
        this.savesMenu = new MenuPane();
        this.savedStateManager = new SavedStateManager();
        this.addH1("SAVES");
        this.add(this.savesMenu.getAsScrollPane());
        this.addButton("BACK", () -> this.gamePane.setState(
            GameState.LevelSelector));
    }

    public void setSavedStates(Level level, int levelIndex) {
        this.savesMenu.getChildren().clear();
        ArrayList<SavedState> savedStates = this.savedStateManager.getStates(level, Game.getInstance().getUsername());
        if (savedStates.size() == 0) {
            this.savesMenu.addH2("No saves yet!");
            this.savesMenu.addParagraph("Save in the pause menu and load it here later.");
        }
        for (SavedState savedState : savedStates) {
            this.savesMenu.addButton(this.relativeTime.getTimeAgo(savedState.getTimeOfSave()),
            () -> Game.getInstance().setSavedState(savedState));
            this.savesMenu.addSmallPrint("Score: " + savedState.getScore());
            this.savesMenu.addSmallPrint("Time left: " + savedState.getTime());
        }
    }

}

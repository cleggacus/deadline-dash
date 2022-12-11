package com.group22.gui;

import java.util.ArrayList;

import com.group22.Game;
import com.group22.GameState;
import com.group22.Level;
import com.group22.SavedState;
import com.group22.SavedStateManager;
import com.group22.TimeUtil;
import com.group22.gui.base.MenuPane;

/**
 * The class {@code SavesBrowser} extends MenuPane and show a list of
 * save states in the level
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class SavesBrowser extends MenuPane {
    private MenuPane savesMenu;
    private SavedStateManager savedStateManager;

    /**
     * Creates the SavesBrowser pane.
     */
    public SavesBrowser() {
        this.savesMenu = new MenuPane();
        this.savedStateManager = new SavedStateManager();
        this.addH1("SAVES");
        this.add(this.savesMenu.getAsScrollPane());
        this.addButton("BACK", 
            () -> Game.getInstance().setGameState(GameState.LEVEL_SELECTOR));
    }

    /**
     * Sets the menu to have stave states in given level.
     * 
     * @param level name of the level to be loaded.
     * @param levelIndex number of the level to be loaded.
     */
    public void setSavedStates(Level level, int levelIndex) {
        this.savesMenu.getChildren().clear();

        ArrayList<SavedState> savedStates = this.savedStateManager
            .getStates(level, Game.getInstance().getProfile().getName());

        if (savedStates.size() == 0) {
            this.savesMenu.addH2("No saves yet!");
            this.savesMenu.addParagraph(
                "Save in the pause menu and load it here later.");
        }
        for (SavedState savedState : savedStates) {
            this.savesMenu.addButton(
                TimeUtil.getTimeAgo(savedState.getTimeOfSave()),
                () -> Game.getInstance().startSavedState(savedState));

            this.savesMenu.addSmallPrint("Score: " + savedState.getScore());
            String time = String.format("Time: %f", savedState.getTime());
            this.savesMenu.addSmallPrint(time);
        }
    }

}

package com.group22.gui;

import java.util.ArrayList;

import com.group22.Game;
import com.group22.GameState;
import com.group22.Replay;
import com.group22.ReplayManager;
import com.group22.TimeUtil;
import com.group22.gui.base.MenuPane;

/**
 * The class {@code ReplayBrowser} extends MenuPane and show a list of
 * replays which can be viewed.
 * 
 * @author Liam Clegg
 * @version 1.1
 */
public class ReplaysBrowser extends MenuPane {
    /** The menu which contains the list of replays. */
    private MenuPane replaysMenu;
    /** Instance of ReplayManager used to access the replays. */
    private ReplayManager replayManager;

    /**
     * Creates ReplayBrowser pane.
     */
    public ReplaysBrowser() {
        this.replaysMenu = new MenuPane();
        this.replayManager = new ReplayManager();
        this.addH1("REPLAYS");
        this.add(this.replaysMenu.getAsScrollPane());
        this.addButton("BACK", () -> 
            Game.getInstance().setGameState(GameState.LEVEL_SELECTOR));
    }

    /**
     * Sets the level which will display the replays.
     * 
     * @param level the name of the level which is loaded.
     * @param levelIndex the level number which is loaded.
     */
    public void setReplays(String level, int levelIndex) {
        this.replaysMenu.getChildren().clear();

        ArrayList<Replay> replays = 
            this.replayManager.getReplaysFromLevelTitle(level);

        if (replays.size() == 0) {
            this.replaysMenu.addH2("No plays yet!");
            this.replaysMenu.addParagraph(
                "Complete the level to have your score and replay saved.");
        }
        
        for (Replay replay : replays) {
            this.replaysMenu.addButton(
                replay.getUsername() + ": " + replay.getScore(),

            () -> Game.getInstance().replayFromLevel(levelIndex, replay));

            this.replaysMenu.addSmallPrint(
                TimeUtil.getTimeAgo(replay.getTimeOfSave()));
        }
    }

}

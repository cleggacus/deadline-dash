package com.group22.gui;

import java.util.ArrayList;

import com.group22.Game;
import com.group22.GameState;
import com.group22.Replay;
import com.group22.ReplayManager;
import com.group22.TimeUtil;
import com.group22.gui.base.MenuPane;

public class ReplaysBrowser extends MenuPane {

    private String username;
    private GamePane gamePane;
    private MenuPane replaysMenu;
    private ReplayManager replayManager;

    public ReplaysBrowser(GamePane gamePane) {
        this.gamePane = gamePane;
        this.replaysMenu = new MenuPane();
        this.replayManager = new ReplayManager();
        this.addH1("REPLAYS");
        this.add(this.replaysMenu.getAsScrollPane());
        this.addButton("BACK", () -> this.gamePane.setState(GameState.LevelSelector));
    }

    public String getUsername() {
        return this.username;
    }

    public void setReplays(String level, int levelIndex) {
        this.replaysMenu.getChildren().clear();

        ArrayList<Replay> replays = this.replayManager.getReplaysFromLevelTitle(level);
        if (replays.size() == 0){
            this.replaysMenu.addH2("No plays yet!");
            this.replaysMenu.addParagraph("Complete the level to have your score and replay saved.");
        }
        for(Replay replay : replays){
            this.replaysMenu.addButton(replay.getUsername() + ": " + replay.getScore(),
            () -> Game.getInstance().setReplay(GameState.Playing, replay, levelIndex));
            this.replaysMenu.addSmallPrint(TimeUtil.getTimeAgo(replay.getTimeOfSave()));
        }
    }

}

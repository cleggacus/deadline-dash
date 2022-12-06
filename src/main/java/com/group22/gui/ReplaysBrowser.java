package com.group22.gui;

import java.time.LocalDateTime;
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
    private TimeUtil relativeTime;
    private ReplayManager replayManager;

    public ReplaysBrowser(GamePane gamePane) {
        this.gamePane = gamePane;
        this.replayManager = new ReplayManager();
        this.relativeTime = new TimeUtil();
        this.replaysMenu = new MenuPane();
        this.addH1("REPLAYS");
        this.add(this.replaysMenu.getAsScrollPane());
        this.addButton("BACK", () -> this.gamePane.setState(GameState.LevelSelector));
    }

    public String getUsername() {
        return this.username;
    }

    public void setReplays(ArrayList<Replay> replays, String level, int levelIndex) {
        this.replaysMenu.getChildren().clear();
        for(Replay replay : replays){
            this.replaysMenu.addButton(replay.getUsername(),
            () -> Game.getInstance().setReplay(GameState.Playing, replay, levelIndex));
            this.replaysMenu.addSmallPrint(this.relativeTime.getTimeAgo(replay.getTimeOfSave()));
        }
    }

}

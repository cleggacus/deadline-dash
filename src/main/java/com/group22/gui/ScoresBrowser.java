package com.group22.gui;

import java.time.LocalDateTime;

import com.group22.GameState;
import com.group22.TimeUtil;
import com.group22.gui.base.MenuPane;

public class ScoresBrowser extends MenuPane {

    private String username;
    private GamePane gamePane;
    private MenuPane scoresMenu;
    private TimeUtil relativeTime;

    public ScoresBrowser(GamePane gamePane) {
        this.gamePane = gamePane;
        this.relativeTime = new TimeUtil();
        this.scoresMenu = new MenuPane();
        this.addH1("TOP STUDENTS");
        this.add(this.scoresMenu.getAsScrollPane());
        this.addButton("BACK", () -> this.gamePane.setState(
            GameState.LevelSelector));
    }

    public String getUsername() {
        return this.username;
    }

    public void setLevel(String level, String[][] scores) {
        this.scoresMenu.getChildren().clear();

        for(int i = 0; i < scores.length; i++){
            this.scoresMenu.addParagraph(scores[i][0].toUpperCase() +
            ": " + scores[i][1]);
            this.scoresMenu.addSmallPrint(this.relativeTime.getTimeAgo(
                LocalDateTime.parse(scores[i][2])));
        }
    }

}

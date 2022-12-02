package com.group22.gui;

import java.time.LocalDateTime;

import com.group22.GameState;
import com.group22.RelativeTime;
import com.group22.gui.base.MenuPane;

public class ScoresBrowser extends MenuPane {

    private String username;
    private GamePane gamePane;
    private MenuPane scoresMenu;
    private RelativeTime relativeTime;

    public ScoresBrowser(GamePane gamePane) {
        this.gamePane = gamePane;
        this.relativeTime = new RelativeTime();
        this.scoresMenu = new MenuPane();
        this.addH1("TOP STUDENTS");
        this.add(this.scoresMenu.getAsScrollPane());

    }

    public String getUsername() {
        return this.username;
    }

    public void setLevel(String level, String[][] scores) {
        this.scoresMenu.getChildren().clear();

        for(int i = 0; i < scores.length; i++){
            this.scoresMenu.addParagraph(scores[i][0].toUpperCase() + ": " + scores[i][1].toUpperCase() + " seconds left");
            this.scoresMenu.addSmallPrint(this.relativeTime.getTimeAgo(LocalDateTime.parse(scores[i][2])));
        }
        this.scoresMenu.addButton("BACK", () -> this.gamePane.setState(GameState.LevelSelector));
    }

}

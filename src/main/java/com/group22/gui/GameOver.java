package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.gui.base.MenuPane;

public class GameOver extends MenuPane {
    private GamePane gamePane;
    public GameOver(GamePane gamePane) {
        this.gamePane = gamePane;

        setUpGameLost();

        this.getStyleClass().add("gameover-menu");
    }
    private void setUpGameLost(){
        this.getChildren().clear();
        this.addH1("GAMEOVER");
        this.addButton("RESTART", () -> Game.getInstance().setGameState(GameState.Playing));
        this.addButton("EXIT", () -> gamePane.setState(GameState.Start));
    }

    public void setStats(int score, double time) {
        setUpGameLost();
        this.addParagraph("Score: " + String.valueOf(score));
    }
}

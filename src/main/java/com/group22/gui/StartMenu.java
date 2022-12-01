package com.group22.gui;

import com.group22.GameState;
import com.group22.gui.base.MenuPane;

import javafx.scene.control.Label;

public class StartMenu extends MenuPane {
    private Label welcomeLabel;

    public StartMenu(GamePane gamePane) {
        this.welcomeLabel = this.addH2("");
        this.addH1("DEADLINE DASH");
        this.addButton("LEVELS", () -> gamePane.setState(GameState.LevelSelector));
        this.addButton("CHANGE USER", () -> gamePane.setState(GameState.ProfileSelector));
    }

    public void setUsername(String username) {
        this.welcomeLabel.setText("HEY, " + username);
    }
}

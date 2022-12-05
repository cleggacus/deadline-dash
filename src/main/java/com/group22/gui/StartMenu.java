package com.group22.gui;

import java.util.List;

import com.group22.GameState;
import com.group22.MOTD;
import com.group22.gui.base.MenuPane;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StartMenu extends BorderPane {
    private Label welcomeLabel;

    public StartMenu(GamePane gamePane) {
        MenuPane topMenuPane = new MenuPane();
        MenuPane centerMenu = new MenuPane();
        MenuPane bottomMenu = new MenuPane();

        this.welcomeLabel = topMenuPane.addH2("");
        centerMenu.addH1("DEADLINE DASH");
        centerMenu.addButton("LEVELS", () -> gamePane.setState(GameState.LevelSelector));
        centerMenu.addButton("CHANGE USER", () -> gamePane.setState(GameState.ProfileSelector));

        centerMenu.addButton("TOGGLE FULLSCREEN", () -> {
            Stage stage = (Stage)this.getScene().getWindow();
            stage.setFullScreen(!stage.isFullScreen());
        });

        bottomMenu.addParagraph(MOTD.getMOTD().toUpperCase());

        this.setTop(topMenuPane);
        this.setCenter(centerMenu);
        this.setBottom(bottomMenu);
    }

    public void setUsername(String username) {
        this.welcomeLabel.setText("HEY, " + username.toUpperCase());
    }
}

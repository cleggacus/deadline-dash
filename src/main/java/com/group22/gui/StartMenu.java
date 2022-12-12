package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.MOTD;
import com.group22.gui.base.MenuPane;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The class {@code StartMenu} extends BorderPane and contains a start menu).
 * This shows the welcome text and message of the day.
 * 
 * @author Liam Clegg
 * @version 1.1
 */
public class StartMenu extends BorderPane {
    /** Label which contains the welcome text. */
    private Label welcomeLabel;

    /**
     * Creates a StartMenu pane.
     */
    public StartMenu() {
        MenuPane topMenuPane = new MenuPane();
        MenuPane centerMenu = new MenuPane();
        MenuPane bottomMenu = new MenuPane();

        this.welcomeLabel = topMenuPane.addH2("");
        centerMenu.addH1("DEADLINE DASH");

        centerMenu.addButton("LEVELS", 
            () -> Game.getInstance().setGameState(GameState.LEVEL_SELECTOR));

        centerMenu.addButton("CHANGE USER", 
            () -> Game.getInstance().setGameState(GameState.PROFILE_SELECTOR));

        centerMenu.addButton("TOGGLE FULLSCREEN", () -> {
            Stage stage = (Stage)this.getScene().getWindow();
            stage.setFullScreen(!stage.isFullScreen());
        });

        bottomMenu.addParagraph(MOTD.getMOTD().toUpperCase());

        this.setTop(topMenuPane);
        this.setCenter(centerMenu);
        this.setBottom(bottomMenu);
    }

    /**
     * Sets the username in the {@link #welcomeLabel}.
     * 
     * @param username current username
     */
    public void setUsername(String username) {
        this.welcomeLabel.setText("HEY, " + username.toUpperCase());
    }
}

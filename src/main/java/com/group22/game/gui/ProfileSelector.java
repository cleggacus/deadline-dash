package com.group22.game.gui;

import com.group22.base.gui.MenuPane;
import com.group22.game.GameState;

public class ProfileSelector extends MenuPane {
    private GamePane gamePane;
    private MenuPane profileMenu;

    public ProfileSelector(GamePane gamePane) {
        this.gamePane = gamePane;
        this.profileMenu = new MenuPane();

        this.addH1("PROFILE");

        this.addInput("NEW PROFILE", v -> this.addProfile(v));

        this.add(this.profileMenu.getAsScrollPane());
    }

    public void addProfile(String profile) {
        this.profileMenu.addListButton(profile, () -> {
            this.gamePane.setState(GameState.StartMenu);
        }, node -> {
            this.profileMenu.remove(node);
        });
    }
}

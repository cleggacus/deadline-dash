package com.group22.gui;

import com.group22.GameState;
import com.group22.gui.base.MenuPane;

public class ProfileSelector extends MenuPane {
    private ProfileAddedEvent profileAddedEvent;
    private ProfileSelectEvent profileSelectEvent;
    private ProfileRemovedEvent profileRemovedEvent;

    private String username;
    private GamePane gamePane;
    private MenuPane profileMenu;

    public ProfileSelector(GamePane gamePane) {
        this.gamePane = gamePane;
        this.profileMenu = new MenuPane();

        this.addH1("PROFILE");

        this.addInput("NEW PROFILE", v -> this.addProfile(v));

        this.add(this.profileMenu.getAsScrollPane());
    }

    public String getUsername() {
        return this.username;
    }

    public void addProfile(String profile) {
        if(this.profileAddedEvent != null)
            this.profileAddedEvent.run(profile.toLowerCase());

        this.profileMenu.addListButton(profile.toUpperCase(), () -> {
            this.profileSelectEvent.run(profile.toLowerCase());
            this.username = profile;
            this.gamePane.setState(GameState.Start);
        }, node -> {
            this.profileMenu.remove(node);

            if(this.profileRemovedEvent != null)
                this.profileRemovedEvent.run(profile.toLowerCase());
        });
    }

    public void setOnProfileRemoved(ProfileRemovedEvent profileRemovedEvent) {
        this.profileRemovedEvent = profileRemovedEvent;
    }

    public void setProfileAddedEvent(ProfileAddedEvent profileAddedEvent) {
        this.profileAddedEvent = profileAddedEvent;
    }

    public void setOnProfileSelectEvent(ProfileSelectEvent profileSelectEvent) {
        this.profileSelectEvent = profileSelectEvent;
    }

    public interface ProfileAddedEvent {
        void run(String profile);
    }

    public interface ProfileRemovedEvent {
        void run(String profile);
    }

    public interface ProfileSelectEvent {
        void run(String profile);
    }
}

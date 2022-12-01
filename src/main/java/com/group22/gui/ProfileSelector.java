package com.group22.gui;

import com.group22.GameState;

public class ProfileSelector extends MenuPane {
    private ProfileAddedEvent profileAddedEvent;
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
        return username;
    }

    public void addProfile(String profile) {
        if(this.profileAddedEvent != null)
            this.profileAddedEvent.run(profile);

        this.profileMenu.addListButton(profile, () -> {
            this.username = profile;
            this.gamePane.setState(GameState.Start);
        }, node -> {
            this.profileMenu.remove(node);

            if(this.profileRemovedEvent != null)
                this.profileRemovedEvent.run(profile);
        });
    }

    public void setOnProfileRemoved(ProfileRemovedEvent profileRemovedEvent) {
        this.profileRemovedEvent = profileRemovedEvent;
    }

    public void setProfileAddedEvent(ProfileAddedEvent profileAddedEvent) {
        this.profileAddedEvent = profileAddedEvent;
    }

    public interface ProfileAddedEvent {
        void run(String profile);
    }

    public interface ProfileRemovedEvent {
        void run(String profile);
    }
}

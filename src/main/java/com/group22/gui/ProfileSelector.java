package com.group22.gui;

import com.group22.GameState;
import com.group22.gui.base.MenuPane;

/**
 * Class that adds profile to a gui element to allow
 * the user to select, create or delete one.
 */
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

    /**
     * This function returns the username of the user
     * 
     * @return The username of the user.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * It adds profile to the menu as a button.
     * Creates listeners for actions performed on this profile
     * 
     * @param profile The name of the profile to add.
     */
    public void addProfile(String profile) {
        String sanitized = profile.toLowerCase().replaceAll(" ", "_");

        if(this.profileAddedEvent != null)
            this.profileAddedEvent.run(sanitized);

        this.profileMenu.addListButton(sanitized.toUpperCase(), () -> {
            this.profileSelectEvent.run(sanitized);
            this.username = sanitized;
            this.gamePane.setState(GameState.Start);
        }, node -> {
            this.profileMenu.remove(node);

            if(this.profileRemovedEvent != null)
                this.profileRemovedEvent.run(sanitized);
        });
    }

    /**
     * This function is used to set the profileRemovedEvent variable to 
     * the profileRemovedEvent parameter
     * 
     * @param profileRemovedEvent This is the event that will be fired when
     * the profile is removed.
     */
    public void setOnProfileRemoved(ProfileRemovedEvent profileRemovedEvent) {
        this.profileRemovedEvent = profileRemovedEvent;
    }

    /**
     * This function is used to set the profileAddedEvent variable to 
     * the profileAddedEvent parameter
     * 
     * @param profileAddedEvent This is the event that will be fired when
     *  the profile is added.
     */
    public void setProfileAddedEvent(ProfileAddedEvent profileAddedEvent) {
        this.profileAddedEvent = profileAddedEvent;
    }

    /**
     * This function sets the profileSelectEvent variable to the
     * profileSelectEvent parameter.
     * 
     * @param profileSelectEvent This is the event that will be fired when
     *  the user selects a profile.
     */
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

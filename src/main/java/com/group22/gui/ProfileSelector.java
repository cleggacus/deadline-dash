package com.group22.gui;

import com.group22.Game;
import com.group22.GameState;
import com.group22.gui.base.MenuPane;

/**
 * The Class {@code ProfileSelector} adds profile to a gui element to allow
 * the user to select, create or delete one.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class ProfileSelector extends MenuPane {
    /** Event thats called when a profile is added to the GUI. */
    private ProfileAddedEvent profileAddedEvent;
    /** Event thats called when a profile is selected from the GUI. */
    private ProfileSelectEvent profileSelectEvent;
    /** Event thats called when a profile is removed from the GUI. */
    private ProfileRemovedEvent profileRemovedEvent;
    /** Username of the selected profile. */
    private String username;
    /** Menu which contains the profiles. */
    private MenuPane profileMenu;

    /**
     * Creates a ProfileSelector pane.
     */
    public ProfileSelector() {
        this.profileMenu = new MenuPane();

        this.addH1("PROFILE");

        this.addInput("NEW PROFILE", v -> this.addProfile(v));

        this.add(this.profileMenu.getAsScrollPane());
    }

    /**
     * This method returns the username of the user.
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

        if (this.profileAddedEvent != null) {
            this.profileAddedEvent.run(sanitized);
        }

        this.profileMenu.addListButton(sanitized.toUpperCase(), () -> {
            this.profileSelectEvent.run(sanitized);
            this.username = sanitized;
            Game.getInstance().setGameState(GameState.Start);
        }, node -> {
            this.profileMenu.remove(node);

            if (this.profileRemovedEvent != null) {
                this.profileRemovedEvent.run(sanitized);
            }
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

    /**
     * Event used for when profile is added and calls run when added.
     * Takes the profile which is added.
     */
    public interface ProfileAddedEvent {
        void run(String profile);
    }

    /**
     * Event used for when profile is removed and calls run when removed.
     * Takes the profile which is removed.
     */
    public interface ProfileRemovedEvent {
        void run(String profile);
    }


    /**
     * Event used for when profile is selected and calls run when selected.
     * Takes the profile which is selected.
     */
    public interface ProfileSelectEvent {
        void run(String profile);
    }
}

package com.group22;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * The {@code Profile} class represents an instance of a profile.
 * 
 * @author Sam Austin
 * @version 1.1
 */
public class Profile {
    private LocalDateTime dateLastActive;
    private String name;
    private static final String PROFILE_FILE = 
        "src/main/resources/com/group22/profiles.txt";
    private Integer maxUnlockedLevelIndex;
    private List<Profile> allProfiles;
    private FileManager fileManager = new FileManager();

    /**
     * Create an profile object
     * @param name  username of the user
     * @param dateLastActive    LocalDateTime of the last time the user
     * was active
     */
    public Profile(String name, LocalDateTime dateLastActive) {
        this.name = name;
        this.dateLastActive = dateLastActive;
        this.maxUnlockedLevelIndex = getMaxUnlockedLevelIndex();
    }

    public Profile() {
        this.loadAllProfiles();
    }


    /**
     * Saves profile data to the profiles.txt file
     */
    public void saveToFile() {
        ArrayList<String> toWrite = new ArrayList<String>();
        toWrite.add(this.getName() + " " +
                this.getTimeAgoLastActive() + " 1\n");
        fileManager.saveFile(toWrite, new File(PROFILE_FILE));
    }


    /**
     * Sets allProfiles to an ArrayList of profiles which
     * are read from getAllProfilesData and created as
     * new objects.
     */
    public void loadAllProfiles() {
        List<String> profileFileData = fileManager.getDataFromFile(
            new File(PROFILE_FILE));
        List<Profile> profiles = new ArrayList<Profile>();

        for (int i = 0; i < profileFileData.size(); i++) {
            String[] splitAtSpace = profileFileData.get(i).split(" ");
            // creates a new profile at each line read
            Profile profile = new Profile(
                splitAtSpace[0], LocalDateTime.parse(splitAtSpace[1]));
            profiles.add(profile);
        }

        this.allProfiles = profiles;
    }


    /**
     * This function returns a list of all the profiles in the database
     * 
     * @return A list of all profiles
     */
    public List<Profile> getAllProfiles() {
        return allProfiles;
    }


    /**
     * It loops through all the profiles in the list and returns
     * the first one that matches the name
     * 
     * @param name The name of the profile you want to get.
     * @return The {@link Profile} that matches the name.
     */
    public Profile getFromName(String name) {
        for (Profile p : this.allProfiles) {
            if (p.getName().equals(name)) {
                return p;
            }
        }

        return null;
    }


    /** 
     * Checks if the profile exists in the file
     * @return {@link Boolean}
     */
    public Boolean exists() {
        List<String> profileFileData = fileManager.getDataFromFile(
            new File(PROFILE_FILE));

        for (int i = 0; i < profileFileData.size(); i++) {
            String[] profile = profileFileData.get(i).split(" ");
            if (profile[0].equals(this.getName())) {
                return true;
            }
        }
        return false;
    }
    
    
    /** 
     * Delete a certain user from the file
     * @param username
     */
    public void delete(String username) {
        try{
            List<String> profileData = fileManager.getDataFromFile(
                new File(PROFILE_FILE));
            BufferedWriter wr = new BufferedWriter(
                new FileWriter(PROFILE_FILE, false));

            // find the specified line and remove it
            for (int i = 0; i < profileData.size(); i++) {
                if (profileData.get(i).split(" ")[0].equals(username)) {
                    profileData.remove(i);
                }

            }

            // write the new data
            for (int i = 0; i < profileData.size(); i++) {
                wr.write(profileData.get(i) + "\n");
            }
            wr.close();

            } catch(Exception e) {
                e.printStackTrace();
                
        }
    }

    /**
     * Updates the time last active
     */
    public void updateTimeActive() {
        this.dateLastActive = LocalDateTime.now();
    }
    
    
    /** 
     * Gets the maximum unlocked level for the profile
     * @return {@link Integer}
     */
    public Integer getMaxUnlockedLevelIndex(){
        List<String> allProfiles = fileManager.getDataFromFile(
            new File(PROFILE_FILE));
        // find the current profile in the file
        for (int i = 0; i < allProfiles.size(); i++) {
            String[] currentProfile = allProfiles.get(i).split(" ");
            if (currentProfile[0].equals(this.getName())) {
                // set the max unlocked level when found
                this.maxUnlockedLevelIndex = 
                    Integer.parseInt(currentProfile[2]);
            }
        }
        return this.maxUnlockedLevelIndex;
    }

    
    /** 
     * Getter for the username
     * @return {@link String}
     */
    public String getName() {
        return name;
    }

    
    /** 
     * Getter for time last active
     * @return {@link String}
     */
    public String getTimeAgoLastActive(){
        return dateLastActive.toString();
    }

    /**
     * Updates the max level unlocked for the profile in the profiles file
     * 
     * @param levelIndex the index of the level that the player has unlocked
     */
    public void setUnlockedLevelIndex(int levelIndex) {
        this.maxUnlockedLevelIndex = levelIndex;
        try{
            List<String> profileData = fileManager.getDataFromFile(
                new File(PROFILE_FILE));
            BufferedWriter wr = new BufferedWriter(
                new FileWriter(PROFILE_FILE, false));
            
            // loop through all profiles
            for (int i = 0; i < profileData.size(); i++) {
                if (profileData.get(i).split(" ")[0].equals(this.getName())) {
                    // when found, set the current line to the updated data
                    String profileString = this.getName() + " " + 
                        LocalDateTime.now().toString() + " " + 
                        String.valueOf(levelIndex);

                    profileData.set(i, profileString);
                }

            }

            // write the file with the updated data
            for (int i = 0; i < profileData.size(); i++) {
                wr.write(profileData.get(i) + "\n");
            }
            wr.close();

            } catch(Exception e){
                e.printStackTrace();

        }
    }
}

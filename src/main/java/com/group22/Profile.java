package com.group22;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Profile {
    private LocalDateTime dateLastActive;
    private String name;
    private static final String profileFile = 
    "src/main/resources/com/group22/profiles.txt";
    private Integer maxUnlockedLevelIndex;
    private List<Profile> allProfiles;

    public Profile(String name, LocalDateTime dateLastActive){
        this.name = name;
        this.dateLastActive = dateLastActive;
        this.maxUnlockedLevelIndex = getMaxUnlockedLevelIndex();
    }

    public Profile(){
        this.loadAllProfiles();
    }


    /**
     * Saves profile data to the profiles.txt file
     */
    public void saveToFile(){
        try {
            FileWriter myWriter = new FileWriter(profileFile, true);
            myWriter.append(this.getName() + " " +
                this.getTimeAgoLastActive() + " 1\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println(
                "An error occurred writing to the profiles.txt file");

            e.printStackTrace();
        }
    }

    /**
     * Sets allProfiles to an ArrayList of profiles which
     * are read from getAllProfilesData and created as
     * new objects.
     */
    public void loadAllProfiles(){
        List<String> profileFileData = getAllProfilesData();
        List<Profile> profiles = new ArrayList<Profile>();

        for(int i=0; i<profileFileData.size(); i++){
            String[] splitAtSpace = profileFileData.get(i).split(" ");
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
     * @return The profile object that matches the name.
     */
    public Profile getFromName(String name){
        for(Profile p : this.allProfiles){
            if(p.getName().equals(name)){
                return p;
            }
        }

        return null;
    }


    private List<String> getAllProfilesData(){
        List<String> dataArray = new ArrayList<String>();
        try {
            Scanner sc = new Scanner(new File(profileFile));

            while(sc.hasNext()){
                String line = sc.nextLine();
                dataArray.add(line);
            }

            sc.close();
        }
            catch(Exception e) {
            e.getStackTrace();
        }
        return dataArray;
    }

    public Boolean exists(){
        List<String> profileFileData = getAllProfilesData();

        for(int i=0; i<profileFileData.size(); i++){
            String[] profile = profileFileData.get(i).split(" ");
            if(profile[0].equals(this.getName())){
                return true;
            }
        }
        return false;

    }
    
    public void delete(String username){
        try{
            List<String> profileData = getAllProfilesData();
            BufferedWriter wr = new BufferedWriter(
                new FileWriter(profileFile, false));

            for(int i = 0; i < profileData.size(); i++){
                if(profileData.get(i).split(" ")[0].equals(username)){
                    profileData.remove(i);
                }

            }
            for(int i = 0; i<profileData.size(); i++){
                wr.write(profileData.get(i) + "\n");
            }
            wr.close();

            } catch(Exception e){

        }
    }

    public void updateTimeActive(){
        return;
    }
    
    public Integer getMaxUnlockedLevelIndex(){
        List<String> allProfiles = this.getAllProfilesData();
        for(int i = 0; i < allProfiles.size(); i++){
            String[] currentProfile = allProfiles.get(i).split(" ");
            if(currentProfile[0].equals(this.getName())){
                this.maxUnlockedLevelIndex = Integer.parseInt(
                    currentProfile[2]);
            }
        }
        return this.maxUnlockedLevelIndex;
    }

    public String getName(){
        return name;
    }

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
            List<String> profileData = getAllProfilesData();
            BufferedWriter wr = new BufferedWriter(
                new FileWriter(profileFile, false));

            for(int i = 0; i < profileData.size(); i++){
                if(profileData.get(i).split(" ")[0].equals(this.getName())){
                    String profileString = this.getName() + " " + 
                        LocalDateTime.now().toString() + " " + 
                        String.valueOf(levelIndex);

                    profileData.set(i, profileString);
                }

            }
            for(int i = 0; i<profileData.size(); i++){
                wr.write(profileData.get(i) + "\n");
            }
            wr.close();

            } catch(Exception e){

        }
    }
}

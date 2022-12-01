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
    private static final String profileFile = "src/main/resources/com/group22/profiles.txt";

    public Profile(String name, LocalDateTime dateLastActive){
        this.name = name;
        this.dateLastActive = dateLastActive;
    }

    public Profile(){
    }


    public void saveToFile(){        
        try {
            FileWriter myWriter = new FileWriter(profileFile, true);
            myWriter.append(this.getName() + " " + this.getTimeAgoLastActive() + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred writing to the profiles.txt file");
            e.printStackTrace();
        }
    }

    public List<Profile> getAllProfiles(){
        List<String> profileFileData = getProfileData();
        List<Profile> profiles = new ArrayList<Profile>();

        for(int i=0; i<profileFileData.size(); i++){
            String[] splitAtSpace = profileFileData.get(i).split(" ");
            Profile profile = new Profile(splitAtSpace[0], LocalDateTime.parse(splitAtSpace[1]));
            profiles.add(profile);
        }
        
        return profiles;

    }


    private List<String> getProfileData(){
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
        List<String> profileFileData = getProfileData();

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
            List<String> profileData = getProfileData();
            BufferedWriter wr = new BufferedWriter(new FileWriter(profileFile, false));

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
    
    public void getUnlockedLevels(){

    }

    public String getName(){
        return name;
    }

    public String getTimeAgoLastActive(){
        return dateLastActive.toString();
    }
}

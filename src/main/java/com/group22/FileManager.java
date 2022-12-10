package com.group22;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 */
public class FileManager {

    /**
     * 
     */
    public FileManager(){}

    
    /** 
     * @param file
     * @return {@link ArrayList} {@link String}
     */
    public ArrayList<String> getDataFromFile(File file) {
        ArrayList<String> dataArray = new ArrayList<String>();
        
        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNext()) {
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

    
    /** 
     * @param folder
     * @param startsWith
     * @param endsWith
     * @return File[]
     */
    public File[] getMatchingFiles(
        String folder, String startsWith, String endsWith) {

        File f = new File(folder);
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(startsWith) && name.endsWith(endsWith);
            }
        });

        if (matchingFiles == null) {
            matchingFiles = new File[0];
        }

        return matchingFiles;
    }

    
    /** 
     * @param dataArray
     * @param file
     */
    public void saveFile(ArrayList<String> dataArray, File file) {
        try (FileWriter writer = new FileWriter(file, true)) {

            for (String data : dataArray) {
                writer.append(data + "\n");
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred writing to the file");
            e.printStackTrace();
        }
    }
}

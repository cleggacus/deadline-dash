package com.group22;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The {@code FileManager} class has methods which ease the loading of
 * text files.
 * 
 * @author Sam Austin
 * @version 1.0
 */
public class FileManager {

    public FileManager(){}

    
    /** 
     * This method takes in a {@link File} and returns an
     * {@link ArrayList} of {@link String}'s with the data from
     * the file.
     * @param file The {@link File} from which the data will be
     * retrieved.
     * @return {@link ArrayList} {@link String} Each String object in this
     * ArrayList is one line from the file.
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
     * Checks a folder for files which start and end with a given string.
     * @param folder    The folder in which files are checked
     * @param startsWith    The string that matching file names begin with.
     * @param endsWith  The string that the matching file names end with.
     * @return File[]   An array of files which were found.
     */
    public File[] getMatchingFiles(
        File folder, String startsWith, String endsWith) {
        File[] matchingFiles = folder.listFiles(new FilenameFilter() {
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
     * Saves given data to a given file.
     * @param dataArray Each String represents a line to write to
     * the file.
     * @param file The file to be written to.
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

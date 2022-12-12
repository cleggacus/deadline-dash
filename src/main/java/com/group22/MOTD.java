package com.group22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * The class {@code MOTD} handles the get requests for the api for the 
 * message of the day. It is used for both requests and solving the puzzle
 * given from the api.
 * 
 * @author Liam Clegg
 * @version 1.0
 */
public class MOTD {
    private static final String API_PUZZLE_URL = 
        "http://cswebcat.swan.ac.uk/puzzle";
    private static final String API_SOLUTION_URL = 
        "http://cswebcat.swansea.ac.uk/message";

    
    /** 
     * Gets the current message of the day by requesting the motd api.
     * 
     * @return A string containing the message of the day.
     */
    public static String getMOTD() {
        String puzzle = fetch(API_PUZZLE_URL);
        String result = 
            fetch(API_SOLUTION_URL + "?solution=" + solvePuzzle(puzzle));

        return result;
    }

    
    /** 
     * Solves the puzzle given from the message of the day api.
     * 
     * @param puzzleString puzzle string given from the api
     * @return the solution string from the puzzle
     */
    private static String solvePuzzle(String puzzleString) {
        String result = "";

        for (int i = 0; i < puzzleString.length(); i++) {
            boolean isBackward = i % 2 == 0;
            char c = puzzleString.charAt(i);

            c += (isBackward ? -1 : 1) * (i + 1);

            result += wrapAZ(c);
        }

        result += "CS-230";
        result = result.length() + result;

        return result;
    }

    
    /** 
     * Wraps char alphabet around from Z to A or A to Z
     * 
     * @param c char to be wrapped
     * @return char after wrap
     */
    private static char wrapAZ(char c) {
        int range = 'Z' - 'A' + 1;

        while (c > 'Z') {
            c -= range;
        }

        while (c < 'A') {
            c += range;
        }

        return c;
    }

    
    /** 
     * Fetches using a get request at a given url and returns
     * with a string response.
     * 
     * @param urlString url string to be requested.
     * @return The response string.
     */
    private static String fetch(String urlString) {
        String content = "";

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                content = getContent(conn);
            }
        } catch (UnknownHostException e) {
            content = 
                "Can't get message of the day. Check your internet connection.";
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    
    /** 
     * Gets the content from the reponse as string.
     * 
     * @param conn connection for to get the content from.
     * @return Response content string.
     */
    private static String getContent(HttpURLConnection conn) {
        String content = "";

        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

            String inputLine;


            while ((inputLine = reader.readLine()) != null) {
                content += inputLine;
            }

            reader.close();

            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}

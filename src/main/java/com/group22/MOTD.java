package com.group22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;

public class MOTD {
    private static final String API_PUZZLE_URL = "http://cswebcat.swan.ac.uk/puzzle";
    private static final String API_SOLUTION_URL = "http://cswebcat.swansea.ac.uk/message";

    public static String getMOTD() {
        String puzzle = fetch(API_PUZZLE_URL);
        String result = fetch(API_SOLUTION_URL + "?solution=" + solvePuzzle(puzzle));

        return result;
    }

    private static String solvePuzzle(String puzzleString) {
        String result = "";

        for(int i = 0; i < puzzleString.length(); i++) {
            boolean isBackward = i % 2 == 0;
            char c = puzzleString.charAt(i);

            c += (isBackward ? -1 : 1) * (i+1);

            result += wrapAZ(c);
        }

        result += "CS-230";
        result = result.length() + result;

        return result;
    }

    private static char wrapAZ(char c) {
        int range = 'Z' - 'A' + 1;

        while(c > 'Z') {
            c -= range;
        }

        while(c < 'A') {
            c += range;
        }

        return c;
    }

    private static String fetch(String urlString) {
        String content = "";

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            if(conn.getResponseCode() == 200) {
                content = getContent(conn);
            }
        } catch (UnknownHostException e) {
            content = "Can't get message of the day. Check your internet connection.";
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

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

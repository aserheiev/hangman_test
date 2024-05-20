package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TopicData {

    private List<List<String>> topicList;

    private String seven = "------------   \n" +
            " |          |  \n" +
            " |          |  \n" +
            " |          |  \n" +
            " |          |  \n" +
            " |          O  \n" +
            " |         /|\\ \n" +
            " |         / \\ \n" +
            " |             \n" +
            " |             \n" +
            " |             \n" +
            "---            ";

    private String[][] hangman = StoreHangman();

    public TopicData() throws IOException {
        topicList = FillDataList();
        StoreHangman();
    }

    private List<List<String>> FillDataList() throws IOException {
        String fileData = "";
        List<List<String>> topicList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\user\\Pictures\\topics.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = reader.readLine();
            }

            fileData = sb.toString();
        }

        // here I'm splitting the file into lines and putting the resulting strings in an array

        String[] topics = fileData.split("\r\n");


        for (String s : topics) {
            topicList.add(Arrays.stream(s.split(",")).toList());
        }

        return topicList;

    }


    public int PrintTopicList() {
        int i = 0;

        // gets the first element in each list which is the category name
        // the counter is used to then report the number of available options
        for (List<String> list : topicList) {
            i++;
            System.out.println(i + ". " + list.getFirst());
        }

        return i;
    }

    public String ChooseWord(int auswahl) {
        Random generator = new Random();

        return topicList.get(auswahl).get(generator.nextInt(1, topicList.get(auswahl).size()));
    }

    public String[][] StoreHangman() {
        String[] splits = seven.split("\n");

        // this assumes uniform width

        String[][] hangman = new String[4][splits.length / 2];

        // needed to keep the split array position consistent instead of it resetting inside the loop
        int splitPosition = 0;

        // splits the hangman into 4 quadrants, only works properly if the dimensions are even (I think)
        for (int i = 0; i < 3; i += 2) {

            for (int j = 0; j < splits.length / 2; j++) {
                hangman[i][j] = splits[splitPosition].substring(0, splits[0].length() / 2);
                hangman[i + 1][j] = splits[splitPosition].substring(splits[0].length() / 2);
                splitPosition++;
            }
        }
        return hangman;
    }

    public void DrawHangman(int maxVersuche, int versuche) {
        int percentage = 100 - (int) (((float) versuche / (float) maxVersuche) * 100);
        int[] percentages = {0, 0, 0, 0};
        // String[] currentHangman = new String[hangman[0].length * 2];
        String[] currentHangman = {"", "", "", "", "", "", "", "", "", "", "", ""};

        for (int i = 0; i < 4; i++) {
            if (percentage >= 25) {
                percentages[i] = hangman[0].length;
                percentage -= 25;
            } else {
                percentages[i] = (int) Math.ceil(((float) percentage / 25f) * hangman[0].length);
                break;
            }
        }


        // this is shit and needs to be remade tomorrow
        // fill up first quadrant (from the bottom)
        for (int i = hangman[0].length - 1; i >= 0; i--) {
            if (percentages[1] > 0) {
                currentHangman[i] += hangman[0][i];
                percentages[1] -= 1;
            } else {
                // for testing only, change this to be dynamic
                currentHangman[i] += "        ";
            }
        }

        System.out.println("First worked");

        for (int i = 0; i < hangman[0].length; i++) {
            if (percentages[2] > 0) {
                currentHangman[i] += hangman[1][i];
                percentages[2] -= 1;
            } else {
                // for testing only, change this to be dynamic
                currentHangman[i] += "        ";
            }
        }

        System.out.println("Second worked");

        for (int i = hangman[0].length * 2 - 1; i >= hangman[0].length; i--) {
            if (percentages[0] > 0) {
                currentHangman[i] += hangman[2][i - hangman[0].length];
                percentages[0] -= 1;
            } else {
                // for testing only, change this to be dynamic
                currentHangman[i] += "        ";
            }
        }

        System.out.println("Third worked");

        for (int i = hangman[0].length; i < hangman[0].length * 2; i++) {
            if (percentages[3] > 0) {
                currentHangman[i] += hangman[3][i - hangman[0].length];
                percentages[3] -= 1;
            } else {
                // for testing only, change this to be dynamic
                currentHangman[i] += "        ";
            }
        }

        System.out.println("Fourth worked");

        for (String s : currentHangman) {
            System.out.println(s);
        }

    }


}


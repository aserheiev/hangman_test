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

    private String seven = " ------------  \n" +
            " |          |  \n" +
            " |          |  \n" +
            " |          |  \n" +
            " |          |  \n" +
            " |          |  \n" +
            " |          |  \n" +
            " |          |  \n" +
            " |          O  \n" +
            " |         /|\\ \n" +
            " |         / \\ \n" +
            "---            ";

    private String[][] hangman = StoreHangman();

    public TopicData() throws IOException {
        topicList = FillDataList();
        StoreHangman();
    }

    private List<List<String>> FillDataList() throws IOException {
        String fileData = "";
        List<List<String>> topicList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\AntonSerheiev\\Desktop\\topics.txt"))) {
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
        String[][] hangman = new String[4][];

        if (splits.length % 2 != 0) {
            hangman[0] = new String[splits.length / 2];
            hangman[1] = new String[splits.length / 2];
            hangman[2] = new String[splits.length / 2 + 1];
            hangman[3] = new String[splits.length / 3];
        } else {
            hangman = new String[4][splits.length/2];
        }

        int position = 0;

        for (int i = 0; i < splits.length; i++) {
            if (i < splits.length / 2) {
                hangman[0][position] = splits[i].substring(0, splits[i].length() / 2);
                hangman[2][position] = splits[i].substring(splits[i].length() / 2);
                position++;
            }
            if (i >= splits.length / 2) {
                if (i == splits.length / 2) {
                    position = 0;
                }
                hangman[1][position] = splits[i].substring(0, splits[i].length() / 2);
                hangman[3][position] = splits[i].substring(splits[i].length() / 2);
                position++;
            }
        }
        return hangman;
    }

    public void DrawHangman(int maxVersuche, int versuche) {
        int overallPercentage = 100 - (int) (((float) versuche / (float) maxVersuche) * 100);
        int[] quadrantPercentages = {0, 0, 0, 0};
        String[] currentHangman = new String[hangman[0].length + hangman[2].length];

        Arrays.fill(currentHangman, "");

        // determines how many lines to fill in each quadrant based on the overall overallPercentage
        for (int i = 0; i < 4; i++) {
            if (overallPercentage >= 25) {
                quadrantPercentages[i] = hangman[0].length;
                overallPercentage -= 25;
            } else {
                quadrantPercentages[i] = (int) Math.ceil(((float) overallPercentage / 25f) * hangman[0].length);
                break;
            }
        }

        for (int i = 0; i < 4; i++) {

        }

        for (String s : currentHangman) {
            System.out.println(s);
        }

    }


}


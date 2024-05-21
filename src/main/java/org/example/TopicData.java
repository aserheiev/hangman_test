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

    private String seven = " ------------\n" +
            " |          |\n" +
            " |          |\n" +
            " |          |\n" +
            " |          |\n" +
            " |          |\n" +
            " |          |\n" +
            " |          O\n" +
            " |         /|\\\n" +
            " |         / \\\n" +
            " |            \n" +
            " |            \n" +
            "---           ";

    private String[][] hangman = StoreHangman();

    public TopicData() throws IOException {
        topicList = FillDataList();
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
        String[][] hangman = new String[4][];

        if (splits.length % 2 != 0) {
            hangman[0] = new String[splits.length / 2];
            hangman[1] = new String[splits.length / 2 + 1];
            hangman[2] = new String[splits.length / 2];
            hangman[3] = new String[splits.length / 2 + 1];
        } else {
            hangman = new String[4][splits.length / 2];
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
        String[] currentHangman = new String[hangman[0].length + hangman[1].length];

        Arrays.fill(currentHangman, "");

        // determines how many lines to fill in each quadrant based on the overall overallPercentage
        // this STILL assumes uniform length

        for (int i = 1; i >= 0; i--) {
            if(overallPercentage > 25) {
                quadrantPercentages[i] = hangman[i].length;
                overallPercentage -= 25;
            } else {
                quadrantPercentages[i] = (int) Math.ceil(((float) overallPercentage / 25f) * hangman[i].length);
                overallPercentage = 0;
            }
        }

        for (int i = 2; i < 4; i++) {
            if(overallPercentage > 25) {
                quadrantPercentages[i] = hangman[i].length;
                overallPercentage -= 25;
            } else {
                quadrantPercentages[i] = (int) Math.ceil(((float) overallPercentage / 25f) * hangman[i].length);
                overallPercentage = 0;
            }
        }


        for (int quadrantPercentage : quadrantPercentages) {
            System.out.println(quadrantPercentage);
        }

        int position = (hangman[0].length + hangman[1].length) - 1;

        // draw the first two quadrants backwards

        for (int i = 1; i >= 0; i--) {
            for (int j = hangman[i].length - 1; j >= 0; j--) {
                if (quadrantPercentages[i] > 0) {
                    currentHangman[position] += hangman[i][j];
                    quadrantPercentages[i]--;
                    position--;
                } else {
                    for (int k = 0; k < hangman[i][j].length(); k++) {
                        currentHangman[position] += " ";
                    }
                    position--;
                }
            }
        }

        // reset the position and draw the remaining two quadrants normally
        position = 0;

        for (int i = 2; i < 4; i++) {
            for (int j = 0; j < hangman[i].length; j++) {
                if (quadrantPercentages[i] > 0) {
                    currentHangman[position] += hangman[i][j];
                    quadrantPercentages[i]--;
                    position++;
                } else {
                    for (int k = 0; k < hangman[i][j].length(); k++) {
                        currentHangman[position] += " ";
                    }
                    position++;
                }
            }
        }

        for (String s : currentHangman) {
            System.out.println(s);
        }

    }
}


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

    private String seven = " ------------ \n" +
            " |          | \n" +
            " |          O \n" +
            " |         /|\\\n" +
            " |         / \\\n" +
            " |            \n" +
            " |            \n" +
            " |            \n" +
            "---           ";

    public TopicData() throws IOException {
        topicList = FillDataList();
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

    public void DrawHangman() {
        String[] splits = seven.split("\n");

        System.out.println("SPLITS[0]");
        System.out.println(splits[1]);
        // this assumes uniform width

        char[][] hangermann = new char[splits.length][splits[0].length()];

        for (int i = 0; i < hangermann.length; i++) {
            hangermann[i] = splits[i].toCharArray();
        }

        for (char[] arry : hangermann) {
            for (char c : arry) {
                System.out.print(c);
            }
            System.out.println("");
        }

        System.out.println("DEBUG: " + hangermann[0].length/2);

        int fulldraw = hangermann.length * 2;

        // this will be the needed draw percentage
        // int drawLimit =

    }
}


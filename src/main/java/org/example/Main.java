package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        PlayGame();

    }

    public static void PlayGame() throws IOException {
        int auswahl;
        int versuche;
        String word;
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        Scanner input = new Scanner(System.in);

        List<Character> correctLetters = new ArrayList<>();
        List<Character> incorrectLetters = new ArrayList<>();


        TopicData topicData = new TopicData();

        int choiceCount = topicData.PrintTopicList();

        auswahl = GetValue(choiceCount);

        System.out.println("Your random word is: ");

        word = topicData.ChooseWord(auswahl - 1);

        int maxVersuche = word.length();

        versuche = word.length();

        System.out.println(word);

        // topicData.StoreHangman();
        boolean guessed = false;

        while (!guessed && versuche > 0) {

            topicData.DrawHangman(maxVersuche, versuche);

            String wordProgress = word;

            for (int i = 0; i < wordProgress.length(); i++) {
                if (correctLetters.contains(word.toLowerCase().charAt(i))) {
                    System.out.print(word.charAt(i) + " ");
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println("");

            for (int i = 0; i < incorrectLetters.size(); i++) {
                System.out.print(incorrectLetters.get(i) + " ");
            }
            System.out.println("");

            System.out.println("Enter letter");

            String letterInput = input.nextLine().toLowerCase();

            if (letterInput.length() > 1) {
                System.out.println("Too long by far");
                continue;
            } else {
                if (letterInput.length() < 1) {
                    System.out.println("Too short by far");
                    continue;
                }
            }

            char letter = letterInput.charAt(0);

            if (incorrectLetters.contains(letter) || correctLetters.contains(letter)) {
                System.out.println("Duplicate letter!");
                continue;
            }

            boolean matchFound = false;

            for (int i = 0; i < word.length(); i++) {
                if (letter == word.toLowerCase().charAt(i)) {
                    matchFound = true;
                }
            }
            if (matchFound) {
                System.out.println("CORRECT");
                correctLetters.add(letter);
            } else {
                System.out.println("WRONG IDIOT");
                incorrectLetters.add(letter);
                versuche--;
                System.out.println("Remaining tries: " + versuche);
            }

            guessed = true;
            for (int i = 0; i < word.length(); i++) {
                if (!correctLetters.contains(word.toLowerCase().charAt(i))) {
                    guessed = false;
                }
            }
        }
        topicData.DrawHangman(maxVersuche, versuche);
        System.out.println("ITS OVER");
    }

    public static void PrintState(int versuche) {
        System.out.println("Remaining tries: " + versuche);
    }


    public static int GetValue(int bound) {
        int zahl = 0;
        Scanner input = new Scanner(System.in);
        boolean valid = false;

        while (!valid) {
            try {
                zahl = Integer.parseInt(input.nextLine());
                if (zahl < 1 || zahl > bound) {
                    System.out.println("Please select one of the options");
                } else {
                    return zahl;
                }
            } catch (Exception e) {
                // Zahl ist keine Zahl
                System.out.println("Give me a real number chief");
            }
        }

        // divine light severed
        return zahl;
    }

}
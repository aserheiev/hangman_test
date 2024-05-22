package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static void main(String[] args) throws IOException {

        System.out.println(ANSI_CYAN + "Welcome to HANGMAN" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "Choose your topic..." + ANSI_RESET);
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

        word = topicData.ChooseWord(auswahl - 1);

        int maxVersuche = word.length();

        versuche = word.length();

        // topicData.StoreHangman();
        boolean guessed = false;

        while (!guessed && versuche > 0) {
            System.out.print(ANSI_RED);
            topicData.DrawHangman(maxVersuche, versuche);
            System.out.print(ANSI_RESET);
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
                System.out.println(ANSI_RED + "Too long by far" + ANSI_RESET);
                continue;
            } else {
                if (letterInput.length() < 1) {
                    System.out.println(ANSI_RED + "Too short by far" + ANSI_RESET);
                    continue;
                }
            }

            char letter = letterInput.charAt(0);

            if (incorrectLetters.contains(letter) || correctLetters.contains(letter)) {
                System.out.println(ANSI_RED + "Duplicate letter!" + ANSI_RESET);
                continue;
            }

            boolean matchFound = false;

            for (int i = 0; i < word.length(); i++) {
                if (letter == word.toLowerCase().charAt(i)) {
                    matchFound = true;
                }
            }
            if (matchFound) {
                System.out.println(ANSI_GREEN + "CORRECT" + ANSI_RESET);
                correctLetters.add(letter);
            } else {
                System.out.println(ANSI_RED + "WRONG" + ANSI_RESET);
                incorrectLetters.add(letter);
                versuche--;
                System.out.println(ANSI_YELLOW + "Remaining tries: " + versuche + ANSI_RESET);
            }

            guessed = true;
            for (int i = 0; i < word.length(); i++) {
                if (!correctLetters.contains(word.toLowerCase().charAt(i))) {
                    guessed = false;
                }
            }
        }

        System.out.print(ANSI_RED);
        topicData.DrawHangman(maxVersuche, versuche);
        System.out.println("ITS OVER" + ANSI_RESET);

        if (guessed) {
            System.out.println(ANSI_CYAN + "YOU WIN" + ANSI_RESET);
        }
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
                    System.out.println(ANSI_YELLOW + "Please select one of the options" + ANSI_RESET);
                } else {
                    return zahl;
                }
            } catch (Exception e) {
                // Zahl ist keine Zahl
                System.out.println(ANSI_YELLOW + "Give me a real number chief" + ANSI_RESET);
            }
        }

        // divine light severed
        return zahl;
    }

}
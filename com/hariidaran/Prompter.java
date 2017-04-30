package com.hariidaran;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Prompter {
    private Jar mJar;

    private BufferedReader mReader;

    private String mName;
    private int mNumberOfGuesses = 0;
    private String mFinalStatement;

    public Prompter(Jar jar) {
        mJar = jar;
        mReader = new BufferedReader(new InputStreamReader(System.in));
    }

    // -HELPER-METHODS--------------------------------------------

    // Tries to convert a string into a float, and checks if an exception is thrown
    // If yes, returns true; if no, returns false
    private boolean isNaN(String string) {
        try {
            float number = Float.parseFloat(string);
        } catch (NumberFormatException nfe) {
            return true;
        }

        return false;
    }

    // Prints title
    private void printTitle(String title) {
        System.out.printf("%n%s%n", title.toUpperCase());
        System.out.printf("========================%n%n");
    }

    // Explains the game
    private void explainGame() {
        System.out.printf("%n" +
                "This is a game where you have to guess the" +
                "%n" +
                "number of items in a jar (which you can't see)." +
                "%n%n" +
                "As you are the admin of this game, you can" +
                "%n" +
                "enter what the items in the jar are to be" +
                "%n" +
                "called. You can also enter the maximum number" +
                "%n" +
                "of items." +
                "%n%n");
    }

    // Method used for prompting user to guess number
    private void promptForGuess() throws IOException {
        System.out.printf("%nYour goal is to guess how many %ss are there in a jar " +
                        "which can hold from 1 to %d %ss.",
                mJar.getItemName(), mJar.getMaxItems(), mJar.getItemName());

        System.out.printf("%n%nReady? (press ENTER or RETURN to play)");
        mReader.readLine();

        String guess = "";

        List<String> guesses = new ArrayList<>();

        while (!guess.equals(mJar.getItems() + "")) {
            System.out.printf("%nGuess: ");
            guess = mReader.readLine();

            if (isNaN(guess) || (Integer.parseInt(guess) == 0 && Integer.parseInt(guess) == 1)) {
                System.out.println("No special characters or letters or 0 allowed. Try again.");
            } else if (guesses.contains(guess)) {
                System.out.println("Number already guessed before.");
            } else {
                guesses.add(guess);
                mNumberOfGuesses++;
            }
        }
    }

    // Shows the score
    private void showStatement() {
        if (mNumberOfGuesses <= (mJar.getMaxItems() / 2) + 1) {
            if (mNumberOfGuesses == 1) {
                mFinalStatement = String.format("%nYou took 1 try to guess that there");
            } else {
                mFinalStatement = String.format("%nYou took %d tries to guess that there", mNumberOfGuesses);
            }

            if (mJar.getItems() == 1) {
                mFinalStatement += String.format(" is 1 %s in the jar,", mJar.getItemName());
            } else {
                mFinalStatement += String.format(" are %d %ss in the jar,", mJar.getItems(), mJar.getItemName());
            }

            mFinalStatement += " and it's like you knew the answer! Good job!";

            System.out.print(mFinalStatement);
        } else {
            if (mNumberOfGuesses == 1) {
                mFinalStatement = String.format("%nYou took 1 try to guess that there");
            } else {
                mFinalStatement = String.format("%nYou took %d tries to guess that there", mNumberOfGuesses);
            }

            if (mJar.getItems() == 1) {
                mFinalStatement += String.format(" is 1 %s in the jar,", mJar.getItemName());
            } else {
                mFinalStatement += String.format(" are %d %ss in the jar,", mJar.getItems(), mJar.getItemName());
            }

            mFinalStatement += " but you could've done better. Try again.";

            System.out.print(mFinalStatement);
        }
    }

    public List<String> getResults() {
        return new ArrayList<>(Arrays.asList(new String[]{
                mName, mJar.getItems() + " " + mJar.getItemName(),
                mJar.getMaxItems() + " " + mJar.getItemName(),
                mNumberOfGuesses + ""}
        ));
    }

    public void printError(Exception e) {
        System.out.printf("%n%nOops! Something went wrong in the program.%n" +
                "Try running the program again.%n%n");
        e.printStackTrace();
    }

    // -MAIN-METHODS----------------------------------------------

    // Play the program
    public void play() {
        explainGame();

        try {
            startAdminSetup();
            startGame();
        } catch (IOException ioe) {
            printError(ioe);
        }
    }

    // Administrator setup
    private void startAdminSetup() throws IOException {
        printTitle("Administrator Setup");

        System.out.print("Name of the player:  ");
        mName = mReader.readLine();

        System.out.print("Name of item (the noun should be singular):  ");
        String itemName = mReader.readLine();
        if (!(itemName.equals("") || itemName.equals(String.format("%n"))) || isNaN(itemName)) {
            mJar.setItemName(itemName);
        } else {
            mJar.setItemName("item");
        }

        String maxItemsString = "";
        int maxItems = 0;

        boolean isNotMatched = isNaN(maxItemsString) ||
                maxItems == 0 ||
                maxItems == 1 ||
                maxItemsString.equals(String.format("%n")) ||
                maxItemsString.equals("f");

        while (isNotMatched) {
            System.out.printf("Maximum number of %ss in the jar:  ", mJar.getItemName());
            maxItemsString = mReader.readLine().trim().toLowerCase();

            try {
                maxItems = Integer.parseInt(maxItemsString);
            } catch (NumberFormatException nfe) {
                System.out.println("1 or 0, and special characters, letters are not allowed.");
            }

            isNotMatched = isNaN(maxItemsString) ||
                    maxItems == 0 ||
                    maxItems == 1 ||
                    maxItemsString.equals(String.format("%n")) ||
                    maxItemsString.equals("") ||
                    maxItemsString.equals("f");
        }

        mJar.setMaxItems(maxItems);
    }

    // Start the game
    private void startGame() throws IOException {
        mJar.fill();

        promptForGuess();

        showStatement();
    }
}
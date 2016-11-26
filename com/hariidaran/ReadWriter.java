package com.hariidaran;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadWriter {
    private Prompter mPrompter;
    private List<List<String>> mScores;

    public ReadWriter(Prompter prompter) {
        mScores = new ArrayList<>();
        mPrompter = prompter;
    }

    // Import scores from specific file
    public void readFrom(String fileName) {
        try (
                FileInputStream fis = new FileInputStream(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        ) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] args = line.split("\\|");
                List<String> score = new ArrayList<>(Arrays.asList(new String[]{
                        args[0], args[1], args[2], args[3]
                }));

                mScores.add(score);

                int numberOfItems = Integer.parseInt(args[1].split("\\s+")[0]);

                String verb = "w";
                args[3] += " tr";

                if (numberOfItems == 1) {
                    verb += "as";
                } else {
                    verb += "ere";
                    args[1] += "s";
                }

                if (args[3].equals("1 tr")) {
                    args[3] += "y";
                } else {
                    args[3] += "ies";
                }

                System.out.printf("%s found out there %s %s in a jar " +
                                "which could hold %ss, in %s.%n",
                        args[0], verb, args[1], args[2], args[3]);
            }
        } catch (FileNotFoundException fnfe) {
            System.out.printf("%nFile \"%s\" doesn't exist.%n", fileName);
        } catch (IOException ioe) {
            mPrompter.printError(ioe);
        }
    }

    // Export scores to specified file
    public void saveTo(String fileName) {
        try (
                FileOutputStream fos = new FileOutputStream(fileName);
                PrintWriter writer = new PrintWriter(fos);
        ) {
            List<String> score = mPrompter.getResults();

            mScores.add(score);

            for (List<String> args : mScores) {
                writer.printf("%s|%s|%s|%s%n",
                        args.get(0), args.get(1), args.get(2), args.get(3));
            }

            System.out.printf("%n%nSaving scores...%n%n");
        } catch (IOException ioe) {
            mPrompter.printError(ioe);
        }
    }
}
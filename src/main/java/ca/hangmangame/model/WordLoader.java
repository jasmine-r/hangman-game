package ca.hangmangame.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WordLoader {
    private ArrayList<String> wordList = new ArrayList<>();

    public void loadWordList() {
        File wordFile = new File("./src/words.txt");
        try {
            Scanner scanner = new Scanner(wordFile);
            while (scanner.hasNextLine()) {
                wordList.add(scanner.nextLine().trim());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Errors encountered when processing file. Does not exist yet?");
            e.printStackTrace();
        }
    }

    public ArrayList<String> getWordList() {
        return wordList;
    }
}

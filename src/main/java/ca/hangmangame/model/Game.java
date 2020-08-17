package ca.hangmangame.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {
    private long id;

    private String word;
    private WordLoader wordLoader = new WordLoader();
    private String dashOutput;
    private String[] dashRepresentation;

    private int totalGuesses;
    private int incorrectGuesses;
    private ArrayList<String> previousGuesses = new ArrayList<>();
    private GameStatus status;
    private boolean validInputFlag;
    private final int MAX_INCORRECT_GUESSES = 7;

    public Game() {
        wordLoader.loadWordList();
        setRandomWord(wordLoader.getWordList());
        setStatus(GameStatus.ACTIVE);
        this.totalGuesses = 0;
        this.incorrectGuesses = 0;
        this.validInputFlag = true;
    }

    private void setRandomWord(ArrayList<String> words) {
        if (words.size() != 0) {
            // get random word from arraylist of words
            Random random = new Random();
            this.word = words.get(random.nextInt(words.size()));
        }
        // set up dash representation
        dashRepresentation = new String[word.length()];
        Arrays.fill(dashRepresentation, "_");
        this.dashOutput = String.join(" ", dashRepresentation);
    }

    private void validateInputtedGuess(String guess) {
        setValidInputFlag(guess.length() > 0 && Character.isLetter(guess.charAt(0)));
    }

    public void checkGuess(String guess) {
        validateInputtedGuess(guess);

        if (isValidInputFlag()) {
            // ensure guess is in lowercase to match the word that is to be guessed
            guess = guess.toLowerCase();

            if (guess.length() > 1) {
                guess = guess.substring(0, 1);
            }

            // confirmed that guess is a single letter (i.e. is valid input & is length 1)
            if (word.contains(guess)) {
                // check if this letter has already been guessed
                if (!previousGuesses.contains(guess)) {
                    updateDashRepresentation(guess);
                } // otherwise this letter is in the word, but has already been guessed
            } else {
                // the word does not contain the guessed letter
                incrementIncorrectGuesses();
            }
            incrementTotalGuesses();
            previousGuesses.add(guess);

            // now verify the status of the game
            verifyGameStatus();
        }
    }

    private void verifyGameStatus() {
        // check for loss
        if (incorrectGuesses > MAX_INCORRECT_GUESSES) {
            // reached max incorrect guesses; player has lost
            setStatus(GameStatus.LOST);
            return;
        }

        // check for win
        String[] wordAsArray = word.split("");

        for (int i = 0; i < wordAsArray.length; i++) {
            if (!previousGuesses.contains(wordAsArray[i])) {
                return;
                // game is still active; player has not won the game at this point in time.
            }
        }
        // player won
        setStatus(GameStatus.WON);
    }

    private void updateDashRepresentation(String letterGuessed) {
        letterGuessed.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            Character currentLetter = word.charAt(i);
            if (currentLetter.compareTo(letterGuessed.charAt(0)) == 0) {
                dashRepresentation[i] = letterGuessed;
            }
        }
        this.dashOutput = String.join(" ", dashRepresentation);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public int getTotalGuesses() {
        return totalGuesses;
    }

    public int getIncorrectGuesses() {
        return incorrectGuesses;
    }

    public void incrementTotalGuesses() {
        this.totalGuesses += 1;
    }

    public void incrementIncorrectGuesses() {
        this.incorrectGuesses += 1;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public String getDashOutput() {
        return dashOutput;
    }

    public boolean isValidInputFlag() {
        return validInputFlag;
    }

    public void setValidInputFlag(boolean validInputFlag) {
        this.validInputFlag = validInputFlag;
    }
}

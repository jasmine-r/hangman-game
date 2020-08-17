package ca.hangmangame.model;

public class Game {
    private long id;
    private String word;
    private int totalGuesses;
    private int incorrectGuesses;
    private GameStatus status;
    private final int MAX_INCORRECT_GUESSES = 7;

    public Game() {

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

    public void incrementTotalGuesses(int totalGuesses) {
        this.totalGuesses += 1;
    }

    public void incrementIncorrectGuesses(int incorrectGuesses) {
        this.incorrectGuesses += 1;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}

package ca.hangmangame.controllers;

import ca.hangmangame.model.Game;
import ca.hangmangame.model.GameStatus;
import ca.hangmangame.model.Guess;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class HangmanController {
    private List<Game> games = new ArrayList<>();
    private AtomicLong nextId = new AtomicLong();

    @GetMapping("/welcome")
    public String showWelcomePage(Model model) {
        return "welcome";
    }

    @PostMapping("/game")
    public String runNewGame(Model model) {
        // show new game
        Game currentGame = new Game();
        currentGame.setId(nextId.incrementAndGet());
        games.add(currentGame);
        model.addAttribute("currentGame", currentGame);
        Guess guess = new Guess();
        model.addAttribute("guess", guess);
        return "game";
    }

    @GetMapping("/game/{id}")
    public String runExistingGame(@PathVariable("id") long gameId, Model model) {
        for (Game game : games) {
            if (game.getId() == gameId) {
                // set attributes here - need to recall specifics from the game specified
                model.addAttribute("currentGame", game);
                Guess guess = new Guess();
                model.addAttribute("guess", guess);

                if (game.getStatus() == GameStatus.ACTIVE) {
                    return "game";
                } else {
                    return "gameover";
                }
            }
        }
        throw new GameNotFoundException();
    }

    @PostMapping("/game/{id}")
    public String continueGame(@PathVariable("id") long gameId, @ModelAttribute("guess") Guess guess, Model model) {
        for (Game game : games) {
            if (game.getId() == gameId) {
                // retrieve guess and update the specified game
                game.checkGuess(guess.getGuessedLetter());
                model.addAttribute("currentGame", game);
                model.addAttribute("guess", guess);

                if (game.getStatus() == GameStatus.ACTIVE) {
                    return "game";
                } else {
                    return "gameover";
                }
            }
        }
        throw new GameNotFoundException();
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(GameNotFoundException.class)
    public String gameNotFoundExceptionHandler(Model model) {
        return "gamenotfound";
    }


}

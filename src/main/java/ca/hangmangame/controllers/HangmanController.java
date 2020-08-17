package ca.hangmangame.controllers;

import ca.hangmangame.model.Game;
import ca.hangmangame.model.Guess;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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


}

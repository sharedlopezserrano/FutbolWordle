import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// CLASS 1: Player - represents one football player
class Player {
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


// CLASS 2: Game - represents ONE round (secret player + attempts)
class Game {
    public static final int WORD_LENGTH = 5;
    public static final int MAX_ATTEMPTS = 6;

    private Player secretPlayer;
    private int attempts;
    private boolean won;

    public Game(Player secretPlayer) {
        this.secretPlayer = secretPlayer;
        this.attempts = 0;
        this.won = false;
    }

    public String getSecret() {
        return secretPlayer.getName();
    }

    public int getAttempts() {
        return attempts;
    }

    public boolean isWon() {
        return won;
    }

    /** The round ends when the player wins or runs out of attempts. */
    public boolean isOver() {
        return won || attempts >= MAX_ATTEMPTS;
    }

    /** A valid guess has exactly 5 letters from A to Z. */
    public boolean isValidGuess(String guess) {
        return guess.length() == WORD_LENGTH && guess.matches("[A-Z]+");
    }

    /**
     * Compares the guess with the secret and counts one attempt.
     * Returns an array: 0 = gray, 1 = yellow, 2 = green (one value per letter).
     */
    public int[] checkGuess(String guess) {
        String secret = getSecret();
        int[] result = new int[WORD_LENGTH];
        boolean[] used = new boolean[WORD_LENGTH]; // secret letters already matched

        // Pass 1: exact matches (green)
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (guess.charAt(i) == secret.charAt(i)) {
                result[i] = 2;
                used[i] = true;
            }
        }

        // Pass 2: letters in the wrong place (yellow)
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (result[i] == 2) continue;
            for (int j = 0; j < WORD_LENGTH; j++) {
                if (!used[j] && guess.charAt(i) == secret.charAt(j)) {
                    result[i] = 1;
                    used[j] = true;
                    break;
                }
            }
        }

        attempts++;
        if (guess.equals(secret)) {
            won = true;
        }
        return result;
    }
}

// MAIN CLASS: user interface (terminal) + file handling

public class FutbolWordle {

    // ANSI colors (work in most terminals; on old Windows CMD use Windows Terminal / IntelliJ / VS Code)
    static final String GREEN  = "\u001B[42m\u001B[30m"; // green background, black text
    static final String YELLOW = "\u001B[43m\u001B[30m"; // yellow background, black text
    static final String GRAY   = "\u001B[100m\u001B[97m"; // gray background, white text
    static final String RESET  = "\u001B[0m";

    static final String PLAYERS_FILE = "players.txt";
    static final String SCORES_FILE = "scores.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Data structure: ArrayList of Player objects, loaded from a file
        ArrayList<Player> players = loadPlayers(PLAYERS_FILE);
        if (players.isEmpty()) {
            System.out.println("No players found. Make sure '" + PLAYERS_FILE
                    + "' is in the same folder, with one 5-letter name per line.");
            return;
        }

        System.out.println("=====================================");
        System.out.println("        FUTBOL WORDLE  (" + players.size() + " players)");
        System.out.println("=====================================");
        System.out.println("Guess the football player in " + Game.MAX_ATTEMPTS
                + " tries (" + Game.WORD_LENGTH + " letters).");
        System.out.println(GREEN + " A " + RESET + " right letter, right place");
        System.out.println(YELLOW + " A " + RESET + " letter is in the name, wrong place");
        System.out.println(GRAY + " A " + RESET + " letter is not in the name");
        System.out.println("Type 'exit' to quit.");
        showStats();
        System.out.println();

        boolean playAgain = true;
        while (playAgain) {
            Player secret = players.get(random.nextInt(players.size()));
            Game game = new Game(secret);

            playRound(scanner, game);

            if (game.isWon()) {
                System.out.println("\nGOAL! You got it in " + game.getAttempts() + " tries: " + game.getSecret());
            } else {
                System.out.println("\nGame over! The player was: " + game.getSecret());
            }

            saveScore(game);

            System.out.print("\nPlay again? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            playAgain = answer.equals("y") || answer.equals("yes");
            System.out.println();
        }

        System.out.println("Thanks for playing! See you on the pitch.(Go watch more soccer!)");
        scanner.close();
    }

    /** Plays one round: asks for guesses until the game is over. */
    static void playRound(Scanner scanner, Game game) {
        while (!game.isOver()) {
            System.out.print("Attempt " + (game.getAttempts() + 1) + "/" + Game.MAX_ATTEMPTS + ": ");
            String guess = scanner.nextLine().trim().toUpperCase();

            if (guess.equals("EXIT")) {
                System.out.println("Bye!");
                System.exit(0);
            }

            // Invalid input doesn't use up an attempt
            if (!game.isValidGuess(guess)) {
                System.out.println("Please type exactly " + Game.WORD_LENGTH + " letters (A-Z, no accents).");
                continue;
            }

            int[] result = game.checkGuess(guess);
            printResult(guess, result);
        }
    }

    /** Prints the guess with colors, using the result array from Game.checkGuess. */
    static void printResult(String guess, int[] result) {
        StringBuilder line = new StringBuilder("            ");
        for (int i = 0; i < guess.length(); i++) {
            String color = result[i] == 2 ? GREEN : result[i] == 1 ? YELLOW : GRAY;
            line.append(color).append(' ').append(guess.charAt(i)).append(' ').append(RESET).append(' ');
        }
        System.out.println(line);
    }

    // FILE HANDLING

    /** READS players.txt (one name per line) and returns an ArrayList of Player. */
    static ArrayList<Player> loadPlayers(String fileName) {
        ArrayList<Player> players = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String name = line.trim().toUpperCase();
                // Only accept valid names (5 letters A-Z); skip blank or wrong lines
                if (name.length() == Game.WORD_LENGTH && name.matches("[A-Z]+")) {
                    players.add(new Player(name));
                } else if (!name.isEmpty()) {
                    System.out.println("Skipping invalid line in " + fileName + ": " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read '" + fileName + "': " + e.getMessage());
        }

        return players;
    }

    /** WRITES one line per game at the end of scores.txt: date,PLAYER,WON/LOST,attempts */
    static void saveScore(Game game) {
        // 'true' = append, so old scores are not erased
        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORES_FILE, true))) {
            String result = game.isWon() ? "WON" : "LOST";
            writer.println(LocalDate.now() + "," + game.getSecret() + "," + result + "," + game.getAttempts());
        } catch (IOException e) {
            System.out.println("Could not save the score: " + e.getMessage());
        }
    }

    /** READS scores.txt (if it exists) and shows games played and games won. */
    static void showStats() {
        File file = new File(SCORES_FILE);
        if (!file.exists()) {
            return; // first time playing
        }

        int played = 0;
        int wins = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // date, player, result, attempts
                if (parts.length == 4) {
                    played++;
                    if (parts[2].equals("WON")) {
                        wins++;
                    }
                }
            }
        } catch (IOException e) {
            return; // stats are optional, so just skip them
        }

        System.out.println("Your record: " + wins + " wins in " + played + " games.");
    }
}
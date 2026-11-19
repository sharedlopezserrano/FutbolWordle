# Overview

As a software engineer, I wanted to strengthen my understanding of core Java concepts by building a complete program from start to finish, instead of only practicing isolated exercises. This project helped me practice designing classes, organizing data in collections, and working with files, which are skills I will use in any larger application.

**Futbol Wordle** is a terminal game based on Wordle, but about football players. The program loads a list of 100 famous players (current and past) from a text file, picks one at random, and the user has 6 attempts to guess the 5-letter name. After each guess, every letter is colored: green if it is in the right place, yellow if it is in the name but in another place, and gray if it is not in the name. At the end of each round, the game saves the result to a file and shows the user's record (wins and games played) the next time it starts.

The whole program is written in a single Java file, `FutbolWordle.java`, with three classes: `Player`, `Game` and `FutbolWordle`. It also uses two text files: `players.txt` (read) and `scores.txt` (written).

My purpose for writing this software was to practice the Java language syntax in a real project: variables, expressions, conditionals, loops, methods, classes and objects, the `ArrayList` collection, and reading and writing files. It also helped me learn how to separate responsibilities between classes (a player, a game round, and the user interface) and how to handle repeated letters correctly in the color logic.

[Software Demo Video](https://www.youtube.com/watch?v=IULgMPLCbdg&feature=youtu.be)

# Development Environment

- Java JDK 8 or higher (compiled with `javac` and run with `java` from the terminal)
- A code editor or IDE of my choice (replace with the one you used, for example VS Code or IntelliJ IDEA)
- Git and GitHub for version control
- A terminal that supports ANSI colors (Windows Terminal, VS Code, IntelliJ, Linux or Mac terminal)

The program is written in **Java**. It uses only the standard library, with no external libraries:

- `java.util.ArrayList` to store the players
- `java.util.Scanner` to read the user's input
- `java.util.Random` to pick a random player
- `java.io.BufferedReader`, `FileReader`, `FileWriter` and `PrintWriter` to read and write files
- `java.io.File` to check if a file exists
- `java.time.LocalDate` to save the date of each game

# Useful Websites

- [Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/)
- [W3Schools Java Tutorial](https://www.w3schools.com/java/)
- [Baeldung: Write to a File in Java](https://www.baeldung.com/java-write-to-file)
- [Markdown Cheat Sheet](https://www.markdownguide.org/cheat-sheet/)

# Future Work

- Show a hint after a few failed attempts (nationality or club)
- Add a winning streak counter
- Show a keyboard with the letters already used
- Add difficulty levels (more or fewer attempts)
- Add an interface or inheritance to support different game modes
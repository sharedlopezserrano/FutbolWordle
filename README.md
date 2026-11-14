# ⚽ Futbol Wordle

A **Wordle-style game** about football players, played in the terminal. Written in Java as a beginner project (This is made for ball knowledge guys).

Guess the secret player (5 letters, usually the surname) in **6 attempts**.

---

## 📋 Table of Contents

- [How to Play](#-how-to-play)
- [Requirements](#-requirements)
- [Installation and Run](#-installation-and-run)
- [Example](#-example)
- [Project Structure](#-project-structure)
- [How the Code Works](#-how-the-code-works)
- [Files the Game Uses](#-files-the-game-uses)

---

## 🎮 How to Play

1. The game loads a list of **100 famous players** (current and past) from `players.txt` and picks one at random.
2. Type a guess of exactly **5 letters** (A-Z, no accents) and press Enter.
3. Each letter gets a color:

| Color | Meaning |
|-------|---------|
| 🟩 Green | Right letter, right place |
| 🟨 Yellow | The letter is in the name, but in another place |
| ⬛ Gray | The letter is not in the name |

4. You have **6 attempts**. Guess the name before they run out!
5. Your result is saved in `scores.txt`, and the game shows your record the next time you start it.

> **Note:** An invalid guess (wrong length or symbols) does **not** use up an attempt.

Type `exit` at any time to quit.

---

## 🧰 Requirements

- Java JDK 8 or higher
- A terminal that supports ANSI colors (IntelliJ, VS Code, Windows Terminal, Linux or Mac terminal)

To check your Java version:

```bash
java -version
```

---

## 🚀 Installation and Run

1. Clone the repository (or download the files):

```bash
git clone https://github.com/your-username/futbol-wordle.git
cd futbol-wordle
```

2. Compile:

```bash
javac FutbolWordle.java
```

3. Run (from the same folder where `players.txt` is):

```bash
java FutbolWordle
```

---

## 🖥️ Example

```text
=====================================
        FUTBOL WORDLE  (100 players)
=====================================
Guess the football player in 6 tries (5 letters).
Type 'exit' to quit.
Your record: 3 wins in 4 games.

Attempt 1/6: salah
             S  A  L  A  H
Attempt 2/6: henry
             H  E  N  R  Y

GOAL! You got it in 2 tries: HENRY
```

*(In the real game each letter has a green, yellow or gray background.)*

---

## 📁 Project Structure

```text
futbol-wordle/
├── FutbolWordle.java   # The whole game (3 classes)
├── players.txt         # The 100 players, one per line
├── scores.txt          # Created automatically after your first game
└── README.md           # This file
```

---

## 🧠 How the Code Works

The code is in one file with **three classes**:

| Class | What it does |
|-------|--------------|
| `Player` | Represents one football player (stores the name, `getName()`) |
| `Game` | Represents one round: the secret player, the attempts, the validation and the color logic (`checkGuess`) |
| `FutbolWordle` | The main class: starts the program, talks to the user and reads/writes the files |

Main methods in `FutbolWordle`:

| Method | What it does |
|--------|--------------|
| `main` | Loads the players, picks a random one and repeats rounds while the user wants to play |
| `playRound` | Reads the guesses and validates them until the game is over |
| `printResult` | Prints the colored letters using ANSI codes |
| `loadPlayers` | **Reads** `players.txt` into an `ArrayList<Player>` |
| `saveScore` | **Writes** the result of each game to `scores.txt` |
| `showStats` | **Reads** `scores.txt` and shows wins and games played |

`Game.checkGuess` works in **two passes** so repeated letters are handled correctly:

1. First pass marks the **green** letters (exact matches)
2. Second pass marks the **yellow** letters (right letter, wrong place) without reusing a letter that was already matched

---

## 💾 Files the Game Uses

**`players.txt`** (read): one 5-letter name per line.

```text
MESSI
HENRY
PIRLO
```

**`scores.txt`** (written): one line per game, in the format `date,PLAYER,WON/LOST,attempts`.

```text
2026-11-14,HENRY,WON,2
2026-11-14,KROOS,LOST,6
```

---

## 👤 Author

Made by **Shared Lopez Serrano** — [GitHub](https://github.com/sharedlopezserrano)
Video (https://www.youtube.com/watch?v=IULgMPLCbdg&feature=youtu.be)


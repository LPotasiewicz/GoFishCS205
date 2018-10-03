package com.example.gofish;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;

public class GoFish {
    public static void main(String[] args) {
        //Create file io object for file IO
        FileIO gameplayOutput = new FileIO();
        //ask for a player name
        gameplayOutput.fileOutput("Hello! Please enter your name: ");
        Scanner scan = new Scanner(System.in);
        String playerName = scan.next();
        //add player name to output
        gameplayOutput.fileOutputDontPrint(playerName);
        Player player = new Player(playerName);

        String[] diffOptions = {
                "Easy - The computer will randomly guess for cards from your hand.",
                "Medium - The computer will make smart decisions on what to ask from your hand.",
                "Hard - The computer will make smart decisions, and sometimes lie when you ask it for a card."
        };
        String[] validResponses = {"e", "m", "h"};
        String difficulty = askUser("What difficulty of GO FISH would you like to play, " + player.getName() + "?", diffOptions, validResponses, gameplayOutput);

        int liePercentage = 0;
        if (difficulty.equals("h")) {
            String[] lieOptions = {
                    "A little (10%)",
                    "A \"fair\" amount (25%)",
                    "Way too much (50%)"
            };
            int lieResponse = Integer.parseInt(askUserNumbered("How much should the computer lie?", lieOptions, gameplayOutput));
            switch (lieResponse) {
                case 1:
                    liePercentage = 10;
                    break;
                case 2:
                    liePercentage = 25;
                    break;
                case 3:
                    liePercentage = 50;
                    break;
            }
        }

        Computer computer = new Computer(liePercentage);

        Deck deck = new Deck();
        deck.shuffle();

        // deals 7 cards to each player, one at a time
        player.populateHand(deck);
        computer.populateHand(deck);

        boolean playerTurn = true;


        gameplayOutput.fileOutput("GO FISH has started!");
        do {
            while (playerTurn) {
                // sort hand
                player.sortHand();

                ArrayList<Card> stolenCards = new ArrayList<>();
                Card userCardChoice = new Card(1, Suit.SPADES);

                // if the computers hand is full, it can not ask for a card
                if (player.getHand().size() > 0) {
                    int userChoice = Integer.parseInt(askUserNumbered(
                            player.getName() + ", What card would you like to ask for in your hand?",
                            player.handToString().split("\n"), gameplayOutput
                    ));
                    // offset the user choice for array indexing
                    userChoice--;
                    userCardChoice = player.getCard(userChoice);
                    gameplayOutput.fileOutput(player.getName() + ", You choose " + userCardChoice);
                    // check computers hand
                    stolenCards = computer.checkForMatches(userCardChoice);
                    // print result
                    gameplayOutput.fileOutput("Computer had " + String.valueOf(stolenCards.size()) + " rank " + userCardChoice.getRankString() + (stolenCards.size() == 1 ? " card." : " cards."));
                } else {
                    gameplayOutput.fileOutput(player.getName() + " has no cards!");
                }
                // no cards were found
                if (stolenCards.size() == 0) {
                    gameplayOutput.fileOutput("GO FISH!");

                    // if the deck is empty, you cant draw, computers turn
                    if (deck.getDeckSize() == 0) {
                        gameplayOutput.fileOutput("You cant draw, the deck is empty");
                        turnInfo(gameplayOutput, player, computer, deck);
                        playerTurn = false;
                        gameplayOutput.fileOutput(">>>>>>>>>>>>>>>> Computer's turn! <<<<<<<<<<<<<<<<");
                    } else {
                        Card drawnCard = player.drawCard(deck);
                        gameplayOutput.fileOutput("You drew a " + drawnCard);

                        // card drawn is the card you asked for
                        if (drawnCard.sameCardRank(userCardChoice) && deck.getDeckSize() > 0) {
                            gameplayOutput.fileOutput("Your turn again!");
                        } else {
                            turnInfo(gameplayOutput, player, computer, deck);
                            playerTurn = false;
                            gameplayOutput.fileOutput(">>>>>>>>>>>>>>>> Computer's turn! <<<<<<<<<<<<<<<<");
                        }
                    }

                }

                // cards were found
                else {
                    gameplayOutput.fileOutput("Your turn again!");
                    player.addToHand(stolenCards);
                }

                // check for sets
                player.checkForSet(gameplayOutput);
            }

            while (!playerTurn) {
                ArrayList<Card> stolenCards = new ArrayList<>();
                Card computerCardChoice = new Card(1, Suit.SPADES);

                // if the computers hand is full, it can not ask for a card
                if (computer.getHand().size() > 0) {
                    // ask for random card
                    computerCardChoice = computer.makeRandomChoice();
                    // if the difficulty is not easy, make an informed decision
                    if (difficulty.equals("e")) {

                        // TODO: make the computer smart.
                        // TODO: always choose the cards of which you have the least,
                        // TODO: don't repeat asking
                        // TODO: remember what the user asks, ask for that thing when you draw it.
                        computerCardChoice = computer.getHand().get(
                                (int) (Math.random() * computer.getHand().size())
                        );
                    }
                    gameplayOutput.fileOutput("Computer asked for cards with rank " + computerCardChoice.getRankString());

                    // check computers hand
                    stolenCards = player.checkForMatches(computerCardChoice);
                    // print result
                    gameplayOutput.fileOutput(player.getName() + " had " + String.valueOf(stolenCards.size()) + " rank " + computerCardChoice.getRankString() + (stolenCards.size() == 1 ? " card." : " cards."));
                } else {
                    gameplayOutput.fileOutput("Computer has no cards!");
                }

                // no cards were found
                if (stolenCards.size() == 0) {
                    gameplayOutput.fileOutput("GO FISH!");

                    // if the deck is empty, you cant draw, players turn
                    if (deck.getDeckSize() == 0) {
                        gameplayOutput.fileOutput("Computer cant draw, the deck is empty");
                        turnInfo(gameplayOutput, player, computer, deck);
                        playerTurn = true;
                        gameplayOutput.fileOutput(">>>>>>>>>>>>>>>> " + player.getName() + "'s turn! <<<<<<<<<<<<<<<<");
                    } else {
                        Card drawnCard = computer.drawCard(deck);

                        // card drawn is the card you asked for
                        if (drawnCard.sameCardRank(computerCardChoice) && deck.getDeckSize() > 0) {
                            gameplayOutput.fileOutput("Computer's turn again!");
                        } else {
                            turnInfo(gameplayOutput, player, computer, deck);
                            playerTurn = true;
                            gameplayOutput.fileOutput(">>>>>>>>>>>>>>>> " + player.getName() + "'s turn! <<<<<<<<<<<<<<<<");
                        }
                    }
                }

                // cards were found
                else {
                    gameplayOutput.fileOutput("Computer's turn again!");
                    computer.addToHand(stolenCards);
                }

                // check for sets
                computer.checkForSet(gameplayOutput);
            }
        } while (player.getHand().size() != 0 && computer.getHand().size() != 0);

        gameplayOutput.fileOutput("Game is Over!");
        gameplayOutput.fileOutput("Players number of sets: " + player.getNumberOfSets());
        gameplayOutput.fileOutput("Computer number of sets: " + computer.getNumberOfSets());
        recordGame(gameplayOutput);
        if (player.getNumberOfSets() > computer.getNumberOfSets()) {
            gameplayOutput.fileOutput("The winner is: " + player.getName());
        } else {
            gameplayOutput.fileOutput("The winner is the computer! Better luck next time pal!");
        }
    }

    public static void turnInfo(FileIO gameplayOutput, Player player, Computer computer, Deck deck) {
        // check for sets
        computer.checkForSet(gameplayOutput);
        player.checkForSet(gameplayOutput);

        gameplayOutput.fileOutput("Players number of sets: " + player.getNumberOfSets());
        gameplayOutput.fileOutput("Computer number of sets: " + computer.getNumberOfSets());
        gameplayOutput.fileOutput("Deck: " + deck.getDeckSize() + " cards left");
    }

    public static String askUserNumbered(String question, String[] options, FileIO file) {
        String[] numbers = new String[options.length];
        for (int i = 0; i < options.length; i++) {
            numbers[i] = String.valueOf(i + 1);
        }
        return askUser(question, options, numbers, file);
    }

    public static String askUser(String question, String[] options, String[] validResponses, FileIO file) {
        if (options.length != validResponses.length) {
            throw new IllegalArgumentException("askUser requires String[] options and String[] validResponses to be of equal length");
        }
        Scanner userIn = new Scanner(System.in);
        file.fileOutput(question);
        for (int i = 0; i < options.length; i++) {
            file.fileOutput("(" + validResponses[i] + ") " + options[i]);
        }
        String answer = userIn.nextLine();
        for (String resp : validResponses) {
            if (answer.equals(resp)) {
                return resp;
            }
        }
        file.fileOutput("Your input was invalid. Please try again:");
        return askUser(question, options, validResponses, file);
    }

    public static void recordGame(FileIO gameRecords) {
        try (FileWriter fw = new FileWriter("gameRecords.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println("------------------------------------------------------------------------------------------");
            out.println("Beginning of Game");
            out.println("------------------------------------------------------------------------------------------");
            for (Object line : gameRecords.getArrayList()) {
                out.println(line);
            }
            out.println("------------------------------------------------------------------------------------------");
            out.println("End of Game");
            out.println("------------------------------------------------------------------------------------------");
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            System.out.println("There was an error with writing out to the file, we apologize for the error.");
        }
    }

}

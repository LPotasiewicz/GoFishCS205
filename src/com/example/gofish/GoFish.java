// ***********************************************************************************************
// this file was created by Luke Potasiewicz,
// but had addition or modification at least in part by Liam O’Toole, Saraf Ray, and Jae Regan.
// ***********************************************************************************************
// For more accurate information of code ownership, visit: https://github.com/LPotasiewicz/GoFishCS205
// Where the entire history of all files in this project is held.
// ***********************************************************************************************
package com.example.gofish;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class GoFish {
    public static void main(String[] args) {
        // ***********************************************************************************************
        // user interactions created by Liam O’Toole
        // ***********************************************************************************************
        //Create file io object for file IO
        FileIO gamePlayOutput = new FileIO();
        //ask for a player name
        gamePlayOutput.fileOutput("Hello! Please enter your name: ");
        Scanner scan = new Scanner(System.in);
        String playerName = scan.next();
        //add player name to output
        gamePlayOutput.fileOutputDontPrint(playerName);
        Player player = new Player(playerName);

        // ***********************************************************************************************
        // user interactions created by Luke Potasiewicz, slight addition by Liam O’Toole, Saraf Ray, and Jae Regan.
        // ***********************************************************************************************

        String[] diffOptions = {
                "Easy - The computer will randomly guess for cards from your hand.",
                "Medium - The computer will make smart decisions on what to ask from your hand.",
                "Hard - The computer will make smart decisions, and sometimes lie when you ask it for a card."
        };
        String[] validResponses = {"e", "m", "h"};
        String difficulty = askUser("What difficulty of GO FISH would you like to play, " + player.getName() + "?", diffOptions, validResponses, gamePlayOutput);

        int liePercentage = 0;
        if (difficulty.equals("h")) {
            String[] lieOptions = {
                    "A little (10%)",
                    "A \"fair\" amount (25%)",
                    "Way too much (50%)"
            };
            int lieResponse = Integer.parseInt(askUserNumbered("How much should the computer lie?", lieOptions, gamePlayOutput));
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

        // ***********************************************************************************************
        // Main game loop created by Luke Potasiewicz,
        // slightly modifyed (for file io) by Liam O’Toole,
        // and bug fixes by Saraf Ray and Liam O’Toole
        // ***********************************************************************************************

        gamePlayOutput.fileOutput("GO FISH has started!");
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
                            player.handToString().split("\n"), gamePlayOutput
                    ));
                    // offset the user choice for array indexing
                    userChoice--;
                    userCardChoice = player.getCard(userChoice);
                    gamePlayOutput.fileOutput(player.getName() + ", You choose " + userCardChoice);
                    // check computers hand
                    stolenCards = computer.checkForMatches(userCardChoice);
                    // print result
                    gamePlayOutput.fileOutput("Computer had " + String.valueOf(stolenCards.size()) + " rank " + userCardChoice.getRankString() + (stolenCards.size() == 1 ? " card." : " cards."));
                } else {
                    gamePlayOutput.fileOutput(player.getName() + " has no cards!");
                }
                // no cards were found
                if (stolenCards.size() == 0) {
                    gamePlayOutput.fileOutput("GO FISH!");

                    // if the deck is empty, you cant draw, computers turn
                    if (deck.getDeckSize() == 0) {
                        gamePlayOutput.fileOutput("You cant draw, the deck is empty");
                        turnInfo(gamePlayOutput, player, computer, deck);
                        playerTurn = false;
                        gamePlayOutput.fileOutput(">>>>>>>>>>>>>>>> Computer's turn! <<<<<<<<<<<<<<<<");
                    } else {
                        Card drawnCard = player.drawCard(deck);
                        gamePlayOutput.fileOutput("You drew a " + drawnCard);

                        // card drawn is the card you asked for
                        if (drawnCard.sameCardRank(userCardChoice) && deck.getDeckSize() > 0) {
                            gamePlayOutput.fileOutput("Your turn again!");
                        } else {
                            turnInfo(gamePlayOutput, player, computer, deck);
                            playerTurn = false;
                            gamePlayOutput.fileOutput(">>>>>>>>>>>>>>>> Computer's turn! <<<<<<<<<<<<<<<<");
                        }
                    }

                }

                // cards were found
                else {
                    gamePlayOutput.fileOutput("Your turn again!");
                    player.addToHand(stolenCards);
                }

                // check for sets
                player.checkForSet(gamePlayOutput);
            }

            while (!playerTurn) {
                ArrayList<Card> stolenCards = new ArrayList<>();
                Card computerCardChoice = new Card(1, Suit.SPADES);
                gamePlayOutput.fileOutputDontPrint("Computer's hand: " + computer.getHand());

                // if the computers hand is full, it can not ask for a card
                if (computer.getHand().size() > 0) {
                    // ask for random card
                    computerCardChoice = computer.makeRandomChoice();
                    // if the difficulty is not easy, make an informed decision
                    if (!difficulty.equals("e")) {
                        computerCardChoice = computer.makeInformedChoice();
                    }
                    gamePlayOutput.fileOutput("Computer asked for cards with rank " + computerCardChoice.getRankString());

                    // check computers hand
                    stolenCards = player.checkForMatches(computerCardChoice);
                    // print result
                    gamePlayOutput.fileOutput(player.getName() + " had " + String.valueOf(stolenCards.size()) + " rank " + computerCardChoice.getRankString() + (stolenCards.size() == 1 ? " card." : " cards."));
                } else {
                    gamePlayOutput.fileOutput("Computer has no cards!");
                }

                // no cards were found
                if (stolenCards.size() == 0) {
                    gamePlayOutput.fileOutput("GO FISH!");

                    // if the deck is empty, you cant draw, players turn
                    if (deck.getDeckSize() == 0) {
                        gamePlayOutput.fileOutput("Computer cant draw, the deck is empty");
                        turnInfo(gamePlayOutput, player, computer, deck);
                        playerTurn = true;
                        gamePlayOutput.fileOutput(">>>>>>>>>>>>>>>> " + player.getName() + "'s turn! <<<<<<<<<<<<<<<<");
                    } else {
                        Card drawnCard = computer.drawCard(deck);

                        // card drawn is the card you asked for
                        if (drawnCard.sameCardRank(computerCardChoice) && deck.getDeckSize() > 0) {
                            gamePlayOutput.fileOutput("Computer's turn again!");
                        } else {
                            turnInfo(gamePlayOutput, player, computer, deck);
                            playerTurn = true;
                            gamePlayOutput.fileOutput(">>>>>>>>>>>>>>>> " + player.getName() + "'s turn! <<<<<<<<<<<<<<<<");
                        }
                    }
                }

                // cards were found
                else {
                    gamePlayOutput.fileOutput("Computer's turn again!");
                    computer.addToHand(stolenCards);
                }

                // check for sets
                computer.checkForSet(gamePlayOutput);
            }
        } while (player.getHand().size() != 0 && computer.getHand().size() != 0);

        // ***********************************************************************************************
        // End game dialog created by Liam O’Toole, lightly modified by: Luke Potasiewicz, Saraf Ray, Jay Regan.
        // ***********************************************************************************************

        gamePlayOutput.fileOutput("Game is Over!");
        gamePlayOutput.fileOutput("Your number of sets: " + player.getNumberOfSets());
        gamePlayOutput.fileOutput("Computer's number of sets: " + computer.getNumberOfSets());
        recordGame(gamePlayOutput);
        if (player.getNumberOfSets() > computer.getNumberOfSets()) {
            gamePlayOutput.fileOutput("The winner is: " + player.getName());
        } else {
            gamePlayOutput.fileOutput("The winner is the computer! Better luck next time pal!");
        }
    }

    // ***********************************************************************************************
    // Created by Luke Potasiewicz, lightly modified by: Liam O’Toole
    // ***********************************************************************************************
    /**
     * turnInfo prints to the file and then console information about the current game state
     * @param gamePlayOutput - used to print to console and gameplay output file
     * @param player - needed for set information
     * @param computer - needed for set information
     * @param deck - needed for numeber of cards
     */
    public static void turnInfo(FileIO gamePlayOutput, Player player, Computer computer, Deck deck) {
        // check for sets
        computer.checkForSet(gamePlayOutput);
        player.checkForSet(gamePlayOutput);

        gamePlayOutput.fileOutput("Your number of sets: " + player.getNumberOfSets());
        gamePlayOutput.fileOutput("Computer's number of sets: " + computer.getNumberOfSets());
        gamePlayOutput.fileOutput("Deck: " + deck.getDeckSize() + " cards left");
    }

    // ***********************************************************************************************
    // Created by Luke Potasiewicz
    // ***********************************************************************************************
    /**
     * askUserNumbered is a wrapper function for askUser, that passes a list of numbers for options
     * @param question - question to ask user
     * @param options - options for user to choose from
     * @param file - needed for printing to file and console
     * @return - askUser
     */
    public static String askUserNumbered(String question, String[] options, FileIO file) {
        String[] numbers = new String[options.length];
        for (int i = 0; i < options.length; i++) {
            numbers[i] = String.valueOf(i + 1);
        }
        return askUser(question, options, numbers, file);
    }

    // ***********************************************************************************************
    // Created by Luke Potasiewicz, lightly modified by: Liam O’Toole
    // ***********************************************************************************************
    /**
     * @param question - question to ask user
     * @param options - options for user to choose from
     * @param validResponses - options user can type to respond
     * @param file - needed for printing to file and console
     * @return - an entry from validResponses
     */
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
    // ***********************************************************************************************
    // Created by Liam O’Toole, lightly modified by: Luke Potasiewicz
    // ***********************************************************************************************
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

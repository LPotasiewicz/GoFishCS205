package com.example.gofish;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class GoFish {
    public static void main(String[] args) {
        //Create file io object for file IO
        FileIO gameplayOutput = new FileIO();
        //ask for a player name
        System.out.println("Hello! Please enter your name: ");
        gameplayOutput.getArrayList().add("Hello! Please enter your name: ");
        Scanner scan = new Scanner(System.in);
        String playerName = scan.next();
        //add player name to output
        gameplayOutput.getArrayList().add(playerName);
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
            switch(lieResponse) {
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


        System.out.println("GO FISH has started!");
        gameplayOutput.getArrayList().add("GO FISH has started!");
        do {
            while (playerTurn) {
                // sort hand
                player.sortHand();
                int userChoice = Integer.parseInt(askUserNumbered(
                        player.getName() + ", What card would you like to ask for in your hand?",
                        player.handToString().split("\n"), gameplayOutput
                ));
                // offset the user choice for array indexing
                userChoice--;
                Card userCardChoice = player.getCard(userChoice);
                System.out.println(player.getName() + ", You choose " + userCardChoice);
                gameplayOutput.getArrayList().add(player.getName() + ", You choose " + userCardChoice);
                // check computers hand
                ArrayList<Card> stolenCards = computer.checkForMatches(userCardChoice);
                // print result
                System.out.println("Computer had " + String.valueOf(stolenCards.size()) + " rank " + userCardChoice.getRankString() + (stolenCards.size() == 1 ? " card." : " cards."));
                gameplayOutput.getArrayList().add("Computer had " + String.valueOf(stolenCards.size()) + " rank " + userCardChoice.getRankString() + (stolenCards.size() == 1 ? " card." : " cards."));

                // no cards were found
                if (stolenCards.size() == 0 || player.getHandSize() < 1) {
                    System.out.println("GO FISH!");
                    gameplayOutput.getArrayList().add("GO FISH!");
                    Card drawnCard = player.drawCard(deck);
                    System.out.println("You drew a " + drawnCard);
                    gameplayOutput.getArrayList().add("You drew a " + drawnCard);

                    // card drawn is the card you asked for
                    if (drawnCard.sameCardRank(userCardChoice)) {
                        System.out.println("Your turn again!");
                        gameplayOutput.getArrayList().add("Your turn again!");
                    } else {
                        playerTurn = false;
                        System.out.println(">> Computer's turn! <<");
                        gameplayOutput.getArrayList().add(">> Computer's turn! <<");
                    }
                }

                // cards were found
                else {
                    System.out.println("Your turn again!");
                    gameplayOutput.getArrayList().add("Your turn again!");
                    player.addToHand(stolenCards);
                }

                // check for sets
                player.checkForSet(gameplayOutput);
            }

            while (!playerTurn) {

                // ask for random card
                Card computerCardChoice = computer.getHand().get(
                        (int)(Math.random()*computer.getHand().size())
                );
                // if the difficulty is not easy, make an informed decision
                if (difficulty.equals("e")) {

                    // TODO: make the computer smart.
                    // TODO: always choose the cards of which you have the least,
                    // TODO: don't repeat asking
                    // TODO: remember what the user asks, ask for that thing when you draw it.
                    computerCardChoice = computer.getHand().get(
                            (int)(Math.random()*computer.getHand().size())
                    );
                }
                System.out.println("Computer asked for cards with rank " + computerCardChoice.getRankString());
                gameplayOutput.getArrayList().add("Computer asked for cards with rank " + computerCardChoice.getRankString());

                // check computers hand
                ArrayList<Card> stolenCards = player.checkForMatches(computerCardChoice);
                // print result
                System.out.println(player.getName() + " had " + String.valueOf(stolenCards.size()) + " rank " + computerCardChoice.getRankString() + (stolenCards.size() == 1 ? " card." : " cards."));
                gameplayOutput.getArrayList().add(player.getName() + " had " + String.valueOf(stolenCards.size()) + " rank " + computerCardChoice.getRankString() + (stolenCards.size() == 1 ? " card." : " cards."));

                // no cards were found
                if (stolenCards.size() == 0 || computer.getHandSize() < 1) {
                    System.out.println("GO FISH!");
                    gameplayOutput.getArrayList().add("GO FISH!");
                    Card drawnCard = computer.drawCard(deck);
                    // TODO: this is for testing
                    // System.out.println("Computer drew a " + drawnCard);

                    // card drawn is the card you asked for
                    if (drawnCard.sameCardRank(computerCardChoice)) {
                        System.out.println("Computer's turn again!");
                        gameplayOutput.getArrayList().add("Computer's turn again!");
                    } else {
                        playerTurn = true;
                        System.out.println(">> " + player.getName() + "'s turn! <<");
                        gameplayOutput.getArrayList().add(">> " + player.getName() + "'s turn! <<");
                    }
                }

                // cards were found
                else {
                    System.out.println("Computer's turn again!");
                    gameplayOutput.getArrayList().add("Computer's turn again!");
                    computer.addToHand(stolenCards);
                }

                // check for sets
                computer.checkForSet(gameplayOutput);
            }
        } while (player.getHand().size() != 0 && computer.getHand().size() != 0);

        System.out.println("Game is Over!");
        gameplayOutput.getArrayList().add("Game is Over!");
        System.out.println("Players number of sets: " + player.getNumberOfSets());
        gameplayOutput.getArrayList().add("Players number of sets: " + player.getNumberOfSets());
        System.out.println("Computer number of sets: " + computer.getNumberOfSets());
        gameplayOutput.getArrayList().add("Computer number of sets: " + computer.getNumberOfSets());
        recordGame(gameplayOutput);
        if(player.getNumberOfSets() > computer.getNumberOfSets()) {
            System.out.println("The winner is: " + player.getName());
            gameplayOutput.getArrayList().add("The winner is: " + player.getName());
        } else {
            System.out.println("The winner is the computer! Better luck next time lad!");
            gameplayOutput.getArrayList().add("The winner is the computer! Better luck next time lad!");
        }
//        System.out.println(player.handToString());
//        computer.handToString();
//        System.out.println("");

    }

    public static String askUserNumbered(String question, String[] options, FileIO file) {
        String[] numbers = new String[options.length];
        for (int i = 0; i < options.length; i++) {
            numbers[i] = String.valueOf(i+1);
        }
        return askUser(question, options, numbers, file);
    }

    public static String askUser(String question, String[] options, String[] validResponses, FileIO file) {
        if (options.length != validResponses.length) {
            throw new IllegalArgumentException("askUser requires String[] options and String[] validResponses to be of equal length");
        }
        Scanner userIn = new Scanner(System.in);
        System.out.println(question);
        file.getArrayList().add(question);
        for (int i = 0; i < options.length; i++) {
            System.out.println("(" + validResponses[i] + ") " + options[i]);
            file.getArrayList().add("(" + validResponses[i] + ") " + options[i]);
        }
        String answer = userIn.nextLine();
        for (String resp : validResponses) {
            if (answer.equals(resp)){
                return resp;
            }
        }
        System.out.println("Your input was invalid. Please try again:");
        file.getArrayList().add("Your input was invalid. Please try again:");
        return askUser(question, options, validResponses, file);
    }

    public static void recordGame(FileIO gameRecords) {
        try(FileWriter fw = new FileWriter("gameRecords.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("------------------------------------------------------------------------------------------");
            out.println("Beginning of Game");
            out.println("------------------------------------------------------------------------------------------");
            for(Object line : gameRecords.getArrayList()) {
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

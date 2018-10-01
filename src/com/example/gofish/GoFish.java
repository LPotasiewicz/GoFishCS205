package com.example.gofish;
import java.util.Scanner;
import java.util.ArrayList;

public class GoFish {
    public static void main(String[] args) {

        Player player = new Player("Player 1");
        Computer computer = new Computer();

        Deck deck = new Deck();
        deck.shuffle();

        // deals 7 cards to each player, one at a time
        player.populateHand(deck);
        computer.populateHand(deck);

        boolean playerTurn = true;

        do {
            while (playerTurn) {
                int userChoice = Integer.parseInt(askUserNumbered(
                        player.getName() + ", What card would you like to ask for in your hand?",
                        player.handToString().split("\n")
                ));
                Card userCardChoice = player.getCard(userChoice);
                System.out.println(player.getName() + ", You choose " + userCardChoice);
                // check computers hand
                ArrayList<Card> stolenCards = computer.checkForMatches(userCardChoice);
                // print result
                System.out.println("Computer had " + String.valueOf(stolenCards.size()) + " rank " + userCardChoice.getRankString() + (stolenCards.size() == 1 ? " card." : " cards."));

                // no cards were found
                if (stolenCards.size() == 0) {
                    System.out.println("GO FISH!");
                    Card drawnCard = player.drawCard(deck);
                    System.out.println("You drew a " + drawnCard);

                    // card drawn is the card you asked for
                    if (drawnCard.sameCardRank(userCardChoice)) {
                        System.out.println("Your turn again!");
                    } else {
                        playerTurn = false;
                    }
                }

                // cards were found
                else {
                    System.out.println("Your turn again!");
                    player.addToHand(stolenCards);
                }

                // check for sets
                player.checkForSet();
            }

            while (!playerTurn) {
                Card drawnCard = computer.drawCard(deck);
                System.out.println("Computer drew a " + drawnCard);

                // check for sets
                computer.checkForSet();
                playerTurn = true;
            }
        } while (true);


//        System.out.println(player.handToString());
//        computer.handToString();
//        System.out.println("");

    }

    public static String askUserNumbered(String question, String[] options) {
        String[] numbers = new String[options.length];
        for (int i = 0; i < options.length; i++) {
            numbers[i] = String.valueOf(i);
        }
        return askUser(question, options, numbers);
    }

    public static String askUser(String question, String[] options, String[] validResponses) {
        if (options.length != validResponses.length) {
            throw new IllegalArgumentException("askUser requires String[] options and String[] validResponses to be of equal length");
        }
        Scanner userIn = new Scanner(System.in);
        System.out.println(question);
        for (int i = 0; i < options.length; i++) {
            System.out.println("(" + validResponses[i] + ") " + options[i]);
        }
        String answer = userIn.nextLine();
        for (String resp : validResponses) {
            if (answer.equals(resp)){
                return resp;
            }
        }
        System.out.println("Your input was invalid. Please try again:");
        return askUser(question, options, validResponses);
    }
}

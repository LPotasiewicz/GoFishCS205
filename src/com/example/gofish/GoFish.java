package com.example.gofish;
import java.util.Scanner;
import java.util.Arrays;

public class GoFish {
    public static void main(String[] args) {

        Player player = new Player("Player 1");
        Computer computer = new Computer();

        Deck deck = new Deck();
        deck.shuffle();

        // deals 7 cards to each player, one at a time
        player.populateHand(deck);
        computer.populateHand(deck);

        do {
            int userChoice = Integer.parseInt(askUserNumbered(
                player.getName() + ", What card would you like to ask for in your hand?",
                player.handToString().split("\n")
            ));
            Card userCardChoice = player.getCard(userChoice);
            System.out.println(player.getName() + ", You choose " + userCardChoice);
            // check computers hand
            // print result
            // move cards

            // repeat for computer
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
        askUser(question, options, validResponses);
        return "An error has occurred.";
    }
}

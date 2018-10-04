// ***********************************************************************************************
// Created by Luke Potasiewicz, lightly modified by: Saraf Ray
// ***********************************************************************************************
package com.example.gofish;

public class Card {
    private int rank;
    private Suit cardSuit;


    public Card(int inputRank, Suit inputCardSuit){
        rank = inputRank;
        cardSuit = inputCardSuit;
    }

    public boolean sameCardRank(Card compareCard){
        return rank == compareCard.getRank();
    }

    public String toString() {
        String printRank;
        String printSuit;
        // this switch statement decides what we print for king, queen, jack, and ace
        switch (rank) {
            case 11:
                printRank = " J";
                break;
            case 12:
                printRank = " Q";
                break;
            case 13:
                printRank = " K";
                break;
            case 1:
                printRank = " A";
                break;
            case 10:
                printRank = String.valueOf(rank);
                break;
            default:
                printRank = " " + String.valueOf(rank);
                break;
        }
        // this switch statement decides the values of our suits
        switch (cardSuit) {
            case CLUBS:
                printSuit = "♣";
                break;
            case SPADES:
                printSuit = "♠";
                break;
            case DIAMONDS:
                printSuit = "♢";
                break;
            case HEARTS:
                printSuit = "♡";
                break;
            default:
                // we should NEVER reach this
                throw new IllegalArgumentException("Card must have a suit");
        }

        return printRank + printSuit;
    }

    public int getRank() {
        return rank;
    }

    public String getRankString() {
        String printRank;
        switch (rank) {
            case 11:
                printRank = "J";
                break;
            case 12:
                printRank = "Q";
                break;
            case 13:
                printRank = "K";
                break;
            case 1:
                printRank = "A";
                break;
            default:
                printRank = String.valueOf(rank);
                break;
        }
        return printRank;
    }
}

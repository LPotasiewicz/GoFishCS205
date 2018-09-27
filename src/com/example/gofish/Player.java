package com.example.gofish;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand = new ArrayList<Card>();
    private ArrayList<Card> sets = new ArrayList<Card>();
    private String Name;

    public Player(String name) {
        Name = name;
    }

    public void addToHand(Card card) {
        hand.add(card);
    }

    public void handToString() {
        for(Card card : hand) {
            System.out.println(card.toString());
        }
    }

    private void checkForSet() {
//        int[]
        for(Card card : hand) {
            int currentRank = card.getRank();

        }
    }

    private void addCardToSet(Card card) {
        sets.add(card);
    }

    private void removeCardFromHand(Card card) {
        hand.remove(card);
    }
}

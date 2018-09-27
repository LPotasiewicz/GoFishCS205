package com.example.gofish;

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
    //this method should be called each turn
    public void checkHandSize() {
        if(hand.size() < 7) {
            while(hand.size() < 7) {
                //this will be changed to drawing from the deck
                Card newCard = new Card(13, Suit.HEARTS);
                hand.add(newCard);
            }
        }
    }
    //this method should be called each turn
    private void checkForSet() {
        ArrayList<Card> set = new ArrayList<Card>();
        for(Card card : hand) {
            if(card.sameCardRank(card) == true) {
                set.add(card);
            }
            if(set.size() == 4) {
                System.out.println("A set has been found of the rank: " + set.get(1).getRank());
                for(Card cardInSet : set) {
                    addCardToSet(cardInSet);
                    removeCardFromHand(cardInSet);
                }
            }
        }
    }

    public int getNumberOfSets() {
        return sets.size() / 4;
    }

    private void addCardToSet(Card card) {
        sets.add(card);
    }

    private void removeCardFromHand(Card card) {
        hand.remove(card);
    }
}

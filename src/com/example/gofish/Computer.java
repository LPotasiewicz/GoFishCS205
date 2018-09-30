package com.example.gofish;

import java.util.ArrayList;

public class Computer {
    private ArrayList<Card> hand = new ArrayList<Card>();
    private ArrayList<Card> sets = new ArrayList<Card>();
    private String Name;

    public Computer() {
        Name = "opponent";
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
    public void checkForSet() {
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

    public ArrayList checkForMatches(Card card) {
        ArrayList<Card> matchedCards = new ArrayList<Card>();
        int removedCardCount = 0;
        for(Card handCard : hand) {
            if(card.getRank() == handCard.getRank()) {
                matchedCards.add(handCard);
                removeCardFromHand(handCard);
                removedCardCount++;
            }
        }
        //add back in cards
        for(int i = 0; i < removedCardCount; i++) {
            // this will change to drawing a card from the deck
            Card newCard = new Card(13, Suit.HEARTS);
            hand.add(newCard);
        }
        return matchedCards;
    }

    public void drawCard(Deck deck) {
        Card cardToDraw = deck.getCardsInDeck().get(0);
        deck.getCardsInDeck().remove(0);
        addToHand(cardToDraw);
    }

    public void populateHand(Deck deck) {
        int count = 0;
        while (count <= 6) {
            drawCard(deck);
            count += 1;
        }
    }

    public int getNumberOfSets() {
        return sets.size() / 4;
    }

    private void addToHand(Card card) {
        hand.add(card);
    }

    private void addCardToSet(Card card) {
        sets.add(card);
    }

    private void removeCardFromHand(Card card) {
        hand.remove(card);
    }


}



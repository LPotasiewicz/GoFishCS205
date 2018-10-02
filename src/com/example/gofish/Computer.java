package com.example.gofish;

import java.util.ArrayList;

public class Computer {
    private ArrayList<Card> hand = new ArrayList<>();
    private ArrayList<Card> sets = new ArrayList<>();
    private String Name;
    private int percentLies;

    public Computer(int pctLies) {
        Name = "opponent";
        percentLies = pctLies;
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
    public void checkForSet(FileIO file) {
        ArrayList<Card> set = new ArrayList<>();
        for(Card baseCard : hand) {
            set = new ArrayList<>();
            for(Card compareCard : hand) {
                if (baseCard.sameCardRank(compareCard)) {
                    set.add(compareCard);
                }
            }
        }
        if (set.size() == 4) {
            file.getArrayList().add("------------------------ Computer has a set of rank: " + set.get(1).getRankString());
            System.out.println("------------------------ Computer has a set of rank: " + set.get(1).getRankString());
            for (Card cardInSet : set) {
                addCardToSet(cardInSet);
                removeCardFromHand(cardInSet);
            }
        }
    }

    public ArrayList<Card> checkForMatches(Card card) {
        ArrayList<Card> matchedCards = new ArrayList<>();
        if (Math.random() * 100 > percentLies) {
            for(int i = 0; i < hand.size(); i++) { // this cant be a for each loop because of a ConcurrentModificationException
                if(card.getRank() == hand.get(i).getRank()) {
                    matchedCards.add(hand.get(i));
                    removeCardFromHand(hand.get(i));
                }
            }
            // add back in cards
            // possibly add a card to the hand if no cards are left?
        }
        return matchedCards;
    }

    public Card drawCard(Deck deck) {
        Card cardToDraw = deck.getCardsInDeck().get(0);
        deck.getCardsInDeck().remove(0);
        addToHand(cardToDraw);
        return cardToDraw;
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

    public void addToHand(ArrayList<Card> cards) {
        hand.addAll(cards);
    }

    private void addCardToSet(Card card) {
        sets.add(card);
    }

    private void removeCardFromHand(Card card) {
        hand.remove(card);
    }
    public int getHandSize() {
        return hand.size();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}



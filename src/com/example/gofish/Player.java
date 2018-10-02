package com.example.gofish;
import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand = new ArrayList<Card>();
    private ArrayList<Card> sets = new ArrayList<Card>();
    private String name;

    public Player(String n) {
        name = n;
    }

    public String handToString() {
        String returnString = "";
        for(Card card : hand) {
            returnString += (card + "\n");
        }
        return returnString;
    }
    //this method should be called each turn
    public void checkForSet() {
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
            System.out.println("------------------------ " + name + " has a set of rank: " + set.get(1).getRankString());
            for (Card cardInSet : set) {
                addCardToSet(cardInSet);
                removeCardFromHand(cardInSet);
            }
        }
    }

    public void populateHand(Deck deck) {
        int count = 0;
        while (count <= 6) {
            drawCard(deck);
            count += 1;
        }
    }


    public ArrayList<Card> checkForMatches(Card card) {
        ArrayList<Card> matchedCards = new ArrayList<>();
        for(int i = 0; i < hand.size(); i++) { // this cant be a for each loop because of a ConcurrentModificationException
            if(card.getRank() == hand.get(i).getRank()) {
                matchedCards.add(hand.get(i));
                removeCardFromHand(hand.get(i));
            }
        }
        // add back in cards
        // possibly add a card to the hand if no cards are left?
        return matchedCards;
    }

    public int getNumberOfSets() {
        return sets.size() / 4;
    }

    public void addToHand(Card card) {
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

    public String getName(){
        return name;
    }

    public Card getCard(int i){
        return hand.get(i);
    }

    public Card drawCard(Deck deck) {
        Card cardToDraw = deck.getCardsInDeck().get(0);
        deck.getCardsInDeck().remove(0);
        addToHand(cardToDraw);
        return cardToDraw;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}

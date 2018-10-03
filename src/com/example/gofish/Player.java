package com.example.gofish;
import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand = new ArrayList<Card>();
    private ArrayList<Card> sets = new ArrayList<Card>();
    private String name;

    public Player(String n) {
        name = n;
    }

    // this method assists in printing the users hand
    public String handToString() {
        String returnString = "";
        for(Card card : hand) {
            returnString += (card + "\n");
        }
        return returnString;
    }
    // this method should be called each turn
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
            file.fileOutput("------------------------ " + name + " has a set of rank: " + set.get(1).getRankString());
            for (Card cardInSet : set) {
                addCardToSet(cardInSet);
                removeCardFromHand(cardInSet);
            }
        }
    }

    // given a card, checks for matches of that cards rank in the hand, and then removes and returns those cards
    public ArrayList<Card> checkForMatches(Card card) {
        ArrayList<Card> cardsToRemove = new ArrayList<>();
        for(int i = 0; i < hand.size(); i++) { // this cant be a for each loop because of a ConcurrentModificationException
            if (card.getRank() == hand.get(i).getRank()) {
                cardsToRemove.add(hand.get(i));
            }
        }
        hand.removeAll(cardsToRemove);
        return cardsToRemove;
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

    // modifies the hand to be sorted, this is called before the hand is printed
    public void sortHand() {
        ArrayList<Card> newHand = new ArrayList<>();
        while (hand.size() > 0) {
            Card min = hand.get(0);
            for (int i = 0; i < hand.size(); i ++) {
               if (min.getRank() > hand.get(i).getRank()) {
                   min = hand.get(i);
               }
            }
            hand.remove(min);
            newHand.add(min);
        }
        hand = newHand;
    }

    private void addCardToSet(Card card) {
        sets.add(card);
    }

    private void removeCardFromHand(Card card) {
        hand.remove(card);
    }

    // takes a card from the deck, and adds it to your hand
    public Card drawCard(Deck deck) {
        Card cardToDraw = deck.getCardsInDeck().get(0);
        deck.getCardsInDeck().remove(0);
        addToHand(cardToDraw);
        return cardToDraw;
    }

    // fills the hand with 7 cards
    public void populateHand(Deck deck) {
        int count = 0;
        while (count <= 6) {
            drawCard(deck);
            count += 1;
        }
    }

    public String getName(){
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Card getCard(int i){
        return hand.get(i);
    }
}

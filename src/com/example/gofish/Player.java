package com.example.gofish;
import java.util.ArrayList;

public class Player {
    //tempCard used for draw() method
    Card tempCard = new Card(8,Suit.CLUBS);

    private ArrayList<Card> hand = new ArrayList<Card>();
    private ArrayList<Card> sets = new ArrayList<Card>();
    private String Name;

    public Player(String name) {
        Name = name;
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
                //this is where you would draw
                //hand.add(player.draw());
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

    public ArrayList checkForMatches(Card card, Player player1) {
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

            /* NEEDS TO TAKE IN A DECK */
            //hand.add(player1.draw());
        }
        return matchedCards;
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

    public Card draw(ArrayList<Card> deck1) {
        tempCard = (deck1.get(0));
        deck1.remove(deck1.get(0));
        return tempCard;


    }

}

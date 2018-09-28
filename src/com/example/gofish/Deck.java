package com.example.gofish;
import java.util.ArrayList;
import java.util.*;

public class Deck {
    private ArrayList<Card> cardsInDeck = new ArrayList<>();
    private Card tempCard = new Card(8,Suit.CLUBS);

    //constructor for deck
    public Deck() {
        for (int i = 1; i < 14; i++) {
            Card card = new Card(i, Suit.HEARTS);
            cardsInDeck.add(card);
            //System.out.println(card.toString());
        }
        for (int i = 1; i < 14; i++) {
            Card card = new Card(i, Suit.DIAMONDS);
            cardsInDeck.add(card);
            //System.out.println(card.toString());
        }
        for (int i = 1; i < 14; i++) {
            Card card = new Card(i, Suit.SPADES);
            cardsInDeck.add(card);
            //System.out.println(card.toString());
        }
        for (int i = 1; i < 14; i++) {
            Card card = new Card(i, Suit.CLUBS);
            cardsInDeck.add(card);
            //System.out.println(card.toString());
        }
    }

    public ArrayList<Card> getCardsinDeck() {
        return cardsInDeck;
    }

    public void shuffle() {
        System.out.println("The Deck has been shuffled!");
        Collections.shuffle(cardsInDeck);
    }


}


// ***********************************************************************************************
// Created by Jae Regan
// ***********************************************************************************************
package com.example.gofish;
import java.util.ArrayList;
import java.util.*;

public class Deck {
    private ArrayList<Card> cardsInDeck = new ArrayList<>();
    private int cardsInSuit = 14;

    //constructor for deck
    public Deck() {
        for (int i = 1; i < cardsInSuit; i++) {
            Card card = new Card(i, Suit.HEARTS);
            cardsInDeck.add(card);
            //System.out.println(card.toString());
        }
        for (int i = 1; i < cardsInSuit; i++) {
            Card card = new Card(i, Suit.DIAMONDS);
            cardsInDeck.add(card);
            //System.out.println(card.toString());
        }
        for (int i = 1; i < cardsInSuit; i++) {
            Card card = new Card(i, Suit.SPADES);
            cardsInDeck.add(card);
            //System.out.println(card.toString());
        }
        for (int i = 1; i < cardsInSuit; i++) {
            Card card = new Card(i, Suit.CLUBS);
            cardsInDeck.add(card);
            //System.out.println(card.toString());
        }
    }

    public ArrayList<Card> getCardsInDeck() {
        return cardsInDeck;
    }

    public void shuffle() {
        System.out.println("The Deck has been shuffled!");
        Collections.shuffle(cardsInDeck);
    }

    public int getDeckSize() {
        return cardsInDeck.size();
    }


}


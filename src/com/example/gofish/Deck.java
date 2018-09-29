package com.example.gofish;
import java.util.ArrayList;
import java.util.*;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();


    //constructor for deck
    public Deck() {
        for (int i = 1; i < 14; i++) {
            Card card = new Card(i, Suit.HEARTS);
            deck.add(card);
            //System.out.println(card.toString());
        }
        for (int i = 1; i < 14; i++) {
            Card card = new Card(i, Suit.DIMONDS);
            deck.add(card);
            //System.out.println(card.toString());
        }
        for (int i = 1; i < 14; i++) {
            Card card = new Card(i, Suit.SPADES);
            deck.add(card);
            //System.out.println(card.toString());
        }
        for (int i = 1; i < 14; i++) {
            Card card = new Card(i, Suit.CLUBS);
            deck.add(card);
            //System.out.println(card.toString());
        }
    }

    public void shuffle() {
        System.out.println("The Deck has been shuffled!");
        Collections.shuffle(deck);
    }


}


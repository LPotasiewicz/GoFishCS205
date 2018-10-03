package com.example.gofish;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Computer {
    private ArrayList<Card> hand = new ArrayList<>();
    private ArrayList<Card> sets = new ArrayList<>();
    private ArrayList<Integer> playerGuesses = new ArrayList<>();
    private ArrayList<Card> computerGuesses = new ArrayList<>();
    private int percentLies;

    public Computer(int pctLies) {
        percentLies = pctLies;
    }

    public Card makeRandomChoice() {
        return hand.get(
                (int) (Math.random() * hand.size())
        );
    }

    public Card makeInformedChoice() {
        // if the player has guessed a card in the past, and you now have this card, ask for it
        for (Card handCard : hand) {
            for (int guessCardRank : playerGuesses) {
                if (handCard.getRank() == guessCardRank) {
                    playerGuesses.removeAll(Arrays.asList(guessCardRank));
                    computerGuesses.add(handCard);
                    return handCard;
                }
            }
        }

        // don't ask for the same cards without asking for most of the other cards first
        ArrayList<Card> handWithoutPreviousGuesses = new ArrayList<>();
        for (Card handCard : hand) {
            boolean cardRankInComputerGuesses = false;
            for (Card compGuessCard : computerGuesses) {
                if (compGuessCard.sameCardRank(handCard)) {
                    cardRankInComputerGuesses = true;
                }
            }
            if (!cardRankInComputerGuesses) {
                handWithoutPreviousGuesses.add(handCard);
            }
        }

        // if we have gone through all the cards in our hand, start the guessing over again
        if (handWithoutPreviousGuesses.size() == 0) {
            handWithoutPreviousGuesses = hand;
            computerGuesses = new ArrayList<>();
        }
        Card leastCommonCard = handWithoutPreviousGuesses.get(0);

        int leastCommonCardInstances = 999;

        // ask for the card of which you have the least of
        for (Card handCard : handWithoutPreviousGuesses) {
            int numberOfCardInstances = 0;
            for (Card handCard2 : handWithoutPreviousGuesses) {
                if (handCard.sameCardRank(handCard2)) {
                    numberOfCardInstances++;
                }
            }
            if (leastCommonCardInstances > numberOfCardInstances) {
                leastCommonCard = handCard;
                leastCommonCardInstances = numberOfCardInstances;
            }
        }
        computerGuesses.add(leastCommonCard);
        return leastCommonCard;
    }

    // this method should be called each turn
    public void checkForSet(FileIO file) {
        ArrayList<Card> set = new ArrayList<>();
        for (Card baseCard : hand) {
            set = new ArrayList<>();
            for (Card compareCard : hand) {
                if (baseCard.sameCardRank(compareCard)) {
                    set.add(compareCard);
                }
            }
        }
        if (set.size() == 4) {
            file.fileOutput("------------------------ Computer has a set of rank: " + set.get(1).getRankString());
            for (Card cardInSet : set) {
                addCardToSet(cardInSet);
                removeCardFromHand(cardInSet);
            }
        }
    }

    public ArrayList<Card> checkForMatches(Card card) {
        if (!playerGuesses.contains(card.getRank())) {
            playerGuesses.add(card.getRank());
        }
        ArrayList<Card> matchedCards = new ArrayList<>();
        if (Math.random() * 100 > percentLies) {
            for (int i = 0; i < hand.size(); i++) { // this cant be a for each loop because of a ConcurrentModificationException
                if (card.getRank() == hand.get(i).getRank()) {
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

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getNumberOfSets() {
        return sets.size() / 4;
    }
}



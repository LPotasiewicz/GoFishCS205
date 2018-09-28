package com.example.gofish;

public class GoFish {
    public static void main(String[] args) {
        Player player = new Player();
        Computer computer = new Computer();
        Deck deck = new Deck();
        deck.shuffle();
        int count = 0;
        //deals 7 cards to each player, one at a time
        //can put this into function if need be
        while(count <= 6) {
            player.drawCard(deck);
            computer.drawCard(deck);
            count+=1;
        }
        player.handToString();
        System.out.println("");
        computer.handToString();
        System.out.println("");
        //int test=0;
        //for(Card c: deck.getCardsinDeck()) {
        //    System.out.println(c);
        //    test+=1;
        //}
        //System.out.println(test);
        //Card testCard = new Card(10, Suit.HEARTS);
        //System.out.println(testCard);


    }
}

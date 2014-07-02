package com.gonzalch.blackjacktutorial.bt;

/**
 * Created by chrisgonzalez on 6/24/14.
 */
/*
    An object of class BlackjackGame represents the current state of the game. It contains the deck as
    well as player and dealers hands and includes methods to start a new round of Blackjack, deal initial
    cards, allow player to hit, split, double, stand, and surrender.
 */
public class BlackjackGame {
    private BlackjackHand playerHand;
    private BlackjackHand dealerHand;
    private Deck deck;

    private Boolean inProgress = false;

    public BlackjackGame() {
        if (playerHand == null)
            playerHand = new BlackjackHand();
        else
            playerHand.clear();

        if (dealerHand == null)
            dealerHand = new BlackjackHand();
        else
            dealerHand.clear();

        if (deck == null)
            deck = new Deck();

        deck.shuffle();

        inProgress = true;

        dealCards();
    }

    public void dealCards() {
        // Deal cards from deck into player and dealer hands.

        if (deck.cardsLeft() < 26) {
            deck = new Deck();
            deck.shuffle();
        }



        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());

    }

    public void playerHit() {
        //
        if (deck.cardsLeft() < 26) {
            deck = new Deck();
            deck.shuffle();
        }
        // Add card into players hand.
        playerHand.addCard(deck.dealCard());

        // Check if player has busted. If true, game ends and player loses.
        // if (playerHand.getBlackjackValue() > 21)

        // Check if player has 21. If true, game ends and check if player wins.
        // if (playerHand.getBlackjackValue() == 21)

    }
}

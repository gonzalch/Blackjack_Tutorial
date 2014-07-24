package com.gonzalch.blackjacktutorial.bt;

/**
 * Created by chrisgonzalez on 6/12/14.
 */

/*
    An object of type Deck represents an ordinary deck of 52 playing cards.
    The deck can be shuffled, and cards can be dealt from the deck.
*/

public class Deck {

    private Card[] deck;   // An array of 52 Cards, representing the deck.
    private int cardsUsed; // How many cards have been dealt from the deck.

    public Deck() {
        // Create an unshuffled deck of cards.
        // Checks for number of decks being used and creates appropriate sized deck.
        deck = new Card[52*Rules.classAInstance.numberOfDecks];
        int cardCt = 0; // How many cards have been created so far.
        for (int numDecks = 0; numDecks < Rules.classAInstance.numberOfDecks; numDecks++) {
            for (int suit = 0; suit <= 3; suit++) {
                for (int value = 1; value <= 13; value++) {
                    // Passes in cardCt to act as index.
                    deck[cardCt] = new Card(value, suit, cardCt);
                    cardCt++;
                }
            }
        }
        cardsUsed = 0;
    }

    public void shuffle() {
        // Put all the used cards back into the deck, and shuffle it into
        // a random order.
        for ( int i = 51; i > 0; i-- ) {
            int rand = (int)(Math.random()*(i+1));
            Card temp = deck[i];
            deck[i] = deck[rand];
            deck[rand] = temp;
        }
        cardsUsed = 0;
    }

    public int cardsLeft() {
        // As cards are dealt from the deck, the number of cards left
        // decreases.  This function returns the number of cards that
        // are still left in the deck.
        return 52 - cardsUsed;
    }

    public Card dealCard() {
        // Deals one card from the deck and returns it.
        if (cardsUsed == 52)
            shuffle();
        cardsUsed++;
        return deck[cardsUsed - 1];
    }


} // end class Deck
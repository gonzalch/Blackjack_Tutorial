package com.gonzalch.blackjacktutorial.bt;

/**
 * Created by chrisgonzalez on 6/12/14.
 */

/*
   An object of class card represents one of the 52 cards in a
   standard deck of playing cards.  Each card has a suit and
   a value.
*/

public class Card {

    public final static int SPADES = 0,       // Codes for the 4 suits.
            HEARTS = 1,
            DIAMONDS = 2,
            CLUBS = 3;

    public final static int ACE = 1,          // Codes for the non-numeric cards.
            JACK = 11,        //   Cards 2 through 10 have their
            QUEEN = 12,       //   numerical values for their codes.
            KING = 13;

    private final int suit;   // The suit of this card, one of the constants
    //    SPADES, HEARTS, DIAMONDS, CLUBS.

    private final int value;  // The value of this card, from 1 to 11.
    private final int index;  // Index of this card, from 0 to 51.

    public Card(int theValue, int theSuit, int theIndex) {
        // Construct a card with the specified value, suit, and index.
        // Value must be between 1 and 13.  Suit must be between
        // 0 and 3. Index must be between 0 and 51. If the parameters are outside
        // these ranges, the constructed card object will be invalid.
        suit = theSuit;
        index = theIndex;

        switch ( (index + 1) % 13 )
        {
            case 1:     value=11; break;
            case 11:    value=10; break;
            case 12:    value=10; break;
            case 0:     value=10; break;
            default:    value = theValue; break;
        }

    }

    public int getSuit() {
        // Return the int that codes for this card's suit.
        return suit;
    }

    public int getValue() {
        // Return the int that codes for this card's value.
        return value;
    }

    public int getIndex() {
        // Return the int that codes for this card's index.
        return index;
    }

    public String getSuitAsString() {
        // Return a String representing the card's suit.
        // (If the card's suit is invalid, "??" is returned.)
        switch ( suit ) {
            case SPADES:   return "Spades";
            case HEARTS:   return "Hearts";
            case DIAMONDS: return "Diamonds";
            case CLUBS:    return "Clubs";
            default:       return "??";
        }
    }

    public String getValueAsString() {
        // Return a String representing the card's value.
        // If the card's value is invalid, "??" is returned.
        switch ( (index + 1) % 13 ) {
            case 1:   return "Ace";
            case 2:   return "2";
            case 3:   return "3";
            case 4:   return "4";
            case 5:   return "5";
            case 6:   return "6";
            case 7:   return "7";
            case 8:   return "8";
            case 9:   return "9";
            case 10:  return "10";
            case 11:  return "Jack";
            case 12:  return "Queen";
            case 0:  return "King";
            default:  return "??";
        }
    }

    public String toString() {
        // Return a String representation of this card, such as
        // "10 of Hearts" or "Queen of Spades".
        return getValueAsString() + " of " + getSuitAsString();
    }

    public String toTruncatedString() {
        // Return a String representation of this card, such as
        // "10H" or "QS".
        StringBuilder s;
        if(getValueAsString() ==  "10" ) {
            s = new StringBuilder().append(getValueAsString()).append(getSuitAsString().charAt(0));
        }
        else{
            s = new StringBuilder().append( getValueAsString().charAt(0)).append(getSuitAsString().charAt(0));
        }

        return s.toString();
    }


} // end class Card
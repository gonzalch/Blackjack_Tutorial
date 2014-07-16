package com.gonzalch.blackjacktutorial.bt;

/**
 * Created by alex on 7/14/2014.
 *
 * Class to control the actions of a player.  A player has a bankroll and a hand.
 * This class handles the interactions between those two structures and the table.
 */

import java.util.Vector;

public class Player {
    public static Player classAInstance = new Player();
    private Bankroll currentBankroll;
    private Hand currentHand;

    public Player(){
        currentBankroll = new Bankroll();
        currentHand = new Hand();
    }

    public String getHandAsString(){
        return currentHand.getHandAsString();
    }

    public void addCard(Card c){
        if(currentHand == null)
        {
            currentHand = new Hand();
        }
        currentHand.addCard(c);
    }

    public int getCardCount(){
        return currentHand.getCardCount();
    }

    public Hand getHand(){
        return currentHand;
    }



    public int getHandTotal()
    {
        return currentHand.getHandValue();
    }

    public boolean hasBlackjack()
    {
        if(currentHand.getCardCount() == 2)
        {
            if(currentHand.getHandValue() == 21)
            {
                return true;
            }
        }
        return false;
    }

    public void removeHand()
    {
        currentHand = null;
    }
}

package com.gonzalch.blackjacktutorial.bt;

/**
 * Created by alex on 7/14/2014.
 */

import java.util.Vector;

public class Player {
    public static Player classAInstance = new Player();
    private Bankroll currentBankroll;
    private Hand currentHand;
    private boolean busted = false;

    public Player(){
        currentBankroll = new Bankroll();
        currentHand = new Hand();
    }

    public String getHandAsString(){
        return currentHand.getHandAsString();
    }

    public void addCard(Card c){
        currentHand.addCard(c);

    }

    public int getCardCount(){
        return currentHand.getCardCount();
    }

    public Hand getHand(){
        return currentHand;
    }

    public boolean hasBusted()
    {
        return busted;
    }

    public String getHandTotalAsString()
    {
        return Integer.toString(currentHand.getHandValue());
    }
}

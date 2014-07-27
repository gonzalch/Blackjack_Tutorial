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
    private Vector splitHands;

    public Player(){
        currentBankroll = new Bankroll();
        currentHand = new Hand();
        splitHands = new Vector();
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

    public void splitHand(Card c1, Card c2){
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();

        hand1.addCard(currentHand.getCard(0));
        hand1.addCard(c1);

        hand2.addCard(currentHand.getCard(1));
        hand2.addCard(c2);

        currentHand = hand1;
        splitHands.add(hand2);
    }

    public Hand getPlayersSplitHand(){
        return (Hand) splitHands.elementAt(0);
    }

    public float getBankroll() {
        return currentBankroll.getMoney();
    }

    public void modifyBankroll(float bet, boolean sign) {
        if (sign) {
            currentBankroll.setMoney(currentBankroll.getMoney() + bet);
        }
        else if (bet <= currentBankroll.getMoney()) {
            currentBankroll.setMoney(currentBankroll.getMoney() - bet);
        }

        //else player is fucked
    }
}

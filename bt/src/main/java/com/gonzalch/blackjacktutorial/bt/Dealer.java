package com.gonzalch.blackjacktutorial.bt;
import android.util.Log;
/**
 * Created by alex on 7/14/2014.
 *
 * Similar to a player.
 */


import java.util.Vector;

import com.gonzalch.blackjacktutorial.bt.Rules;

public class Dealer extends Player {
    private static final String TAG = "Dealer Class";
    private Hand currentHand;
    private boolean willHit = true;
    private Rules currentRules;

    public Dealer()
    {
        currentHand = new Hand();
    }

    public String getHandAsString(){
        String hand = currentHand.getHandAsString();
        Log.d(TAG, "Dealer hand: " + hand);

        return hand;
    }

    public void addCard(Card c){
        if(currentHand == null)
        {
            currentHand = new Hand();
        }
        currentHand.addCard(c);
    }


    public Hand getHand(){
        return currentHand;
    }

    public boolean hasSoftSeventeen()
    {
        if (getCardCount() == 2)
        {
            Card c1 = currentHand.getCard(0);
            Card c2 = currentHand.getCard(1);
            if(c1.getValueAsString() == "Ace" | c1.getValueAsString() == "Ace")
            {
                if( currentHand.getHandValue() == 17 )
                {
                    return true;
                }
            }
        }
        return false;
    }

    public int getHandTotal()
    {
        return currentHand.getHandValue();
    }

    public boolean willHit(){
        return willHit;
    }

    public void removeHand()
    {
        currentHand = null;
    }

    public int getCardCount(){ return currentHand.getCardCount(); }

}

package com.gonzalch.blackjacktutorial.bt;
import android.util.Log;
/**
 * Created by alex on 7/14/2014.
 */

import java.util.Vector;

import com.gonzalch.blackjacktutorial.bt.Rules;

public class Dealer extends Player {
    private static final String TAG = "Dealer Class";
    private boolean shouldRevealSecondCard;
    private Hand currentHand;
    private boolean willHit = true;
    private Rules currentRules;

    public Dealer()
    {
        currentHand = new Hand();
        shouldRevealSecondCard = false;
    }

    public String getHandAsString(){
        String hand = currentHand.getHandAsString();
        Log.d(TAG, "Dealer hand: " + hand);

        if(shouldRevealSecondCard == false){
            return hand.substring(0,3);
        }
        return hand;
    }

    public void addCard(Card c){
        currentHand.addCard(c);

    }




    public boolean willHit(){
        return willHit;
    }
}

package com.gonzalch.blackjacktutorial.bt;

/**
 * Created by alex on 7/14/2014.
 */
public class Rules {
    private boolean allowSurrender;
    private boolean dealerHitsOnSoft17;
    private int numberOfDecks;

    public Rules(){
        allowSurrender = false;
        dealerHitsOnSoft17 = false;
        numberOfDecks = 1;
    }
}

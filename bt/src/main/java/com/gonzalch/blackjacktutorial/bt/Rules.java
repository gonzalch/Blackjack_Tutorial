package com.gonzalch.blackjacktutorial.bt;

/**
 * Created by alex on 7/14/2014.
 */
public class Rules {
    public static Rules classAInstance = new Rules();

    public boolean allowSurrender;
    public boolean dealerHitsOnSoft17;
    public int numberOfDecks;

    public Rules(){
        allowSurrender = false;
        dealerHitsOnSoft17 = false;
        numberOfDecks = 1;
    }
}

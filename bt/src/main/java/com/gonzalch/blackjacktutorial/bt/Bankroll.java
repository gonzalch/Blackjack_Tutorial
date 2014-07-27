package com.gonzalch.blackjacktutorial.bt;

import android.util.Log;

/**
 * Created by alex on 7/14/2014.
 *
 * Basic class to store the money available to the dealer or the player
 */

public class Bankroll {
    private static final String TAG = "Bankroll";
    private float money;

    public Bankroll(){
        money = 1000;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float newMoney) {
        Log.d(TAG, "money = " + String.valueOf(money));
        money = newMoney;
        Log.d(TAG, "newMoney = " + String.valueOf(money));
    }
}

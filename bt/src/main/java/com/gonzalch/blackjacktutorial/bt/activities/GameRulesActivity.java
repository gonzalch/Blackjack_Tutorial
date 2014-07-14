/**
 * Created by Alex Taylor on 7/7/14.
 */
/*
    This activity runs when the player would like to alter the house Blackjack rules.
*/

package com.gonzalch.blackjacktutorial.bt.activities;


import android.os.Bundle;

import android.preference.PreferenceActivity;
import com.gonzalch.blackjacktutorial.bt.R;

public class GameRulesActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.game_rules);

    }
}
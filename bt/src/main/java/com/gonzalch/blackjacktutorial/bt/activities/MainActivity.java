package com.gonzalch.blackjacktutorial.bt.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gonzalch.blackjacktutorial.bt.BlackjackHand;
import com.gonzalch.blackjacktutorial.bt.Deck;
import com.gonzalch.blackjacktutorial.bt.R;
import com.gonzalch.blackjacktutorial.bt.House;
import com.gonzalch.blackjacktutorial.bt.Table;
import com.gonzalch.blackjacktutorial.bt.Player;

public class MainActivity extends ActionBarActivity {

    private static final int RESULT_RULES = 1;

    private static final String TAG = "MainActivity";

    private House house;

    private Button mDealButton;
    private Button mHitButton;
    private Button mStandButton;
    private Button mSplitButton;
    private Button mDoubleButton;
    private TextView mDealerHandText;
    private TextView mPlayerHandText;

    private BlackjackHand mPlayerHand;
    private BlackjackHand mDealerHand;
    //private int mPlayerHandValue;
    private Deck mDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        house = new House();
//        mDeck = new Deck();
//        mDeck.shuffle();


        //mPlayerHandValue = 0;
        //dealt = false;
        mDealerHandText = (TextView) findViewById(R.id.dealer_hand);
        mPlayerHandText = (TextView) findViewById(R.id.player_hand);


        mDealButton = (Button) findViewById(R.id.deal_button);
        mDealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Table.classAInstance.startGame();
                    mPlayerHandText.setText(Table.classAInstance.getPlayerHand());
                    mDealerHandText.setText(Table.classAInstance.getDealerHand());
                    mDealButton.setEnabled(false);
                    mHitButton.setEnabled(true);
                    mStandButton.setEnabled(true);
                    mDoubleButton.setEnabled(Table.classAInstance.canPlayerDouble());
                    mSplitButton.setEnabled(Table.classAInstance.canPlayerSplit());
                }
        });

        mHitButton = (Button) findViewById(R.id.hit_button);
        mHitButton.setEnabled(false);
        mHitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table.classAInstance.playerHit();
                mPlayerHandText.setText(Table.classAInstance.getPlayerHand());
                mHitButton.setEnabled(Table.classAInstance.gameOver());
                mStandButton.setEnabled(!Table.classAInstance.gameOver());
                mDoubleButton.setEnabled(Table.classAInstance.canPlayerDouble());
                mSplitButton.setEnabled(Table.classAInstance.canPlayerSplit());

//                if (dealt) {
//                    mPlayerHand.addCard(mDeck.dealCard());
//                    Log.d(TAG, "Player: " + mPlayerHand.getCard(mPlayerHand.getCardCount() - 1).getValueAsString() + " of " + mPlayerHand.getCard(mPlayerHand.getCardCount() - 1).getSuitAsString() + " dealt.");
//
//                    Log.d(TAG, "Hand Value: " + mPlayerHand.getBlackjackValue());
//
//                    mPlayerHandText.setText(mPlayerHand.getHandAsString());
//
//                    if (mPlayerHand.getBlackjackValue() == 21) {
//                        //Log.i(TAG, "21!");
//                        dealt = false;
//                        Toast.makeText(MainActivity.this, "21!", Toast.LENGTH_SHORT).show();
//                    }
//
//                    if (mPlayerHand.getBlackjackValue() > 21) {
//                        //Log.i(TAG, "You busted!");
//                        dealt = false;
//                        Toast.makeText(MainActivity.this, "Busted", Toast.LENGTH_SHORT).show();
//                    }
//                }
            }
        });

        mStandButton = (Button) findViewById(R.id.stand_button);
        mStandButton.setEnabled(false);
        mStandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mPlayerHand != null) {
//                    if (mPlayerHand.getBlackjackValue() > mDealerHand.getBlackjackValue()) {
//                        Toast.makeText(MainActivity.this, "You Won!", Toast.LENGTH_SHORT).show();
//                        dealt = false;
//                    }
//
//                    else if (mPlayerHand.getBlackjackValue() == mDealerHand.getBlackjackValue()) {
//                        Toast.makeText(MainActivity.this, "Push", Toast.LENGTH_SHORT).show();
//                        dealt = false;
//                    }
//
//                    else {
//                        Toast.makeText(MainActivity.this, "You lose", Toast.LENGTH_SHORT).show();
//                        dealt = false;
//                    }
//                }
            }
        });
        mDoubleButton = (Button) findViewById(R.id.double_button);
        mDoubleButton.setEnabled(false);
        mDoubleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mSplitButton = (Button) findViewById(R.id.split_button);
        mSplitButton.setEnabled(false);
        mSplitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int a = 0;
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.house_rules:
                Intent intent = new Intent(this, GameRulesActivity.class);
                startActivityForResult(intent, RESULT_RULES);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}

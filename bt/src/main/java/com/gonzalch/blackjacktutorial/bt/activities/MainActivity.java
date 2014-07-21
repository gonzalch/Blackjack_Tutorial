package com.gonzalch.blackjacktutorial.bt.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
    private TextView mPlayerTotalText;
    private TextView mDealersTotalText;
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
        mPlayerTotalText = (TextView) findViewById(R.id.player_total);
        mDealersTotalText = (TextView) findViewById(R.id.dealer_total);

        mDealButton = (Button) findViewById(R.id.deal_button);
        mDealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table.classAInstance.reset();
                Table.classAInstance.startGame();

                refresh();

                if(Table.classAInstance.checkPlayerForBlackjack())
                {
                    Toast.makeText(MainActivity.this, "Blackjack!", Toast.LENGTH_SHORT).show();
                    refresh();
                }
                else if(Table.classAInstance.checkDealerForBlackjack())
                {
                    Toast.makeText(MainActivity.this, "You lose - dealer blackjack", Toast.LENGTH_SHORT).show();
                    refresh();
                }
            }
        });

        mHitButton = (Button) findViewById(R.id.hit_button);
        mHitButton.setEnabled(false);
        mHitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table.classAInstance.playerHit();
                refresh();

                if( Table.classAInstance.playerBusted())
                {
                    Toast.makeText(MainActivity.this, "Busted!", Toast.LENGTH_SHORT).show();
                    refresh();
                    Table.classAInstance.reset();
                }
            }
        });

        mStandButton = (Button) findViewById(R.id.stand_button);
        mStandButton.setEnabled(false);
        mStandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table.classAInstance.playerStands();
                refresh();
                 if ( Table.classAInstance.checkForTie()) {
                    Toast.makeText(MainActivity.this, "Push", Toast.LENGTH_SHORT).show();
                    refresh();
                } else if (Table.classAInstance.playerWon()) {
                    Toast.makeText(MainActivity.this, "You win", Toast.LENGTH_SHORT).show();
                    refresh();
                } else {
                    Toast.makeText(MainActivity.this, "You lose", Toast.LENGTH_SHORT).show();
                    refresh();
                }
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
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.house_rules:
                intent = new Intent(this, GameRulesActivity.class);
                startActivityForResult(intent, RESULT_RULES);
                break;
            case R.id.blackjack_table:
                intent = new Intent(this, BlackjackTableActivity.class);
                startActivityForResult(intent, RESULT_RULES);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /*
     *  Check the table instance for updates to the state of the game.
     */
    private void refresh()
    {
        mDealersTotalText.setText(Table.classAInstance.getDealersTotalAsString());
        mDealerHandText.setText(Table.classAInstance.getDealerHandAsString());
        mPlayerTotalText.setText(Table.classAInstance.getPlayersTotalAsString());
        mPlayerHandText.setText(Table.classAInstance.getPlayerHandAsString());
        mDealButton.setEnabled(!Table.classAInstance.isGameInProgress());
        mHitButton.setEnabled(Table.classAInstance.isGameInProgress());
        mStandButton.setEnabled(Table.classAInstance.isGameInProgress());
        mDoubleButton.setEnabled(Table.classAInstance.canPlayerDouble());
        mSplitButton.setEnabled(Table.classAInstance.canPlayerSplit());
    }
}

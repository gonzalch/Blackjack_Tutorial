package com.gonzalch.blackjacktutorial.bt;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";
    private Boolean dealt;
    private Button mDealButton;
    private Button mHitButton;
    private Button mStandButton;
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

        mDeck = new Deck();
        mDeck.shuffle();

        //mPlayerHandValue = 0;
        dealt = false;
        mDealerHandText = (TextView) findViewById(R.id.dealer_hand);
        mPlayerHandText = (TextView) findViewById(R.id.player_hand);


        mDealButton = (Button) findViewById(R.id.deal_button);
        mDealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dealt) {
                    dealHand();
                    dealt = true;
                }

                else {
                    Toast.makeText(MainActivity.this, "You've already dealt", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mHitButton = (Button) findViewById(R.id.hit_button);
        mHitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dealt) {
                    mPlayerHand.addCard(mDeck.dealCard());
                    Log.d(TAG, "Player: " + mPlayerHand.getCard(mPlayerHand.getCardCount() - 1).getValueAsString() + " of " + mPlayerHand.getCard(mPlayerHand.getCardCount() - 1).getSuitAsString() + " dealt.");

                    Log.d(TAG, "Hand Value: " + mPlayerHand.getBlackjackValue());

                    mPlayerHandText.setText(mPlayerHand.getHandAsString());

                    if (mPlayerHand.getBlackjackValue() == 21) {
                        //Log.i(TAG, "21!");
                        dealt = false;
                        Toast.makeText(MainActivity.this, "21!", Toast.LENGTH_SHORT).show();
                    }

                    if (mPlayerHand.getBlackjackValue() > 21) {
                        //Log.i(TAG, "You busted!");
                        dealt = false;
                        Toast.makeText(MainActivity.this, "Busted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mStandButton = (Button) findViewById(R.id.stand_button);
        mStandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPlayerHand != null) {
                    if (mPlayerHand.getBlackjackValue() > mDealerHand.getBlackjackValue()) {
                        Toast.makeText(MainActivity.this, "You Won!", Toast.LENGTH_SHORT).show();
                        dealt = false;
                    }

                    else if (mPlayerHand.getBlackjackValue() == mDealerHand.getBlackjackValue()) {
                        Toast.makeText(MainActivity.this, "Push", Toast.LENGTH_SHORT).show();
                        dealt = false;
                    }

                    else {
                        Toast.makeText(MainActivity.this, "You lose", Toast.LENGTH_SHORT).show();
                        dealt = false;
                    }
                }
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void dealHand() {

        if (mPlayerHand == null)
            mPlayerHand = new BlackjackHand();
        else
            mPlayerHand.clear();

        if (mDealerHand == null)
            mDealerHand = new BlackjackHand();
        else
            mDealerHand.clear();

        mPlayerHand.addCard(mDeck.dealCard());
        Log.d(TAG, "Player: " + mPlayerHand.getCard(0).getValueAsString() + " of " + mPlayerHand.getCard(0).getSuitAsString() + " dealt.");

        mPlayerHand.addCard(mDeck.dealCard());
        Log.d(TAG, "Player: " + mPlayerHand.getCard(1).getValueAsString() + " of " + mPlayerHand.getCard(1).getSuitAsString() + " dealt.");



        mDealerHand.addCard(mDeck.dealCard());
        Log.d(TAG, "Dealer: " + mDealerHand.getCard(0).getValueAsString() + " of " + mDealerHand.getCard(0).getSuitAsString() + " dealt.");
        mDealerHand.addCard(mDeck.dealCard());
        Log.d(TAG, "Dealer: " + mDealerHand.getCard(1).getValueAsString() + " of " + mDealerHand.getCard(1).getSuitAsString() + " dealt.");

        mPlayerHandText.setText(mPlayerHand.getHandAsString());
        mDealerHandText.setText(mDealerHand.getHandAsString());
    }
}

package com.gonzalch.blackjacktutorial.bt.activities;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Display;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Paint;
import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.Button;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

/*
 *  Cards are drawn proportionally to the screensize.
 *
 *  There are two layers to this Activity.  The background layer uses a View class to draw
 *  cards to the canvas.  The top layer contains the buttons and text.
 */

import com.gonzalch.blackjacktutorial.bt.House;
import com.gonzalch.blackjacktutorial.bt.R;
import com.gonzalch.blackjacktutorial.bt.Table;
import com.gonzalch.blackjacktutorial.bt.Hand;
import com.gonzalch.blackjacktutorial.bt.Card;

public class BlackjackTableActivity extends ActionBarActivity {
    private static final String TAG = "Blackjack Table View";

    FrameLayout mBackgroundFrame;
    LinearLayout mButtonLayout;
    BlackjackTableView mBlackjackTableView;

    private Button mDealButton;
    private Button mHitButton;
    private Button mStandButton;
    private Button mSplitButton;
    private Button mDoubleButton;

    /**********************CHIP BUTTONS**********************/
    private Button mBetFiveButton;
    private Button mBetTenButton;
    private Button mBetTwentyFiveButton;
    private Button mBetFiftyButton;
    private Button mBetHundredButton;

    private TextView mPlayerBankroll;
    private TextView mPlayerBetTotal;

    private House house;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack_table);

        mBlackjackTableView = new BlackjackTableView(this);
        mBackgroundFrame = (FrameLayout) findViewById(R.id.background_layout);
        mBackgroundFrame.addView(mBlackjackTableView);
        mButtonLayout = (LinearLayout) findViewById(R.id.buttons_frame);
        mButtonLayout.bringToFront();

        house = new House();

        mDealButton = (Button) findViewById(R.id.place_bet_button);
        mHitButton = (Button) findViewById(R.id.hit_button);
        mStandButton = (Button) findViewById(R.id.stand_button);
        mDoubleButton = (Button) findViewById(R.id.double_button);
        mSplitButton = (Button) findViewById(R.id.split_button);

        mBetFiveButton = (Button) findViewById(R.id.bet_five_button);
        mBetTenButton = (Button) findViewById(R.id.bet_ten_button);
        mBetTwentyFiveButton = (Button) findViewById(R.id.bet_twenty_five_button);
        mBetFiftyButton = (Button) findViewById(R.id.bet_fifty_button);
        mBetHundredButton = (Button) findViewById(R.id.bet_hundred_button);

        mPlayerBankroll = (TextView) findViewById(R.id.player_bankroll);
        mPlayerBetTotal = (TextView) findViewById(R.id.player_bet_total);

        mPlayerBankroll.setText(String.valueOf(Table.classAInstance.getPlayerBankroll()));

        mHitButton.setEnabled(false);
        mStandButton.setEnabled(false);
        mDoubleButton.setEnabled(false);
        mSplitButton.setEnabled(false);

        /******************************************************************************************
         * L I S T E N E R S
         *****************************************************************************************/
        mDealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table.classAInstance.reset();
                Table.classAInstance.startGame();
                mBlackjackTableView.setClearTable(true);
                refresh();
                mBlackjackTableView.invalidate();

                if (Table.classAInstance.checkPlayerForBlackjack()) {
                    Toast.makeText(BlackjackTableActivity.this, "Blackjack!", Toast.LENGTH_SHORT).show();
                    refresh();
                } else if (Table.classAInstance.checkDealerForBlackjack()) {
                    Toast.makeText(BlackjackTableActivity.this, "You lose - dealer blackjack.", Toast.LENGTH_SHORT).show();
                    refresh();
                }
            }
        });

        mHitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table.classAInstance.playerHit();
                refresh();

                if (Table.classAInstance.playerBusted()) {
                    Toast.makeText(BlackjackTableActivity.this, "Busted!", Toast.LENGTH_SHORT).show();
                    refresh();
                    mBlackjackTableView.invalidate();
                }
            }
        });

        mStandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table.classAInstance.playerStands();
                refresh();
                mBlackjackTableView.invalidate();

                if (Table.classAInstance.checkForTie()) {
                    Toast.makeText(BlackjackTableActivity.this, "Push", Toast.LENGTH_SHORT).show();
                    refresh();
                } else if (Table.classAInstance.playerWon()) {
                    Toast.makeText(BlackjackTableActivity.this, "You win", Toast.LENGTH_SHORT).show();
                    refresh();
                } else {
                    Toast.makeText(BlackjackTableActivity.this, "You lose", Toast.LENGTH_SHORT).show();
                    refresh();
                }
            }
        });

        mSplitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Table.classAInstance.playerStands();
                Table.classAInstance.playerSplit();
                refresh();
                mBlackjackTableView.invalidate();
            }
        });

        mBetFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table.classAInstance.increaseCurrentBetTotal(5);
                refresh();
            }
        });


        mBetTenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table.classAInstance.increaseCurrentBetTotal(10);
                refresh();
            }
        });

        mBetTwentyFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table.classAInstance.increaseCurrentBetTotal(25);
                refresh();
            }
        });

        mBetFiftyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table.classAInstance.increaseCurrentBetTotal(50);
                refresh();
            }
        });

        mBetHundredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table.classAInstance.increaseCurrentBetTotal(100);
                refresh();
            }
        });


        setContentView(mBackgroundFrame);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.blackjack_table, menu);
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

    /*
     *  Refresh the state of the game.  This includes adding cards to the display, highlighting
     *  buttons, and displaying totals.
     */
    private void refresh() {
        mDealButton.setEnabled(!Table.classAInstance.isGameInProgress());
        mHitButton.setEnabled(Table.classAInstance.isGameInProgress());
        mStandButton.setEnabled(Table.classAInstance.isGameInProgress());
        mDoubleButton.setEnabled(Table.classAInstance.canPlayerDouble());
        mSplitButton.setEnabled(Table.classAInstance.canPlayerSplit());
        mBetFiveButton.setEnabled(!Table.classAInstance.isGameInProgress());
        mBetTenButton.setEnabled(!Table.classAInstance.isGameInProgress());
        mBetTwentyFiveButton.setEnabled(!Table.classAInstance.isGameInProgress());
        mBetFiftyButton.setEnabled(!Table.classAInstance.isGameInProgress());
        mBetHundredButton.setEnabled(!Table.classAInstance.isGameInProgress());

        mBlackjackTableView.setRevealDealer(Table.classAInstance.getRevealDealer());
        mBlackjackTableView.setDealerTotal(Table.classAInstance.getDealersTotalAsString());
        mBlackjackTableView.setPlayerTotal(Table.classAInstance.getPlayersTotalAsString());
        mPlayerBankroll.setText(String.valueOf(Table.classAInstance.getPlayerBankroll()));
        mPlayerBetTotal.setText(String.valueOf(Table.classAInstance.getCurrentBetTotal()));

        if (!Table.classAInstance.hasPlayerSplit()) {
            if (Table.classAInstance.getPlayersHandSize() > mBlackjackTableView.getPlayersHandSize()) {
                Hand h = (Hand) Table.classAInstance.getPlayerHand();
                for (int i = mBlackjackTableView.getPlayersHandSize(); i < Table.classAInstance.getPlayersHandSize(); i++) {
                    Card c = h.getCard(i);
                    mBlackjackTableView.addCardToPlayer(c.getIndex());
                }
            }
            if (Table.classAInstance.getDealersHandSize() > mBlackjackTableView.getDealersHandSize()) {
                Hand h = (Hand) Table.classAInstance.getDealerHand();
                for (int i = mBlackjackTableView.getDealersHandSize(); i < Table.classAInstance.getDealersHandSize(); i++) {
                    Card c = h.getCard(i);
                    mBlackjackTableView.addCardToDealer(c.getIndex());
                }
            }
        } else {
            mBlackjackTableView.setPlayerSplit(true);
            mBlackjackTableView.clearPlayersHand();
            Hand h = (Hand) Table.classAInstance.getPlayerHand();
            for (int i = mBlackjackTableView.getPlayersHandSize(); i < Table.classAInstance.getPlayersHandSize(); i++) {
                Card c = h.getCard(i);
                mBlackjackTableView.addCardToPlayer(c.getIndex());
            }
            h = (Hand) Table.classAInstance.getPlayersSplitHand();
            for (int i = 0; i < h.getCardCount(); i++) {
                Card c = h.getCard(i);
                mBlackjackTableView.addCardToPlayerSplitHand(c.getIndex());
            }
        }
    }

    /**
     * *******************************************************************************************
     * This class handles the drawing of the cards onto the background canvas.
     * <p/>
     * The resourceIDS are hard coded to be the IDS of the cards in order.
     * The order. is Spades,Hearts, Diamonds, Spades.
     * *******************************************************************************************
     */
    public class BlackjackTableView extends View {
        private int cardHeight;
        private int cardWidth;
        private int screenHeight;
        private int screenWidth;

        private Vector dealersCardsImages;
        private Vector playersCardsImages;
        private Vector playerSplitCardsImages;

        String dealerTotal;
        String playerTotal;
        String playerSplitTotal;

        int[] resourceIDS;

        private boolean revealDealer = false;
        private boolean redrawTable = false;
        private boolean playerSplit = false;

        public BlackjackTableView(Context context) {
            super(context);
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            if (size.x < size.y) {
                cardWidth = (int) (size.x / 5.3);
                cardHeight = (int) (size.y / 6.5);
                screenHeight = size.y;
                screenWidth = size.x;
            } else {
                cardWidth = (int) (size.y / 5.3);
                cardHeight = (int) (size.x / 6.5);
                screenHeight = size.x;
                screenWidth = size.y;
            }

            resourceIDS = new int[52];
            initResourceIDS();

            dealersCardsImages = new Vector();
            playersCardsImages = new Vector();
            playerSplitCardsImages = new Vector();
        }

        @Override
        public void onDraw(Canvas canvas) {
            if (redrawTable == true) {
                clearTable(canvas);
                canvas.drawColor(Color.rgb(0, 140, 0));

                refresh();
                redrawTable = false;
            } else {
                canvas.drawColor(Color.rgb(0, 140, 0));
                if (playerSplit) {
                    drawCardsWithSplit(canvas);
                } else {
                    drawCards(canvas);
                }
            }
        }

        private void drawCards(Canvas canvas) {
            int left = 0;
            int top = 0;
            int right;
            int bottom;

            for (int i = 0; i < dealersCardsImages.size(); i++) {
                Drawable c;
                if (i == 1 & !revealDealer) {
                    c = getResources().getDrawable(R.drawable.b1fv);
                } else {
                    c = (Drawable) dealersCardsImages.elementAt(i);
                }

                left = (screenWidth / 3) + (i * (cardWidth / 5));
                top = (screenHeight / 8);
                right = ((screenWidth / 3) + cardWidth) + (i * (cardWidth / 5));
                bottom = ((screenHeight / 8) + cardHeight);
                c.setBounds(left, top, right, bottom);
                c.draw(canvas);


            }
            if (dealersCardsImages.size() != 0) {
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setTextSize(35);
                canvas.drawText(dealerTotal, (screenWidth / 3) - screenWidth / 10, (screenHeight / 8) + screenHeight / 30, paint);
            }

            for (int i = 0; i < playersCardsImages.size(); i++) {
                Drawable c = (Drawable) playersCardsImages.elementAt(i);
                left = (screenWidth / 3) + (i * (cardWidth / 5));
                top = (int) ((screenHeight / 8) * 3.5);
                right = ((screenWidth / 3) + cardWidth) + (i * (cardWidth / 5));
                bottom = (int) (((screenHeight / 8) * 3.5) + cardHeight);

                c.setBounds(left, top, right, bottom);
                c.draw(canvas);
            }
            if (playersCardsImages.size() != 0) {
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setTextSize(35);
                canvas.drawText(playerTotal, (screenWidth / 3) - screenWidth / 10, (int) ((screenHeight / 8) * 3.5) + screenHeight / 30, paint);
            }
        }

        private void drawCardsWithSplit(Canvas canvas) {
            for (int i = 0; i < dealersCardsImages.size(); i++) {
                Drawable c;
                if (i == 1 & !revealDealer) {
                    c = getResources().getDrawable(R.drawable.b1fv);
                } else {
                    c = (Drawable) dealersCardsImages.elementAt(i);
                }
                int left = (screenWidth / 3) + (i * (cardWidth / 5));
                int top = (screenHeight / 8);
                int right = ((screenWidth / 3) + cardWidth) + (i * (cardWidth / 5));
                int bottom = ((screenHeight / 8) + cardHeight);
                c.setBounds(left, top, right, bottom);
                c.draw(canvas);
            }
            if (dealersCardsImages.size() != 0) {
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setTextSize(35);
                canvas.drawText(dealerTotal, (screenWidth / 3) - screenWidth / 10, (screenHeight / 8) + screenHeight / 30, paint);
            }

            for (int i = 0; i < playersCardsImages.size(); i++) {
                Drawable c = (Drawable) playersCardsImages.elementAt(i);
                int left = (screenWidth / 6) + (i * (cardWidth / 5));
                int top = (int) ((screenHeight / 8) * 3.5);
                int right = ((screenWidth / 6) + cardWidth) + (i * (cardWidth / 5));
                int bottom = (int) (((screenHeight / 8) * 3.5) + cardHeight);
                c.setBounds(left, top, right, bottom);
                c.draw(canvas);
            }
            if (playersCardsImages.size() != 0) {
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setTextSize(35);
                canvas.drawText(playerTotal, (screenWidth / 6) - screenWidth / 10, (int) ((screenHeight / 8) * 3.5) + screenHeight / 30, paint);
            }
            for (int i = 0; i < playerSplitCardsImages.size(); i++) {
                Drawable c = (Drawable) playerSplitCardsImages.elementAt(i);
                int left = ((screenWidth / 6) * 4) + (i * (cardWidth / 5));
                int top = (int) ((screenHeight / 8) * 3.5);
                int right = (((screenWidth / 6) * 4) + cardWidth) + (i * (cardWidth / 5));
                int bottom = (int) (((screenHeight / 8) * 3.5) + cardHeight);
                c.setBounds(left, top, right, bottom);
                c.draw(canvas);
            }
            if (playerSplitCardsImages.size() != 0) {
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setTextSize(35);
                canvas.drawText(playerTotal, ((screenWidth / 6) * 4) - screenWidth / 10, (int) ((screenHeight / 8) * 3.5) + screenHeight / 30, paint);
            }
        }

        private void clearTable(Canvas canvas) {
            canvas.drawColor(Color.rgb(0, 140, 0));
            dealersCardsImages.clear();
            playersCardsImages.clear();
        }

        public void addCardToDealer(int card) {

            Drawable c = getResources().getDrawable(resourceIDS[card]);
            dealersCardsImages.add(c);
            invalidate();
        }

        public void addCardToPlayer(int card) {

            Drawable c = getResources().getDrawable(resourceIDS[card]);
            playersCardsImages.add(c);
            invalidate();
        }

        public void addCardToPlayerSplitHand(int card) {

            Drawable c = getResources().getDrawable(resourceIDS[card]);
            playerSplitCardsImages.add(c);
            invalidate();
        }

        public void playerSplit(int card1, int card2) {
            playerSplit = true;
            playersCardsImages.clear();
            invalidate();
        }

        public int getPlayersHandSize() {
            return playersCardsImages.size();
        }

        public int getDealersHandSize() {
            return dealersCardsImages.size();
        }

        /**
         * ***************************************************************************************
         * S E T T E R S
         * ****************************************************************************************
         */
        public void setRevealDealer(boolean rd) {
            revealDealer = rd;
        }

        public void setClearTable(boolean ct) {
            redrawTable = ct;
        }

        public void setDealerTotal(String total) {
            dealerTotal = total;
        }

        public void setPlayerTotal(String total) {
            playerTotal = total;
        }

        public void setPlayerSplit(boolean b) {
            playerSplit = b;
        }


        private void initResourceIDS() {
            resourceIDS[0] = R.drawable.card0;
            resourceIDS[1] = R.drawable.card1;
            resourceIDS[2] = R.drawable.card2;
            resourceIDS[3] = R.drawable.card3;
            resourceIDS[4] = R.drawable.card4;
            resourceIDS[5] = R.drawable.card5;
            resourceIDS[6] = R.drawable.card6;
            resourceIDS[7] = R.drawable.card7;
            resourceIDS[8] = R.drawable.card8;
            resourceIDS[9] = R.drawable.card9;
            resourceIDS[10] = R.drawable.card10;
            resourceIDS[11] = R.drawable.card11;
            resourceIDS[12] = R.drawable.card12;
            resourceIDS[13] = R.drawable.card13;
            resourceIDS[14] = R.drawable.card14;
            resourceIDS[15] = R.drawable.card15;
            resourceIDS[16] = R.drawable.card16;
            resourceIDS[17] = R.drawable.card17;
            resourceIDS[18] = R.drawable.card18;
            resourceIDS[19] = R.drawable.card19;
            resourceIDS[20] = R.drawable.card20;
            resourceIDS[21] = R.drawable.card21;
            resourceIDS[22] = R.drawable.card22;
            resourceIDS[23] = R.drawable.card23;
            resourceIDS[24] = R.drawable.card24;
            resourceIDS[25] = R.drawable.card25;
            resourceIDS[26] = R.drawable.card26;
            resourceIDS[27] = R.drawable.card27;
            resourceIDS[28] = R.drawable.card28;
            resourceIDS[29] = R.drawable.card29;
            resourceIDS[30] = R.drawable.card30;
            resourceIDS[31] = R.drawable.card31;
            resourceIDS[32] = R.drawable.card32;
            resourceIDS[33] = R.drawable.card33;
            resourceIDS[34] = R.drawable.card34;
            resourceIDS[35] = R.drawable.card35;
            resourceIDS[36] = R.drawable.card36;
            resourceIDS[37] = R.drawable.card37;
            resourceIDS[38] = R.drawable.card38;
            resourceIDS[39] = R.drawable.card39;
            resourceIDS[40] = R.drawable.card40;
            resourceIDS[41] = R.drawable.card41;
            resourceIDS[42] = R.drawable.card42;
            resourceIDS[43] = R.drawable.card43;
            resourceIDS[44] = R.drawable.card44;
            resourceIDS[45] = R.drawable.card45;
            resourceIDS[46] = R.drawable.card46;
            resourceIDS[47] = R.drawable.card47;
            resourceIDS[48] = R.drawable.card48;
            resourceIDS[49] = R.drawable.card49;
            resourceIDS[50] = R.drawable.card50;
            resourceIDS[51] = R.drawable.card51;
        }


        public void clearPlayersHand() {
            playersCardsImages.clear();
        }
    }
}

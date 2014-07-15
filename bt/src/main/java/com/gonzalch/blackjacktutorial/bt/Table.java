package com.gonzalch.blackjacktutorial.bt;
import android.util.Log;
/**
 * Created by alex on 7/14/2014.
 */
public class Table {
    public static Table classAInstance = new Table();
    private static final String TAG = "TableClass";
    private Dealer dealer;
    private Player player;
    private Rules rules;
    private BlackjackGame game;
    private Deck deck;
    private boolean gameOver = false;

    public Table(){
        dealer = new Dealer();
        player = new Player();
        deck = new Deck();
        rules = new Rules();
        game = new BlackjackGame();
    }
    public void startGame()
    {
        deck.shuffle();

        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

    }

    public String getPlayerHand(){
        return player.getHandAsString();
    }

    public String getDealerHand(){
        return dealer.getHandAsString();
    }

    public boolean canPlayerDouble(){
        if(player.getCardCount() == 2){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean canPlayerSplit(){
        if(player.getCardCount() != 2){
            return false;
        }

        Hand pHand = player.getHand();
        Card c1 = pHand.getCard(0);
        Card c2 = pHand.getCard(1);

        if( c1.getValue() == c2.getValue() )
        {
            return true;
        }

        return false;
    }

    public void playerHit() {
        player.addCard(deck.dealCard());
        if( player.hasBusted() )
        {
            gameOver = true;
        }
    }

    public boolean gameOver()
    {
        return gameOver;
    }

    public void playerStands(){

    }

    public String getPlayersTotalAsString()
    {
        return player.getHandTotalAsString();
    }
}

package com.gonzalch.blackjacktutorial.bt;
import android.util.Log;
/**
 * Created by alex on 7/14/2014.
 *
 * Controls the state of the game.
 */

public class Table {
    public static Table classAInstance = new Table();
    private static final String TAG = "TableClass";
    private Dealer dealer;
    private Player player;
    private Rules rules;
    private BlackjackGame game;
    private Deck deck;
    private boolean playerBusted = false;
    private boolean dealerBusted = false;
    private boolean revealDealer = false;
    private boolean gameInProgress = false;

    /*
     * Default constuctor.
    */
    public Table(){
        dealer = new Dealer();
        player = new Player();
        deck = new Deck();
        rules = new Rules();
        game = new BlackjackGame();
    }
    /*
     *  Begin a game.  Init booleans and shuffle deck and deal two cards
     *  to each player.
     */
    public void startGame()
    {
        gameInProgress = true;
        playerBusted = false;
        dealerBusted = false;
        revealDealer = false;
        if(deck == null)
        {
            deck = new Deck();
        }
        deck.shuffle();
        if(rules == null)
        {
            rules = new Rules();
        }
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
    }

    /*
     *  Returns the players hand as a string
     */
    public String getPlayerHand(){
        return player.getHandAsString();
    }

    public boolean isGameInProgress()
    {
        return gameInProgress;
    }

    /*
     *  Returns the dealers hand as a sting.
     *  If the player has not played then only the
     *  first card is displayed to the player.
     */
    public String getDealerHand(){
        String hand = dealer.getHandAsString();
        Log.d(TAG, "Dealer hand: " + hand);

        if(revealDealer == false){
            return hand.substring(0,3);
        }
        return hand;
    }

    /*
     *  Determins if the player has not yet hit and able to double down.
     */
    public boolean canPlayerDouble(){
        if(player.getCardCount() == 2 & gameInProgress){
            return true;
        }
        else{
            return false;
        }
    }

    /*
     *  Method to determin if the player has two cards of the same value, allowing the player
     *  to split.
     */
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
    /*
     *  Add a card to players hand and check for a bust.
     */
    public void playerHit()
    {
        player.addCard(deck.dealCard());
        if(player.getHandTotal() > 21)
        {
            revealDealer = true;
            playerBusted = true;
            gameInProgress = false;
        }
    }

    public boolean playerBusted()
    {
        return playerBusted;
    }

    public boolean dealerBusted() { return dealerBusted; }

    /*
     *  Method called when player stands.  Dealer begins his turn.
     *  After method is complete game is over.
     */
    public void playerStands()
    {
        revealDealer = true;
        if(rules.dealerHitsOnSoft17 & dealer.getHandTotal() < 18) {
            while (dealer.getHandTotal() < 18) {
                dealer.addCard(deck.dealCard());
            }
        }
        else
        {
            if(dealer.getHandTotal() < 17)
            {
                while (dealer.getHandTotal() < 17) {
                    dealer.addCard(deck.dealCard());
                }
            }
        }
        if( dealer.getHandTotal() > 21 )
        {
            dealerBusted = true;
        }

        gameInProgress = false;
    }

    /*
     *  Returns the players total as a string
     */
    public String getPlayersTotalAsString()
    {
        return Integer.toString(player.getHandTotal());
    }


    /*
     *  Returns the dealers hand total as a string.
     *  If the player has not played then only the first
     *  card's value is displayed to the player.
     */
    public String getDealersTotalAsString()
    {
        if(revealDealer)
        {
            return  Integer.toString(dealer.getHandTotal());
        }
        Hand h = dealer.getHand();
        return Integer.toString(h.getCard(0).getValue());
    }





    /*
     *  Reset the state of the game.  Currently reshuffles
     *  the deck every time.
     */
    public void reset()
    {
        gameInProgress = false;
        if( player.getHand() != null) {player.removeHand();}
        if( dealer.getHand() != null) {dealer.removeHand();}
        if( deck != null) {deck = null;}
    }






/* ******************************************
 *Needed to communitate to the view.
 *********************************************/

    /*
     *  Player automatically wins on blackjack.
     */
    public boolean checkPlayerForBlackjack()
    {
        if( player.hasBlackjack() )
        {
            gameInProgress = false;
            return true;
        }
        return false;
    }

    /*
     *  If the dealer has a blackjack and the player does not then the game does not proceed.
    */
    public boolean checkDealerForBlackjack()
    {
        if( dealer.hasBlackjack() )
        {
            gameInProgress = false;
            revealDealer = true;
            return true;
        }
        return false;
    }

    /*
     * If the player has tied the dealer than we need to push.
     */
    public boolean checkForTie()
    {
        if(player.getHandTotal() == dealer.getHandTotal())
        {
            return true;
        }
        return false;
    }
    /*
     * Method to detect if a player has won.
     * Player must have a hand less than or equal to 21.
     * Players hand must be larger than dealers hand unless
     * dealers hand exceeds 21.
     */
    public boolean playerWon()
    {
        if(player.getHandTotal() <= 21)
        {
            if( dealer.getHandTotal() <= 21)
            {
                if( player.getHandTotal() > dealer.getHandTotal()  )
                {
                    return true;
                }
            }
            else
            {
                return true;
            }
        }
        return false;
    }

}
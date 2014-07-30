package com.gonzalch.blackjacktutorial.bt;
import android.util.Log;
/**
 * Created by alex on 7/14/2014.
 *
 * Controls the state of the game.
 */

public class Table {
    public static Table classAInstance = new Table();
    private static final String TAG = "Table Logic Class";
    private Dealer dealer;
    private Player player;
    private Rules rules;
    private int currentBetTotal = 0;

    private Deck deck;
    private boolean playerBusted = false;
    private boolean dealerBusted = false;
    private boolean revealDealer = false;
    private boolean gameInProgress = false;
    private boolean playerHasSplit = false;
    private boolean playerSplitStandOrBust = false;

    /*
     * Default constuctor.
    */
    public Table(){
        dealer = new Dealer();
        player = new Player();
        deck = new Deck();
        rules = new Rules();

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

        // Place players bet which subtracts it from the bankroll.
        playerBet();

        Card c = deck.dealCard();
        Log.d(TAG, "Player was dealt a:" + c.getValueAsString() + " of " + c.getSuitAsString() + " index: " + c.getIndex());
        player.addCard(c);
        c = deck.dealCard();
        Log.d(TAG, "Dealer was dealt a:" + c.getValueAsString() + " of " + c.getSuitAsString() + " index: " + c.getIndex());
        dealer.addCard(c);
        c = deck.dealCard();
        Log.d(TAG, "Player was dealt a:" + c.getValueAsString() + " of " + c.getSuitAsString() + " index: " + c.getIndex());
        player.addCard(c);
        c = deck.dealCard();
        Log.d(TAG, "Dealer was dealt a:" + c.getValueAsString() + " of " + c.getSuitAsString() + " index: " + c.getIndex());
        dealer.addCard(c);
    }

    /*
     *  Returns the players hand as a string
     */
    public Hand getPlayerHand(){
        return player.getHand();
    }
    public String getPlayerHandAsString(){
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
    public String getDealerHandAsString(){
        String hand = dealer.getHandAsString();
        Log.d(TAG, "Dealer hand: " + hand);

        if(revealDealer == false){
            return hand.substring(0,3);
        }
        return hand;
    }
    public Hand getDealerHand(){
        return dealer.getHand();
    }

    /*
     *  Determines if the player has not yet hit and able to double down.
     */
    public boolean canPlayerDouble(){
        if((player.getCardCount() == 2) && (player.getBankroll() >= currentBetTotal) && gameInProgress){
            Log.d(TAG, "player can double");
            return true;
        }
        else{
            Log.d(TAG, "Bankroll - " + String.valueOf(player.getBankroll()) + " bet - " + currentBetTotal);
            return false;
        }
    }

    /*
     *  Method to determine if the player has two cards of the same value, allowing the player
     *  to split.
     */
    public boolean canPlayerSplit(){
        if(player.getCardCount() != 2 | !gameInProgress){
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
        Card c = deck.dealCard();
        Log.d(TAG, "Player was dealt a:" + c.getValueAsString() + " of " + c.getSuitAsString() + " index: " + c.getIndex());


        if( player.getPlayersSplitHand() != null)
        {
            //if the player split and has stood or busted on the first hand.
            if( playerSplitStandOrBust ){
                player.addCardToSplit(c);
                if(player.getSplitHandTotal() > 21)
                {
                    revealDealer = true;
                    playerBusted = true;
                    gameInProgress = false;
                    currentBetTotal = 0;
                }
            }
            else{
                player.addCard(c);
                if(player.getHandTotal() > 21)
                {
                    revealDealer = false;
                    playerBusted = true;
                    gameInProgress = true;
                    currentBetTotal = 0;
                    playerSplitStandOrBust = true;
                }
            }
        }
        else{
            player.addCard(c);
            if(player.getHandTotal() > 21)
            {
                revealDealer = true;
                playerBusted = true;
                gameInProgress = false;
                currentBetTotal = 0;
            }
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
        if(!playerHasSplit | (playerHasSplit & playerSplitStandOrBust) )
        {

            revealDealer = true;
            if(rules.dealerHitsOnSoft17 & dealer.getHandTotal() < 18) {
                while (dealer.getHandTotal() < 18) {
                    Card c = deck.dealCard();
                    Log.d(TAG, "Dealer was dealt a:" + c.getValueAsString() + " of " + c.getSuitAsString() + " index: " + c.getIndex());
                    dealer.addCard(c);
                }
            }
            else
            {
                if(dealer.getHandTotal() < 17)
                {
                    while (dealer.getHandTotal() < 17) {
                        Card c = deck.dealCard();
                        Log.d(TAG, "Dealer was dealt a:" + c.getValueAsString() + " of " + c.getSuitAsString() + " index: " + c.getIndex());
                        dealer.addCard(c);
                    }
                }
            }
            if( dealer.getHandTotal() > 21 )
            {
                dealerBusted = true;
            }

            gameInProgress = false;
        }
        if( playerHasSplit ){
            playerSplitStandOrBust = true;

        }
    }


    /*
     *  Method called when player doubles down. Player hits one final time and then dealer begins his turn.
     *  After method is complete game is over.
     */
    public void playerDouble() {
        playerBet();
        currentBetTotal+=currentBetTotal;
        playerHit();
        playerStands();
    }

    /*
     *  Returns the players total as a string
     */
    public String getPlayersTotalAsString()
    {
        return Integer.toString(player.getHandTotal());
    }

    public String getPlayersSplitTotalAsString()
    {
        return Integer.toString(player.getSplitHandTotal());
    }

    public boolean getPlayersSplit()
    {
        return playerHasSplit;
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
        if (h.getCard(0) != null) {
            return Integer.toString(h.getCard(0).getValue());
        }
        else {
            return "0";
        }
    }


    /*
     *  Reset the state of the game.  Currently reshuffles
     *  the deck every time.
     */
    public void reset()
    {
    playerBusted = false;
        dealerBusted = false;
        revealDealer = false;
        gameInProgress = false;
        playerHasSplit = false;
        playerSplitStandOrBust = false;
        if( player.getHand() != null) {player.removeHand();}
        if( dealer.getHand() != null) {dealer.removeHand();}
        if( deck != null) {deck = null;}
    }

    public int getPlayersHandSize(){
        return player.getCardCount();
    }

    public int getPlayersSplitHandSize(){
        return player.getSplitCardCount();
    }

    public int getDealersHandSize(){
        return dealer.getCardCount();
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
            revealDealer = true;
            player.modifyBankroll(currentBetTotal*2, true);
            currentBetTotal = 0;
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
            currentBetTotal = 0;

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
            player.modifyBankroll(currentBetTotal, true);
            currentBetTotal = 0;
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
                    player.modifyBankroll(currentBetTotal*2, true);
                    currentBetTotal = 0;
                    return true;
                }
            }
            else
            {
                player.modifyBankroll(currentBetTotal*2, true);
                currentBetTotal = 0;
                return true;
            }
        }
        currentBetTotal = 0;
        return false;
    }

    public void playerSplit(){
        playerHasSplit = true;
        Card c1 = deck.dealCard();
        Card c2 = deck.dealCard();

        Log.d(TAG, "Player split hand 1 got: " + c1.getValueAsString() + " of " + c1.getSuitAsString() + " index: " + c1.getIndex());
        Log.d(TAG, "Player split hand 2 got: " + c2.getValueAsString() + " of " + c2.getSuitAsString() + " index: " + c2.getIndex());

        player.splitHand(c1, c2);

    }

    public boolean hasPlayerSplit(){ return playerHasSplit; }

    public Hand getPlayersSplitHand(){ return player.getPlayersSplitHand(); }

    public boolean getRevealDealer()
    { return revealDealer; }

    public float getPlayerBankroll() {
        return player.getBankroll();
    }

    public float getCurrentBetTotal() {
        return currentBetTotal;
    }

    public void increaseCurrentBetTotal(int bet) {
        currentBetTotal = currentBetTotal + bet;
    }

    public void playerBet() {
        player.modifyBankroll(currentBetTotal, false);
    }
}
package com.gonzalch.blackjacktutorial.bt;

/**
 * Created by alex on 7/14/2014.
 *
 * Class used to control the overall casino.  Not much will be used here but this is a good place
 * to store a table.
 */
public class House {
    private Rules houseRules;
    private Table table;

    public House(){
        houseRules = new Rules();
        table = new Table();
    }

    public void startGame(){

    }
}

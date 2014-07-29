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

    private Button mPlayButton;
    private Button mHitButton;
    private Button mStandButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayButton = (Button) findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BlackjackTableActivity.class);
                startActivityForResult(intent, RESULT_RULES);
            }
        });

        mHitButton = (Button) findViewById(R.id.hit_button);
        mHitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mStandButton = (Button) findViewById(R.id.stand_button);
        mStandButton.setOnClickListener(new View.OnClickListener() {
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
}

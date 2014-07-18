package com.gonzalch.blackjacktutorial.bt.activities;

import android.app.ActionBar;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Display;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Paint;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


/*
 *  Cards are of size 72 x 96 pixels.  Hidden dealer cards are 12 x 96 pixels
 */


import com.gonzalch.blackjacktutorial.bt.R;

public class BlackjackTableActivity extends ActionBarActivity {
    private static final String TAG = "Table View";

    ImageView playerCard0;
    ImageView playerCard1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack_table);

        

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

    private void getScreenDemensions()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenHeight = size.x;
        int screenWidth = size.y;

        Log.d(TAG, "Screen width: " + screenWidth);
        Log.d(TAG, "Screen height: " + screenHeight);
    }
}

package com.app.ashish.ujanlearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.app.ashish.constants.Constants;

/**
 * Created by ashis_000 on 2/6/2015.
 */
public class ActivitySwitchHandler extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);

        int selectedOption = getIntent().getExtras().getInt(Constants.SELECTED_INTENT);

        if(selectedOption == Constants.ENGLISH_CAPS_VALUE || selectedOption == Constants.ENGLISH_SMALL_VALUE) {
            Intent intent = new Intent(getApplicationContext(), EnglishLetterActivity.class);
            intent.putExtra(Constants.SELECTED_INTENT, selectedOption);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

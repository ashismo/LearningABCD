package com.app.ashish.ujanlearning;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.app.ToolbarActionBar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ashish.constants.Constants;
import com.app.ashish.settings.SettingActivity;
import com.app.ashish.util.DatabaseUtil;

import java.io.InputStream;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    private CheckBox soundEnabledCkBox = null;
    private boolean isSoundEnabled = true;
    private int selectedNumber = 10;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create private database which will be used to store user settings
        DatabaseUtil dbUtil = new DatabaseUtil(getApplicationContext());
        String editMode = dbUtil.getUserSettingsByParam(Constants.EDIT_MODE_COL);
        Toast.makeText(getApplicationContext(), "editMode " + editMode, Toast.LENGTH_LONG).show();

        addListenerOnSoundEnableCkBox();
        // Capital Letter
        RadioButton engCapitalLetter = (RadioButton)findViewById(R.id.radio_eng_caps);
        engCapitalLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EnglishLetterActivity.class);
                intent.putExtra(Constants.SELECTED_INTENT, Constants.ENGLISH_CAPS_VALUE);
                contrlSound(intent);
                startActivity(intent);

            }
        });

        // Small letter
        RadioButton engSmallLetter = (RadioButton)findViewById(R.id.radio_eng_small);
        engSmallLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EnglishLetterActivity.class);
                intent.putExtra(Constants.SELECTED_INTENT, Constants.ENGLISH_SMALL_VALUE);
                contrlSound(intent);
                startActivity(intent);

            }
        });

        // English number
        RadioButton engNumber = (RadioButton)findViewById(R.id.radio_eng_number);
        engNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EnglishLetterActivity.class);
                intent.putExtra(Constants.SELECTED_INTENT, Constants.ENGLISH_NUMBER_VALUE);
                onNumberLimitClicked(v);
                intent.putExtra(Constants.SELECTED_NUMBER_KEY, selectedNumber);
                contrlSound(intent);
                startActivity(intent);

            }
        });
    }

    public void onNumberLimitClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_num_10:
                if (checked) {
                    selectedNumber = Constants.SELECTED_NUM_VALUE_10;
                }
                break;
            case R.id.radio_num_20:
                if (checked) {
                    if (checked) {
                        selectedNumber = Constants.SELECTED_NUM_VALUE_20;
                    }
                }
                break;
            case R.id.radio_num_100:
                if (checked) {
                    selectedNumber = Constants.SELECTED_NUM_VALUE_100;
                }
                break;
        }
    }
    private void contrlSound(Intent intent) {
        addListenerOnSoundEnableCkBox();
        if(isSoundEnabled) {
            intent.putExtra(Constants.SOUND_ENABLE_KEY, Constants.SOUND_ENABLE_VALUE.Y);
        } else {
            intent.putExtra(Constants.SOUND_ENABLE_KEY, Constants.SOUND_ENABLE_VALUE.N);
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

        switch (item.getItemId()) {
            case R.id.action_about:
                Toast.makeText(getApplicationContext(),R.string.version, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), R.string.developed_by, Toast.LENGTH_LONG).show();
                return true;
            case R.id.user_settings:
                Intent userSettings=new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(userSettings);
                return true;
            default:
                return super.onContextItemSelected(item);
        }

//        return super.onOptionsItemSelected(item);
    }

    public void addListenerOnSoundEnableCkBox() {

        soundEnabledCkBox = (CheckBox) findViewById(R.id.isSoundEnable);

        soundEnabledCkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is soundEnabledCkBox checked?
                if (((CheckBox) v).isChecked()) {
                    isSoundEnabled = true;
                } else {
                    isSoundEnabled = false;
                }

            }
        });

    }
}

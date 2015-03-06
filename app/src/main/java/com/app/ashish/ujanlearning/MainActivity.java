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
import com.app.ashish.singleton.UserSettingsSingleton;
import com.app.ashish.util.DatabaseUtil;
import com.app.ashish.util.Utility;

import java.io.InputStream;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    private CheckBox soundEnabledCkBox = null;
    private boolean isSoundEnabled = true;
    private int selectedNumber = 10;

    @Override
    public void finish() {
        // Set edit mode to false while exiting from the app
        UserSettingsSingleton userSettings = UserSettingsSingleton.getUserSettings();
        DatabaseUtil dbUtil = new DatabaseUtil(userSettings.getContext());
        dbUtil.updateUserSettings(Constants.EDIT_MODE_COL, "false");
        super.finish();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Below line should not be moved from this place.
        UserSettingsSingleton userSettings = UserSettingsSingleton.getUserSettings();
        userSettings.setContext(getApplicationContext());
        userSettings.setNoOfTimeHomePageAccessed(userSettings.getNoOfTimeHomePageAccessed() + 1);
        new Utility().readUserSettings();

//        TextView editModeUserMsg = (TextView) findViewById(R.id.edit_mode_msg);
//        if(userSettings.isEditMode()) {
//            editModeUserMsg.setVisibility(View.VISIBLE);
//            editModeUserMsg.setText(R.string.change_edit_mode_from_settings);
//            editModeUserMsg.setTextColor(Color.RED);
//        } else {
//            editModeUserMsg.setText(R.string.setting_msg);
//            editModeUserMsg.setVisibility(View.VISIBLE);
//            editModeUserMsg.setTextColor(Color.BLUE);
//        }

        addListenerOnSoundEnableCkBox();
        // Capital Letter
//        RadioButton engCapitalLetter = (RadioButton)findViewById(R.id.radio_eng_caps);
        ImageView engCapitalLetter = (ImageView)findViewById(R.id.caps_abcd);
        engCapitalLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EnglishLetterActivity.class);
                intent.putExtra(Constants.SELECTED_INTENT, Constants.ENGLISH_CAPS_VALUE);
                UserSettingsSingleton.getUserSettings().setSelectedLearningOption(Constants.ENGLISH_CAPS_VALUE);
                contrlSound(intent);
                startActivity(intent);

            }
        });

        // Small letter
//        RadioButton engSmallLetter = (RadioButton)findViewById(R.id.radio_eng_small);
        ImageView engSmallLetter = (ImageView)findViewById(R.id.small_abcd);
        engSmallLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EnglishLetterActivity.class);
                intent.putExtra(Constants.SELECTED_INTENT, Constants.ENGLISH_SMALL_VALUE);
                UserSettingsSingleton.getUserSettings().setSelectedLearningOption(Constants.ENGLISH_SMALL_VALUE);
                contrlSound(intent);
                startActivity(intent);

            }
        });

        // English number
        ImageView engNumber = (ImageView)findViewById(R.id.num_123);
        final ImageView engNumber10 = (ImageView)findViewById(R.id.num_10);
        final ImageView engNumber20 = (ImageView)findViewById(R.id.num_20);
        final ImageView engNumber100 = (ImageView)findViewById(R.id.num_100);
        setNumbersVisible(engNumber10, engNumber20, engNumber100, View.INVISIBLE);
        engNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(engNumber10.getVisibility() == View.VISIBLE) {
                    setNumbersVisible(engNumber10, engNumber20, engNumber100, View.INVISIBLE);
                } else {
                    setNumbersVisible(engNumber10, engNumber20, engNumber100, View.VISIBLE);
                }
            }
        });

        engNumber10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRespectiveNumber(v, Constants.SELECTED_NUM_VALUE_10);

            }
        });


        engNumber20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRespectiveNumber(v, Constants.SELECTED_NUM_VALUE_20);

            }
        });

        engNumber100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRespectiveNumber(v, Constants.SELECTED_NUM_VALUE_100);

            }
        });
    }

    private void setNumbersVisible(ImageView engNumber10, ImageView engNumber20, ImageView engNumber100, int isVisible) {
        engNumber10.setVisibility(isVisible);
        engNumber20.setVisibility(isVisible);
        engNumber100.setVisibility(isVisible);
    }

    private void callRespectiveNumber(View v, int selectedNumber) {
        Intent intent = new Intent(getApplicationContext(), EnglishLetterActivity.class);
        intent.putExtra(Constants.SELECTED_INTENT, Constants.ENGLISH_NUMBER_VALUE);
        UserSettingsSingleton.getUserSettings().setSelectedLearningOption(Constants.ENGLISH_NUMBER_VALUE);
//        onNumberLimitClicked(v);
        intent.putExtra(Constants.SELECTED_NUMBER_KEY, selectedNumber);
        contrlSound(intent);
        startActivity(intent);
    }

//    public void onNumberLimitClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.radio_num_10:
//                if (checked) {
//                    selectedNumber = Constants.SELECTED_NUM_VALUE_10;
//                }
//                break;
//            case R.id.radio_num_20:
//                if (checked) {
//                    if (checked) {
//                        selectedNumber = Constants.SELECTED_NUM_VALUE_20;
//                    }
//                }
//                break;
//            case R.id.radio_num_100:
//                if (checked) {
//                    selectedNumber = Constants.SELECTED_NUM_VALUE_100;
//                }
//                break;
//        }
//    }
    private void contrlSound(Intent intent) {
        final UserSettingsSingleton userSettings = UserSettingsSingleton.getUserSettings();
        final DatabaseUtil dbUtil = new DatabaseUtil(userSettings.getContext());
        final String isSoundOn = dbUtil.getUserSettingsByParam(Constants.SOUND_ON);

        if(!"true".equals(isSoundOn)) {
            intent.putExtra(Constants.SOUND_ENABLE_KEY, Constants.SOUND_ENABLE_VALUE.N);
        } else {
            intent.putExtra(Constants.SOUND_ENABLE_KEY, Constants.SOUND_ENABLE_VALUE.Y);
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

        final ImageView soundEnabled = (ImageView) findViewById(R.id.sound);
        final UserSettingsSingleton userSettings = UserSettingsSingleton.getUserSettings();
        final DatabaseUtil dbUtil = new DatabaseUtil(userSettings.getContext());
        final String isSoundOn = dbUtil.getUserSettingsByParam(Constants.SOUND_ON);

        if(!"true".equals(isSoundOn)) {
            soundEnabled.setImageResource(R.drawable.sound_off);
        } else {
            soundEnabled.setImageResource(R.drawable.sound_on);
        }

        soundEnabled.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is soundEnabledCkBox checked?
//                UserSettingsSingleton userSettings = UserSettingsSingleton.getUserSettings();
//                DatabaseUtil dbUtil = new DatabaseUtil(userSettings.getContext());
                String isSoundOn = dbUtil.getUserSettingsByParam(Constants.SOUND_ON);
                if(isSoundOn == null) {
                    dbUtil.updateUserSettings(Constants.SOUND_ON, "false");
                    soundEnabled.setImageResource(R.drawable.sound_off);
                    isSoundEnabled = false;
                } else {
                    if("true".equals(isSoundOn)) {
                        dbUtil.updateUserSettings(Constants.SOUND_ON, "false");
                        soundEnabled.setImageResource(R.drawable.sound_off);
                        isSoundEnabled = false;
                    } else {
                        dbUtil.updateUserSettings(Constants.SOUND_ON, "true");
                        soundEnabled.setImageResource(R.drawable.sound_on);
                        isSoundEnabled = true;
                    }
                }
            }
        });

    }
}

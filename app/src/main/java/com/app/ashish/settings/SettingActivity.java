package com.app.ashish.settings;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.app.ashish.constants.Constants;
import com.app.ashish.ujanlearning.R;
import com.app.ashish.util.DatabaseUtil;

/**
 * Created by ashis_000 on 2/14/2015.
 */
public class SettingActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settngs);
        final DatabaseUtil dbUtil = new DatabaseUtil(getApplicationContext());

        // On selection of edit mode database to be updated
        Switch editModeSwitch = (Switch)findViewById(R.id.edit_mode_switch);

        // Set edit mode from db value
        String editMode = dbUtil.getUserSettingsByParam(Constants.EDIT_MODE_COL);
        if("true".equalsIgnoreCase(editMode)) {
            editModeSwitch.setChecked(true);
        } else {
            editModeSwitch.setChecked(false);
        }

        editModeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = ((Switch) v).isChecked();
                dbUtil.updateUserSettings(Constants.EDIT_MODE_COL, String.valueOf(isChecked));
            }
        });

    }
}

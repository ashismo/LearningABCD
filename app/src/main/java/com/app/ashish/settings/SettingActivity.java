package com.app.ashish.settings;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.app.ashish.constants.Constants;
import com.app.ashish.singleton.UserSettingsSingleton;
import com.app.ashish.ujanlearning.R;
import com.app.ashish.util.DatabaseUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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
                UserSettingsSingleton.getUserSettings().setEditMode(isChecked);
            }
        });



        // Reset all customized images
        final Switch resetToSwitch = (Switch)findViewById(R.id.reset_to_default_switch);
        resetToSwitch.setChecked(false);
        resetToSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a dialog to confirm
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingActivity.this);
                alertDialogBuilder.setMessage(R.string.confirm_default);
                // Get the layout inflater
                LayoutInflater inflater =  (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.activity_dialog_confirmation, null);
                alertDialogBuilder.setView(mView);

                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // Delete all customized images from external directory
                                UserSettingsSingleton userSettings = UserSettingsSingleton.getUserSettings();
                                String imagePathInExternalDir = userSettings.getAppDirPath();
                                File folder = new File(imagePathInExternalDir);
                                if (folder.exists() && folder.isDirectory()) {
                                    // Delete all images name starting with english_
                                    int count = 0;
                                    for(File f : folder.listFiles()) {
                                        if(f != null && f.exists() && f.getName().contains("english_")) {
                                            f.delete();
                                            count++;
                                        }
                                        if(count > 1) {
                                            Toast.makeText(getApplicationContext(),count+"",Toast.LENGTH_LONG).show();
                                            break;
                                        }
                                    }

                                }
                                // Reset the switch back to false
                                resetToSwitch.setChecked(false);
                            }
                        });
                alertDialogBuilder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                resetToSwitch.setChecked(false);
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

    }
}

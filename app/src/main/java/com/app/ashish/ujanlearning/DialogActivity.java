package com.app.ashish.ujanlearning;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.app.ashish.singleton.UserSettingsSingleton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by ashis_000 on 2/15/2015.
 */
public class DialogActivity extends Activity {
    private final int NO_OF_SELECTED_IMAGE = 1;
    private Bitmap selectedImageBM = null;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_user_input);

        // Image picker
        ImageButton imageButton = (ImageButton)findViewById(R.id.select_image);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pick photo from gallery
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent,NO_OF_SELECTED_IMAGE);
                UserSettingsSingleton userSettings = UserSettingsSingleton.getUserSettings();
                userSettings.setImageName("english_" + userSettings.getSelectedText().toLowerCase().trim() + ".jpg");
            }
        });
    }


}

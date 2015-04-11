package com.app.ashish.ujanlearning;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.ashish.singleton.UserSettingsSingleton;
import com.app.ashish.util.Utility;

import java.io.InputStream;

/**
 * Created by ashis_000 on 3/7/2015.
 */
public class ColorGridActivity extends ActionBarActivity {
    private ImageView colorIv = null;
    private GridView gridView = null;
    private LinearLayout colorLinerLayout = null;
    private TextView imageText = null;
    private TextToSpeech text2Speech = null;

//    @Override
//    public void finish() {
//        Utility.deinitText2Speech();
//        super.finish();
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_layout);
        colorIv = (ImageView) findViewById(R.id.colorLargeView);
        imageText = (TextView) findViewById(R.id.colorText);

        gridView = (GridView) findViewById(R.id.color_grid);
        gridView.setAdapter(new ColorGridAdapter(getApplicationContext()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                colorLinerLayout.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.INVISIBLE);

                try {
                    String fileName = UserSettingsSingleton.getUserSettings().getColorFilesList().get(position);
                    String colorName = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.lastIndexOf("."));
                    if(colorName != null && !colorName.trim().equals("")) {
                        imageText.setText(colorName.toUpperCase());
                        imageText.setTextColor(Color.BLACK);
                        imageText.setTextSize(80);
                        imageText.setGravity(Gravity.CENTER);
                        text2Speech = Utility.getSpechInitialized(getApplicationContext());
                        Utility.speak(colorName);
                    }
                    InputStream si1 = getApplicationContext().getAssets().open(fileName);
                    Bitmap image = BitmapFactory.decodeStream(si1);
                    Bitmap scaledImage = Bitmap.createScaledBitmap(image, colorIv.getWidth(), colorIv.getHeight(), true);
                    colorIv.setImageBitmap(scaledImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                colorIv.setImageResource(parent.getId());
            }
        });

        colorLinerLayout = (LinearLayout) findViewById(R.id.colorLayout);
        colorLinerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text2Speech == null || !text2Speech.isSpeaking()) {
                    colorLinerLayout.setVisibility(View.INVISIBLE);
                    gridView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}

package com.app.ashish.ujanlearning;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.VideoView;

import com.app.ashish.constants.Constants;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;


public class EnglishLetterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);

        int selectedOption = getIntent().getExtras().getInt(Constants.SELECTED_INTENT);
        englishLetters(selectedOption);
    }


    private void englishLetters(int selectedOption) {
        String alphabet[][] = null;

        if(selectedOption == Constants.ENGLISH_SMALL_VALUE) {
            String alphabet2[][] = {{"a","b","c","d"}, {"e","f","g","h"},
                {"i","j","k","l"}, {"m","n","o","p"},
                {"q","r","s","t"}, {"u","v","w","x"},
                {"","y","z",""}};
            alphabet = alphabet2;
        } else if(selectedOption == Constants.ENGLISH_CAPS_VALUE) {
            String alphabet1[][] = {{"A","B","C","D"}, {"E","F","G","H"},
                    {"I","J","K","L"}, {"M","N","O","P"},
                    {"Q","R","S","T"}, {"U","V","W","X"},
                    {"","Y","Z",""}};
            alphabet = alphabet1;
        }

        TableLayout tableLayout = (TableLayout)findViewById(R.id.alphabet);
        // Hide image view
        ImageView imageView = (ImageView)findViewById(R.id.imageView_grid);
        imageView.setVisibility(View.INVISIBLE);

        // Hide video view
        final VideoView videoview = (VideoView) findViewById(R.id.videoView_grid);
        videoview.setVisibility(View.INVISIBLE);

        for(int i = 0 ; i < alphabet.length; i++) {
            TableRow row = new TableRow(this);
            row.setBackgroundColor(Color.GRAY);
            row.setMinimumHeight(70);
            for(int j = 0; j < alphabet[i].length; j++) {
                final TextView textView = new TextView(this);

                textView.setText(alphabet[i][j]);

                //textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setTextSize(60);
                if((i+j) % 2 == 0) {
                    textView.setBackgroundColor(Color.WHITE);
                }
                textView.setGravity(Gravity.CENTER);

                textView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if(!videoview.isPlaying()) {
                            videoview.setVisibility(View.INVISIBLE);
                        }
                        ImageView imageView = (ImageView)findViewById(R.id.imageView_grid);
//                        Toast.makeText(getApplicationContext(),textView.getText() + "- " + path ,Toast.LENGTH_SHORT).show();
                        try {
                            String imgPath = "english_" + textView.getText().toString().toLowerCase() + ".jpg";
                                InputStream si1 = getAssets().open(imgPath);
                                Bitmap image = BitmapFactory.decodeStream(si1);
                                Bitmap scaledImage = Bitmap.createScaledBitmap(image, imageView.getWidth(), imageView.getHeight(), true);
                                imageView.setImageBitmap(scaledImage);
                                //Add animation to the image
                                Animation mAnimationTopLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_top_left);
                                Animation mAnimationBottomRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_bottom_right);

                                imageView.setVisibility(View.VISIBLE);
                                Random r = new Random();
                                int random = r.nextInt(80 - 65) + 65;
                                if (random % 2 == 0) {
                                    imageView.startAnimation(mAnimationTopLeft);
                                } else {
                                    imageView.startAnimation(mAnimationBottomRight);
                                }
                        } catch(Exception e) {
                            Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.english_f);
                            VideoView videoview = (VideoView) findViewById(R.id.videoView_grid);
                            videoview.setVideoURI(uri);
                            videoview.setVisibility(View.VISIBLE);
                            videoview.start();
                        }
//                        imageView.setImageResource(R.drawable.alphabet_a);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ImageView imageView = (ImageView)findViewById(R.id.imageView_grid);
                                imageView.setVisibility(View.INVISIBLE);
                            }
                        });

                        videoview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                VideoView videoview = (VideoView) findViewById(R.id.videoView_grid);
                                videoview.pause();
                                videoview.setVisibility(View.INVISIBLE);
                                RelativeLayout relative = (RelativeLayout) findViewById(R.id.imageView_grid);
                                relative.setBackgroundResource(0);

                            }
                        });
                    }
                });

                row.addView(textView);
            }
            tableLayout.addView(row);
        }
    }


}

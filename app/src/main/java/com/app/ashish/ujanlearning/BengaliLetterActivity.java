package com.app.ashish.ujanlearning;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.app.ashish.constants.Constants;
import com.app.ashish.util.Utility;

import java.io.InputStream;
import java.util.Locale;
import java.util.Random;


public class BengaliLetterActivity extends ActionBarActivity {
    TextToSpeech test2Speech = null;
    //Add animation to the image
    Animation mAnimationTopLeft = null;
    Animation mAnimationBottomRight = null;
    Animation mAnimationTopRight = null;
    Animation mAnimationBottomLeft = null;

    @Override
    public void finish(){
        deinitText2Speech();
        super.finish();
    }

    private void deinitText2Speech() {
        if(test2Speech !=null){
            test2Speech.stop();
            test2Speech.shutdown();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add animation to the english page
//        RelativeLayout englishAlphabetLayout = (RelativeLayout) findViewById(R.id.english_relative_layout);
//        englishAlphabetLayout.setAnimation(mAnimationBottomRight);
        setContentView(R.layout.activity_english);


        int selectedOption = getIntent().getExtras().getInt(Constants.SELECTED_INTENT);
        Constants.SOUND_ENABLE_VALUE isSoundEnable = (Constants.SOUND_ENABLE_VALUE)getIntent().getExtras().get(Constants.SOUND_ENABLE_KEY);
        englishLetters(selectedOption);

        Utility.initAlphabetMap();

        // Initialize text to speech object
        if(isSoundEnable.equals(Constants.SOUND_ENABLE_VALUE.Y)) {
            test2Speech = new TextToSpeech(getApplicationContext(),
                    new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                test2Speech.setLanguage(Locale.UK);
                            }
                        }
                    });
        }
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
        // Hide the top text view
        final TextView alphabetText = (TextView) findViewById(R.id.imageView_text);
        alphabetText.setVisibility(View.INVISIBLE);
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

                        // If video is open but not playing then close it
                        if(!videoview.isPlaying() && (videoview.getVisibility() == View.VISIBLE)) {
                            videoview.setVisibility(View.INVISIBLE);
                            return;
                        }
                        String audioPath = "";
                        ImageView imageView = (ImageView)findViewById(R.id.imageView_grid);

                        try {
                                String imgPath = "english_" + textView.getText().toString().toLowerCase() + ".jpg";
                                InputStream si1 = getAssets().open(imgPath);
                                Bitmap image = BitmapFactory.decodeStream(si1);
                                Bitmap scaledImage = Bitmap.createScaledBitmap(image, imageView.getWidth(), (int)(imageView.getHeight()*.7), true);
                                imageView.setImageBitmap(scaledImage);

                            // Set the alphabets at the top
//                            String alphabet2Text = Utility.getTextByAlphabet(textView.getText().toString());
                            alphabetText.setText(textView.getText());
                            alphabetText.setTextSize(100);
                            alphabetText.setBackgroundColor(Color.WHITE);
                            alphabetText.setTextColor(Color.RED);
                            alphabetText.setGravity(Gravity.CENTER);
                            alphabetText.setVisibility(View.VISIBLE);
                            imageView.setVisibility(View.VISIBLE);

                            //Add animation to the image
                            mAnimationTopLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_top_left);
                            mAnimationBottomRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_bottom_right);
                            mAnimationTopRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_top_right);
                            mAnimationBottomLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_bottom_left);
                                Random r = new Random();
                                int random = r.nextInt(80 - 65) + 65;
                                if (random % 4 == 0) {
                                    imageView.startAnimation(mAnimationTopLeft);
                                } else if (random % 4 == 1) {
                                    imageView.startAnimation(mAnimationTopRight);
                                } else if (random % 4 == 2) {
                                    imageView.startAnimation(mAnimationBottomLeft);
                                } else if (random % 4 == 3) {
                                    imageView.startAnimation(mAnimationBottomRight);
                                }
                            String text2Speech = Utility.getTextByAlphabet(textView.getText().toString());
                            if(test2Speech != null) {
                                test2Speech.setSpeechRate(0.6f);
                                test2Speech.speak(text2Speech, TextToSpeech.QUEUE_FLUSH, null);
                            } else {
                                Toast.makeText(getApplicationContext(), text2Speech.substring(5).toUpperCase(), Toast.LENGTH_LONG).show();
                            }

                            // Play sound
//                            audioPath = "english_" + textView.getText().toString().toLowerCase() + ".mp3";
//                            Uri uri = Uri.parse(audioPath);
//                            final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), uri);
//                            mp.start();
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // If text to speech conversion is stooped then close the image
                                    closeImageAndText(alphabetText);
                                }
                            });

                            alphabetText.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // If text to speech conversion is stooped then close the image
                                    closeImageAndText(alphabetText);
                                }
                            });
                        } catch(Exception e) {
                            if(textView.getText() != null && !"".equals(textView.getText().toString().trim())) {
                                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.english_f);
                                VideoView videoview = (VideoView) findViewById(R.id.videoView_grid);
                                videoview.setVideoURI(uri);
                                videoview.setVisibility(View.VISIBLE);
                                videoview.start();
                            }
                        }
                        videoview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                VideoView videoview = (VideoView) findViewById(R.id.videoView_grid);
                                videoview.pause();
                                videoview.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                });

                row.addView(textView);
            }
            tableLayout.addView(row);
        }
    }

    private void closeImageAndText(TextView alphabetText) {
        if (test2Speech == null || !test2Speech.isSpeaking()) {
            ImageView imageView = (ImageView) findViewById(R.id.imageView_grid);
            imageView.setVisibility(View.INVISIBLE);
            alphabetText.setVisibility(View.INVISIBLE);
        }
    }


}

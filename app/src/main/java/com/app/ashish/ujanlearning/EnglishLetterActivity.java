package com.app.ashish.ujanlearning;

import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.app.ashish.constants.Constants;
import com.app.ashish.singleton.UserSettingsSingleton;
import com.app.ashish.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Locale;
import java.util.Random;


public class EnglishLetterActivity extends ActionBarActivity {
    private TextToSpeech test2Speech = null;
    //Add animation to the image
    private Animation mAnimationTopLeft = null;
    private Animation mAnimationBottomRight = null;
    private Animation mAnimationTopRight = null;
    private Animation mAnimationBottomLeft = null;
    private final int numberIntentSelected = 3;
    private boolean isAllNumberSelected = false;
    private boolean isEditMode = false;
    private final int NO_OF_SELECTED_IMAGE = 1;


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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Set the navigation up to the main page
        setContentView(R.layout.activity_english);
        isEditMode = UserSettingsSingleton.getUserSettings().isEditMode();

        int selectedIntent = getIntent().getExtras().getInt(Constants.SELECTED_INTENT);
        int selectedNumberLimit = getIntent().getExtras().getInt(Constants.SELECTED_NUMBER_KEY);
        Constants.SOUND_ENABLE_VALUE isSoundEnable = (Constants.SOUND_ENABLE_VALUE)getIntent().getExtras().get(Constants.SOUND_ENABLE_KEY);
        englishLetters(selectedIntent, selectedNumberLimit);

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


    private void englishLetters(int selectedIntent, int selectedNumberLimit) {
        String alphabet[][] = null;

        if(selectedIntent == Constants.ENGLISH_CAPS_VALUE) {
            String alphabet1[][] = {{"A","B","C","D"}, {"E","F","G","H"},
                    {"I","J","K","L"}, {"M","N","O","P"},
                    {"Q","R","S","T"}, {"U","V","W","X"},
                    {"","Y","Z",""}};
            alphabet = alphabet1;
            setTitle(R.string.english_caps);
        } else if(selectedIntent == Constants.ENGLISH_SMALL_VALUE) {
            String alphabet2[][] = {{"a","b","c","d"}, {"e","f","g","h"},
                {"i","j","k","l"}, {"m","n","o","p"},
                {"q","r","s","t"}, {"u","v","w","x"},
                {"","y","z",""}};
            alphabet = alphabet2;
            setTitle(R.string.english_small);
        } else if(selectedIntent == Constants.ENGLISH_NUMBER_VALUE) {
            if(selectedNumberLimit == Constants.SELECTED_NUM_VALUE_10 || selectedNumberLimit == 0) {
//                String number10[][] = {{"", " 1 ", "", " 2 ", ""}, {" 3 ", "", " 4 ", "", " 5 "},
//                        {"6", "", "7", "", "8"}, {"", "9", "", "10", ""},
//                };
                String number10[][] = {{" 1 ", " 2 "}, {" 3 ", " 4 "},
                        {"5", "6"}, {"7", "8"},{"9", "10"}
                };
                alphabet = number10;
            } else if(selectedNumberLimit == Constants.SELECTED_NUM_VALUE_20) {
//                    String number20[][] = {{"", " 1 ", "", " 2 ", ""}, {" 3 ", "", " 4 ", "", " 5 "},
//                            {"6", "", "7", "", "8"}, {"", "9", "", "10", ""},
//                            {"", "11", "", "12", ""}, {"13", "", "14", "", "15"},
//                            {"16", "", "17", "", "18"}, {"", "19", "", "20", ""}
//                    };

                        String number20[][] = {{" 1 ", " 2 ", " 3 ", " 4 "}, {" 5 ", " 6 ", " 7 ", " 8 "},
                                {"9", "10", "11", "12"}, {"13", "14", "15", "16"},
                                {"17", "18", "19", "20"}
                        };
                    alphabet = number20;
            } else if(selectedNumberLimit == Constants.SELECTED_NUM_VALUE_100) {
                int count = 1;
                String number100[][] = new String[20][5];
                for(int i = 0; i < number100.length; i++) {
                    for(int j = 0; j < number100[i].length; j++) {
                        if(i != 0) {
                            number100[i][j] = "" + count++;
                        } else {
                            number100[i][j] = " " + count++ + " ";
                        }
                    }
                }
                alphabet = number100;
                isAllNumberSelected = true;
            }
            setTitle(R.string.english_number);
        }

        TableLayout tableLayout = (TableLayout)findViewById(R.id.alphabet);
        // Hide the top text view
        final TextView alphabetText = (TextView) findViewById(R.id.imageView_text);
        final TextView alphabetTextDesc = (TextView) findViewById(R.id.imageView_text_desc);
        alphabetText.setVisibility(View.INVISIBLE);
        alphabetTextDesc.setVisibility(View.INVISIBLE);
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
                    public void onClick(View v) {
                        if (!isEditMode) {
                            // If video is open but not playing then close it
                            if (!videoview.isPlaying() && (videoview.getVisibility() == View.VISIBLE)) {
                                videoview.setVisibility(View.INVISIBLE);
                                return;
                            }
                            String audioPath = "";
                            ImageView imageView = (ImageView) findViewById(R.id.imageView_grid);

                            try {
                                // If selected intent is Number and displaying more than 20 numbers then do not display image
                                String imgPath = "";
                                if (!isAllNumberSelected || (isAllNumberSelected && Integer.parseInt(textView.getText().toString().trim()) <= 20)) {
                                    imgPath = "english_" + textView.getText().toString().toLowerCase().trim() + ".jpg";
                                } else {
                                    imgPath = "english_100.jpg";
                                }
                                InputStream si1 = getAssets().open(imgPath);
                                String imagePathInExternalDir = UserSettingsSingleton.getUserSettings().getAppDirPath()  + "/" + imgPath;
                                File file = new File(imagePathInExternalDir);
                                if(file.exists()) {
                                    si1 = new FileInputStream(file);
                                }
                                Bitmap image = BitmapFactory.decodeStream(si1);
                                Bitmap scaledImage = Bitmap.createScaledBitmap(image, imageView.getWidth(), (int) (imageView.getHeight() * .7), true);
                                imageView.setImageBitmap(scaledImage);

                                // Set the alphabets at the top
//                            String alphabet2Text = Utility.getTextByAlphabet(textView.getText().toString());
                                alphabetText.setText(textView.getText());
                                alphabetText.setTextSize(80);
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
                                if (test2Speech != null) {
                                    test2Speech.setSpeechRate(0.6f);
                                    test2Speech.speak(text2Speech, TextToSpeech.QUEUE_FLUSH, null);
                                }
                            /*else {
                                if(numberIntentSelected == Constants.ENGLISH_NUMBER_VALUE) {
                                    Toast.makeText(getApplicationContext(), text2Speech, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), text2Speech.substring(5).toUpperCase(), Toast.LENGTH_LONG).show();
                                }
                            }*/

                                if (numberIntentSelected == Constants.ENGLISH_NUMBER_VALUE) {
                                    // Set description
                                    alphabetTextDesc.setText(Utility.getTextByAlphabet(textView.getText().toString()));
                                } else {
                                    // Set description
                                    alphabetTextDesc.setText(textView.getText().toString().substring(5));
                                }
                                alphabetTextDesc.setTextSize(50);
                                alphabetTextDesc.setBackgroundColor(Color.WHITE);
                                alphabetTextDesc.setTextColor(Color.BLUE);
                                alphabetTextDesc.setGravity(Gravity.CENTER);
                                alphabetTextDesc.setVisibility(View.VISIBLE);

                                // Play sound
//                            audioPath = "english_" + textView.getText().toString().toLowerCase() + ".mp3";
//                            Uri uri = Uri.parse(audioPath);
//                            final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), uri);
//                            mp.start();
                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // If text to speech conversion is stooped then close the image
                                        closeImageAndText(alphabetText, alphabetTextDesc);
                                    }
                                });

                                alphabetText.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // If text to speech conversion is stooped then close the image
                                        closeImageAndText(alphabetText, alphabetTextDesc);
                                    }
                                });
                                alphabetTextDesc.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // If text to speech conversion is stooped then close the image
                                        closeImageAndText(alphabetText, alphabetTextDesc);
                                    }
                                });
                            } catch (Exception e) {
                                try {
                                    if (textView.getText() != null && !"".equals(textView.getText().toString().trim()) &&
                                            Integer.parseInt(textView.getText().toString()) < Constants.SELECTED_NUM_VALUE_20) {
                                        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.english_f);
                                        VideoView videoview = (VideoView) findViewById(R.id.videoView_grid);
                                        videoview.setVideoURI(uri);
                                        videoview.setVisibility(View.VISIBLE);
                                        videoview.start();
                                    }
                                } catch (NumberFormatException nfe) {
                                } catch (Exception e1) {
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
                        } else {
                            // Pick photo from gallery
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent.setType("image/*");
                            startActivityForResult(photoPickerIntent,NO_OF_SELECTED_IMAGE);
                            UserSettingsSingleton.getUserSettings().setImageName("english_" + textView.getText().toString().toLowerCase().trim() + ".jpg");
                        }
                    }
                });

                row.addView(textView);
            }
            tableLayout.addView(row);
        }
    }

    private void closeImageAndText(TextView alphabetText, TextView alphabetTextDesc) {
        if (test2Speech == null || !test2Speech.isSpeaking()) {
            ImageView imageView = (ImageView) findViewById(R.id.imageView_grid);
            imageView.setVisibility(View.INVISIBLE);
            alphabetText.setVisibility(View.INVISIBLE);
            alphabetTextDesc.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        try {
            switch (requestCode) {
                case NO_OF_SELECTED_IMAGE:
                    if (resultCode == RESULT_OK) {
                        Uri selectedImage = imageReturnedIntent.getData();
                        InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap selectedImageBM = BitmapFactory.decodeStream(imageStream);

                        UserSettingsSingleton userSettings = UserSettingsSingleton.getUserSettings();
                        File file = new File(userSettings.getAppDirPath(), userSettings.getImageName());
                        FileOutputStream fOut = new FileOutputStream(file);

                        selectedImageBM.compress(Bitmap.CompressFormat.JPEG, 20, fOut);
                        fOut.flush();
                        fOut.close();
                    }
            }
        } catch (Exception e) {

        }
    }
}

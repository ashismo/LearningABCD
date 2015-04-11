package com.app.ashish.ujanlearning;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ashish.constants.Constants;
import com.app.ashish.singleton.UserSettingsSingleton;
import com.app.ashish.util.DatabaseUtil;
import com.app.ashish.util.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by ashis_000 on 2/15/2015.
 */
public class DialogActivity extends ActionBarActivity {
    private final int NO_OF_SELECTED_IMAGE = 1;
    private Bitmap selectedImageBM = null;
    private AlertDialog alertDialog = null;
    private Bitmap scaledImage = null;
    private boolean isImgDescBlank = false;
    private Context context = null;
    ActionBarActivity activity = null;

    public DialogActivity(){
        super();
    }
    public DialogActivity(Context context, ActionBarActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_user_input);

    }

    public void openDialog(final View view){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(R.string.dialog_desc);
        // Get the layout inflater
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.activity_dialog_user_input, null);
        alertDialogBuilder.setView(mView);

        // Pickup current image and give option to the user to change

        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Validation: Description should not be empty
                        EditText imageDesc = (alertDialog != null) ? (EditText)alertDialog.findViewById(R.id.image_desc) : null;
                        if(imageDesc == null || imageDesc.getText().toString().trim().equals("")) {
                            isImgDescBlank = true;
//                            imageDesc.setHintTextColor(Color.RED);
//                            imageDesc.setHint("Description can not be blank");
//                            alertDialog.show();
//                            alertDialog = alertDialogBuilder.create();
//                            alertDialog.show();
                        } else {
                            isImgDescBlank = false;
                            saveImageAndDesc();
                        }
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserSettingsSingleton userSettings = UserSettingsSingleton.getUserSettings();
                        //Setting the customized image reference to null
                        userSettings.setSelectedImageBM(null);
                    }
                });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

//        Button ok = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
//        if(ok != null) {
//            ok.setBackgroundDrawable(getResources().getDrawable(R.drawable.ok));
//        }
//        Button cancel = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
//        if(cancel != null) {
//            cancel.setBackgroundDrawable(getResources().getDrawable(R.drawable.cancel));
//        }
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // Validation: Description should not be empty
                        EditText imageDesc = (alertDialog != null) ? (EditText)alertDialog.findViewById(R.id.image_desc) : null;
                        if(imageDesc == null || imageDesc.getText().toString().trim().equals("")) {
                            isImgDescBlank = true;
                            imageDesc.setHintTextColor(Color.RED);
                            imageDesc.setHint("Description can not be blank");
                        } else {
                            isImgDescBlank = false;
                            saveImageAndDesc();
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });

        UserSettingsSingleton.getUserSettings().setAlertDialog(alertDialog);
        ImageButton imageButton = (ImageButton)alertDialog.findViewById(R.id.select_image);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhotoFromGallery();

            }
        });

        // Set default Image
        ImageButton defaultImgBtn = (ImageButton)alertDialog.findViewById(R.id.set_default_Image);
        defaultImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button rotateBtn = (Button)alertDialog.findViewById(R.id.rotate_button);
                rotateBtn.setVisibility(View.INVISIBLE);
                loadDefaultImageAndDesc();

            }
        });

        // Rotate image
        Button rotateBtn = (Button)alertDialog.findViewById(R.id.rotate_button);
        rotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSettingsSingleton userSettings = UserSettingsSingleton.getUserSettings();
                Bitmap selectedImageBM = userSettings.getSelectedImageBM();
                // Get customized image bit map
                if(selectedImageBM == null) {
                    ImageView imageView = (ImageView) alertDialog.findViewById(R.id.selected_image);
                    selectedImageBM = scaledImage; //imageView.getDrawingCache();
                    userSettings.setSelectedImageBM(selectedImageBM);
                    userSettings.setNewImageSelected(true);
                }
                if(selectedImageBM != null) {
                    ImageView imageView = (ImageView) userSettings.getAlertDialog().findViewById(R.id.selected_image);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);

                    selectedImageBM = Bitmap.createBitmap(userSettings.getSelectedImageBM(), 0, 0, selectedImageBM.getWidth(), selectedImageBM.getHeight(), matrix, true);
                    imageView.setImageBitmap(selectedImageBM);
                    userSettings.setSelectedImageBM(selectedImageBM);
                }
            }
        });
    }

    private void saveImageAndDesc() {
        try {
            UserSettingsSingleton userSettings = UserSettingsSingleton.getUserSettings();

            String imgPath = "english_" + userSettings.getSelectedText().toLowerCase() + ".jpg";
            String imagePathInExternalDir = userSettings.getAppDirPath() + "/" + imgPath;
            File customizedFile = new File(imagePathInExternalDir);

            // Compress and save image if default image is not selected
            if(userSettings.isNewImageSelected()) {
                FileOutputStream fOut = new FileOutputStream(customizedFile);
                if(userSettings.getSelectedImageBM() != null) {
                    userSettings.getSelectedImageBM().compress(Bitmap.CompressFormat.JPEG, 20, fOut);
                    fOut.flush();
                    fOut.close();
                    //Setting the customized image reference to null
                    userSettings.setSelectedImageBM(null);
                }
            } else {
                if(customizedFile.exists()) {
                    customizedFile.delete();
                }
            }

            // Save description in database
            EditText imageDesc = (EditText)userSettings.getAlertDialog().findViewById(R.id.image_desc);
            DatabaseUtil dbUtil = new DatabaseUtil(userSettings.getContext());
            String imageDescStr = imageDesc.getText().toString();
            String selectedText = userSettings.getSelectedText().toUpperCase();
            if(userSettings.getSelectedLearningOption() == Constants.ENGLISH_CAPS_VALUE ||
                    userSettings.getSelectedLearningOption() == Constants.ENGLISH_SMALL_VALUE) {
                imageDescStr = selectedText + " for " + imageDescStr;
            }
            dbUtil.updateUserSettings(selectedText, imageDescStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectPhotoFromGallery() {
        // Pick photo from gallery
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent,NO_OF_SELECTED_IMAGE);
        UserSettingsSingleton userSettings = UserSettingsSingleton.getUserSettings();
        userSettings.setImageName("english_" + userSettings.getSelectedText().toLowerCase().trim() + ".jpg");
    }

    private void loadDefaultImageAndDesc() {
        try {
            UserSettingsSingleton userSettings = UserSettingsSingleton.getUserSettings();
            DatabaseUtil dbUtil = new DatabaseUtil(userSettings.getContext());
            String selectedText = userSettings.getSelectedText();
            String imgPath = "english_" + selectedText.toLowerCase() + ".jpg";
            dbUtil.deleteUserSettingsByParam(selectedText.toUpperCase());
            InputStream si1 = context.getAssets().open(imgPath);
            displayImageInImageView(si1);
            displayImageDescInDialog();
            userSettings.setNewImageSelected(false);
            userSettings.setSelectedImageBM(null);

        } catch(Exception e) {
            Toast.makeText(context,"Default Image Loading failed", Toast.LENGTH_LONG).show();
        }
    }

    private void displayImageDescInDialog() {
        UserSettingsSingleton userSettings = UserSettingsSingleton.getUserSettings();
        String selectedText = userSettings.getSelectedText();
        EditText imageDesc = (EditText) userSettings.getAlertDialog().findViewById(R.id.image_desc);
        String textToBeDisplayed = Utility.getTextByAlphabet(selectedText);//userSettings.getUsrSettingsMap().get(selectedText);
        if (userSettings.getSelectedLearningOption() != Constants.ENGLISH_NUMBER_VALUE) {
            textToBeDisplayed = textToBeDisplayed.substring(6);
        }
        imageDesc.setText(textToBeDisplayed);
    }

    private void displayImageInImageView(InputStream si1) {
        UserSettingsSingleton userSettings = UserSettingsSingleton.getUserSettings();
        scaledImage = BitmapFactory.decodeStream(si1);
        ImageView imageView = (ImageView) userSettings.getAlertDialog().findViewById(R.id.selected_image);
        imageView.setImageBitmap(scaledImage);
        imageView.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(400, 300);
        imageView.setLayoutParams(parms);
    }
}

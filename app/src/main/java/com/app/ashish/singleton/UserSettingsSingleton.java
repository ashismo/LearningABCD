package com.app.ashish.singleton;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ashis_000 on 2/14/2015.
 */
public class UserSettingsSingleton {
    private static UserSettingsSingleton userSettings = null;
    private boolean isEditMode = false;
    private Context context;
    private String appDirPath;
    private String imageName;
    private int noOfTimeHomePageAccessed = 0;
    private String selectedText;                // A,B,C or a,b,c or 1,2,3
    private AlertDialog alertDialog;
    private Bitmap selectedImageBM;
    private String imageDesc;
    private int selectedLearningOption;
    private Map<String, String> usrSettingsMap = new HashMap<>();
    private boolean isAppDefaultImage;
    private boolean isAppDefaultImageChangedButNotSaved;

    private UserSettingsSingleton(){}
    public static UserSettingsSingleton getUserSettings() {
        if(userSettings == null) {
            userSettings = new UserSettingsSingleton();
        }
        return userSettings;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getAppDirPath() {
        return appDirPath;
    }

    public void setAppDirPath(String appDirPath) {
        this.appDirPath = appDirPath;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getNoOfTimeHomePageAccessed() {
        return noOfTimeHomePageAccessed;
    }

    public void setNoOfTimeHomePageAccessed(int noOfTimeHomePageAccessed) {
        this.noOfTimeHomePageAccessed = noOfTimeHomePageAccessed;
    }

    public String getSelectedText() {
        return selectedText;
    }

    public void setSelectedText(String selectedText) {
        this.selectedText = selectedText;
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    public Bitmap getSelectedImageBM() {
        return selectedImageBM;
    }

    public void setSelectedImageBM(Bitmap selectedImageBM) {
        this.selectedImageBM = selectedImageBM;
    }

    public String getImageDesc() {
        return imageDesc;
    }

    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }

    public int getSelectedLearningOption() {
        return selectedLearningOption;
    }

    public void setSelectedLearningOption(int selectedLearningOption) {
        this.selectedLearningOption = selectedLearningOption;
    }

    public Map<String, String> getUsrSettingsMap() {
        return usrSettingsMap;
    }

    public void setUsrSettingsMap(Map<String, String> usrSettingsMap) {
        this.usrSettingsMap = usrSettingsMap;
    }

    public boolean isAppDefaultImage() {
        if(userSettings != null && userSettings.getSelectedText() != null && userSettings.getAppDirPath() != null) {
            String imgPath = "english_" + userSettings.getSelectedText().toLowerCase() + ".jpg";
            String imagePathInExternalDir = userSettings.getAppDirPath() + "/" + imgPath;
            File file = new File(imagePathInExternalDir);
            if (!file.isDirectory() && file.exists()) {
                return false;
            }
        }
        return true;
    }

    public boolean isAppDefaultImageChangedButNotSaved() {
        return isAppDefaultImageChangedButNotSaved;
    }

    public void setAppDefaultImageChangedButNotSaved(boolean isAppDefaultImageChangedButNotSaved) {
        this.isAppDefaultImageChangedButNotSaved = isAppDefaultImageChangedButNotSaved;
    }
}

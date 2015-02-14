package com.app.ashish.singleton;

import android.app.Application;
import android.content.Context;

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
}

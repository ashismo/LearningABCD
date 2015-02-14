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
}

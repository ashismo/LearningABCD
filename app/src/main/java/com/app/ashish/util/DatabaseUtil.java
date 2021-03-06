package com.app.ashish.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

//import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by ashis_000 on 2/14/2015.
 */
public class DatabaseUtil {
    private Context context = null;
    private SQLiteDatabase userSettingsDB;
    public DatabaseUtil(Context context) {
        this.context = context;
        userSettingsDB = context.openOrCreateDatabase("APP_SETTINGS", Context.MODE_PRIVATE, null);
        userSettingsDB.execSQL("CREATE TABLE IF NOT EXISTS USER_SETTINGS(\n" +
                "\tSETTING_PARAM VARCHAR(50) NOT NULL,\n" +
                "\tSETTING_VAL VARCHAR(50) NOT NULL,\n" +
                "\tPRIMARY KEY (SETTING_PARAM))");
        userSettingsDB.close();
    }
    public void updateUserSettings(String settingsParam, String settingVal) {
        if(settingsParam != null && settingVal != null) {
            SQLiteDatabase userSettingsDB = context.openOrCreateDatabase("APP_SETTINGS", Context.MODE_PRIVATE, null);
            try {
                // Check if param-val already exists
                Cursor resultSet = userSettingsDB.rawQuery("Select * from USER_SETTINGS where " +
                        "SETTING_PARAM='" + settingsParam + "'", null);
                if (resultSet != null && resultSet.getCount() > 0) {
                    resultSet.moveToFirst();
                    String param = resultSet.getString(0);
                    String val = resultSet.getString(1);

                    if (settingsParam.equalsIgnoreCase(param)) {
                        userSettingsDB.execSQL("update USER_SETTINGS set SETTING_VAL='" + settingVal + "' WHERE SETTING_PARAM='" + settingsParam + "';");
                    }
                } else {
                    userSettingsDB.execSQL("INSERT INTO USER_SETTINGS VALUES('" + settingsParam + "','" + settingVal + "');");
                }
            } catch (Exception e) {

            } finally {
                userSettingsDB.close();
            }
        }
    }

    public String getUserSettingsByParam(String settingsParam) {
        if(settingsParam != null) {
            SQLiteDatabase userSettingsDB = context.openOrCreateDatabase("APP_SETTINGS", Context.MODE_PRIVATE, null);

            // Check if val exists for the given param
            try {
                Cursor resultSet = userSettingsDB.rawQuery("Select * from USER_SETTINGS where " +
                        "SETTING_PARAM='" + settingsParam + "'", null);
                if (resultSet != null && resultSet.getCount() > 0) {
                    resultSet.moveToFirst();
                    String val = resultSet.getString(1);
                    return val;
                }
            } catch (Exception e) {
//                // Exception occurs if table not present. Below method call creates table for the first time
//                updateUserSettings(settingsParam, "false");
            } finally {
                userSettingsDB.close();
            }
        }
        return null;
    }

    public void deleteUserSettingsByParam(String settingsParam) {
        if(settingsParam != null) {
            SQLiteDatabase userSettingsDB = context.openOrCreateDatabase("APP_SETTINGS", Context.MODE_PRIVATE, null);

            // delete if val exists for the given param
            try {
                Cursor resultSet = userSettingsDB.rawQuery("Select * from USER_SETTINGS where " +
                        "SETTING_PARAM='" + settingsParam + "'", null);
                if (resultSet != null && resultSet.getCount() > 0) {
                    resultSet.moveToFirst();
                    String val = resultSet.getString(1);
                    if(val != null) {
                        userSettingsDB.execSQL("DELETE FROM USER_SETTINGS where SETTING_PARAM='" + settingsParam + "'");
                    }
                }
            } catch (Exception e) {
//                // Exception occurs if table not present. Below method call creates table for the first time
//                updateUserSettings(settingsParam, "false");
            } finally {
                userSettingsDB.close();
            }
        }
    }

    public void resetAllUserSettings() {
            SQLiteDatabase userSettingsDB = context.openOrCreateDatabase("APP_SETTINGS", Context.MODE_PRIVATE, null);

            try {
                userSettingsDB.execSQL("DELETE FROM USER_SETTINGS");
            } catch (Exception e) {
            } finally {
                userSettingsDB.close();
            }
    }

    public Map<String, String> getAllUserSettings() {
        Map<String, String> userSettingsMap = new HashMap<>();
            SQLiteDatabase userSettingsDB = context.openOrCreateDatabase("APP_SETTINGS", Context.MODE_PRIVATE, null);

            // Check if val exists for the given param
            try {
                Cursor resultSet = userSettingsDB.rawQuery("Select * from USER_SETTINGS", null);
                if (resultSet != null && resultSet.getCount() > 0) {
                    resultSet.moveToFirst();
                    int totalSettings = resultSet.getCount();
                    for(int i = 0; i < totalSettings; i++) {
                        String param = resultSet.getString(0);
                        String val = resultSet.getString(1);
                        resultSet.moveToNext();
                        userSettingsMap.put(param, val);
                    }
                }
            } catch (Exception e) {
//                // Exception occurs if table not present. Below method call creates table for the first time
//                updateUserSettings(settingsParam, "false");
            } finally {
                userSettingsDB.close();
            }
        return userSettingsMap;
    }
}

package com.s22010022.plantpal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "PlantPalSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DARK_MODE = "darkMode";
    private static final String KEY_NOTIFICATIONS = "notifications";

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private final Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String username, String email) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, "User");
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public void setDarkMode(boolean enabled) {
        editor.putBoolean(KEY_DARK_MODE, enabled);
        editor.apply();
    }

    public boolean isDarkMode() {
        return pref.getBoolean(KEY_DARK_MODE, false);
    }

    public void setNotificationsEnabled(boolean enabled) {
        editor.putBoolean(KEY_NOTIFICATIONS, enabled);
        editor.apply();
    }

    public boolean isNotificationsEnabled() {
        return pref.getBoolean(KEY_NOTIFICATIONS, true);
    }

    public void logoutUser() {
        editor.clear();
        editor.apply();
        Intent intent = new Intent(context, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}

/*
 * Blockify - a messaging app with a focus on speed and security.
 * https://github.com/King-M-A-KH-85/blockify-android
 * Copyright (c) 2023. All Rights Reserved.
 *
 * Use of this source code is governed by a BSD-style license
 * that can be found in the LICENSE file in the root of the source
 * tree. An additional intellectual property rights grant can be found
 * in the file PATENTS.  All contributing project authors may
 * be found in the AUTHORS file in the root of the source tree.
 */

package io.blockify.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.VisibleForTesting;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.developer.crashx.config.CrashConfig;

import java.io.File;
import java.util.Timer;

public class ApplicationLoader extends MultiDexApplication {
    @SuppressLint("StaticFieldLeak")
    private static ApplicationLoader sInstance;

    public static ApplicationLoader getInstance() {
        return sInstance;
    }
    @SuppressLint("StaticFieldLeak")
    public static Context applicationContext;

    @Override
    public void onCreate() {
//        Timer timer = Time.startTimer();
        super.onCreate();
//        System.out.println("onCreate took " + timer.getElapsed());

        setupTheme();

        sInstance = this;
        applicationContext = this;
//        ApplicationProvider.initialize(applicationContext);

        CrashConfig.Builder.create()
                .backgroundMode(CrashConfig.BACKGROUND_MODE_SHOW_CUSTOM)
                .enabled(true)
                .showErrorDetails(true)
                .showRestartButton(true)
                .logErrorOnRestart(true)
                .trackActivities(true)
                .apply();

        File userDir = new File(getFilesDir(), "user_dir");
        System.setProperty("blockify.user.dir", userDir.getAbsolutePath());
    }

    private void setupTheme() {
//        ApplicationSettingsFragment.ThemeProvider provider = new ApplicationSettingsFragment.ThemeProvider(this);
//        int theme = provider.getThemeFromPreferences();
//        AppCompatDelegate.setDefaultNightMode(theme);
    }

    public static SharedPreferences getDefaultPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(applicationContext);
    }

    public static void showToast(String message) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
                .show();
    }

    @VisibleForTesting
    public static void setApplicationContext(Context context) {
        applicationContext = context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

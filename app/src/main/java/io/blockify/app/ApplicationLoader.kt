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
package io.blockify.app

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.developer.crashx.config.CrashConfig
import io.blockify.common.ApplicationProvider
import java.io.File

class ApplicationLoader : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        setupTheme()
        instance = this
//        Companion.applicationContext = this

        ApplicationProvider.initialize(applicationContext);

        CrashConfig.Builder.create()
            .backgroundMode(CrashConfig.BACKGROUND_MODE_SHOW_CUSTOM)
            .enabled(true)
            .showErrorDetails(true)
            .showRestartButton(true)
            .logErrorOnRestart(true)
            .trackActivities(true)
            .apply()

        val userDir = File(filesDir, "user_dir")
        System.setProperty("blockify.user.dir", userDir.absolutePath)
    }

    private fun setupTheme() {
//        ApplicationSettingsFragment.ThemeProvider provider = new ApplicationSettingsFragment.ThemeProvider(this);
//        int theme = provider.getThemeFromPreferences();
//        AppCompatDelegate.setDefaultNightMode(theme);
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: ApplicationLoader? = null
            private set

//        @SuppressLint("StaticFieldLeak")
//        lateinit var applicationContext: Context

        fun showToast(message: String?) {
//            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
//                .show()
        }

        @VisibleForTesting
        fun setApplicationContext(context: Context) {
//            applicationContext = context
        }
    }
}
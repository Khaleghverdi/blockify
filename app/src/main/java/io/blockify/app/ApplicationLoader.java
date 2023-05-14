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

import androidx.multidex.MultiDexApplication;

import ir.tapsell.plus.TapsellPlus;
import ir.tapsell.plus.TapsellPlusInitListener;
import ir.tapsell.plus.model.AdNetworkError;
import ir.tapsell.plus.model.AdNetworks;

/**
 * Edited by kingm on 5/11/2023
 */
public class ApplicationLoader extends MultiDexApplication {
    private static final String TAG = "ApplicationLoader";
    private static final String TAPSELL_PLUS_KEY = "YOUR_TAPSELL_PLUS_APP_ID";

    @Override
    public void onCreate() {
        super.onCreate();

        TapsellPlus.initialize(this, TAPSELL_PLUS_KEY,
                new TapsellPlusInitListener() {
                    @Override
                    public void onInitializeSuccess(AdNetworks adNetworks) {
                        // Init successful
                    }

                    @Override
                    public void onInitializeFailed(AdNetworks adNetworks,
                                                   AdNetworkError adNetworkError) {
                        // Error in initialization - Use provided parameters to see why
                    }
                });
    }
}

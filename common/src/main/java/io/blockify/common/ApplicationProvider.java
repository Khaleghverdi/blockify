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

package io.blockify.common;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Utility class to retrieve the application context from anywhere.
 */
public class ApplicationProvider {

    private static Context sApplicationContext;

    public static void initialize(@NonNull Context context) {
        sApplicationContext = context.getApplicationContext();
    }

    public static Context getApplicationContext() {
        if (sApplicationContext == null) {
            throw new IllegalStateException("initialize() has not been called yet.");
        }
        return sApplicationContext;
    }
}

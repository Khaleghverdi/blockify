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

@SuppressWarnings("unused")
object Versions {
    // Project versions
    private const val version = "0.21.1"
    const val versionCode = 77

    val versionName by lazy {
        if (CI.isCiBuild) {
            "$version-${CI.commitHash}-SNAPSHOT"
        } else version
    }

    // Platform & Tool versions
    const val buildToolsVersion = "33.0.0"
    const val compileSdkVersion = 33
    const val minSdkVersion = 21
    const val minSdkVersionHighApi = 26
    const val targetSdkVersion = 33
}
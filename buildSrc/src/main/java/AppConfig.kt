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

/**
 * Created by Sergey Chuprin on 07.06.2019.
 */

const val PUBLISHING_GROUP = "io.blockify"

object AppConfig {

    object AppCoordinates {
        const val APP_ID = "io.blockify"

        const val VERSION_CODE = 1
        const val VERSION_NAME = "1.0.0-alpha01"
    }

    object LibraryAndroidCoordinates {
        const val LIBRARY_VERSION = "1.0.0"
        const val LIBRARY_VERSION_CODE = 1
    }
    const val MIN_SDK = 21
    const val TARGET_SDK = 29
    const val COMPILE_SDK = 33
    const val buildToolsVersion = "31.0.0"

    object BuildTypes {
        const val DEV = "dev"
        const val DEBUG = "debug"
        const val RELEASE = "release"
        const val STAGING = "staging"
    }

    object BuildModes {
        const val USER = "user"
        const val ADMIN = "admin"
    }

}

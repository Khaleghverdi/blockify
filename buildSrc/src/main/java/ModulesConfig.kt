import org.gradle.api.JavaVersion

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
 * Created by king m a kh on 07.06.2019.
 */

const val publishingGroup = "io.blockify"

object ModulesConfig {
    const val minSdk = 21
    const val targetSdk = 29
    const val compileSdk = 33
    const val buildToolsVersion = "33.0.0"
    const val ndkVersion = "25.0.8775105"
    const val cmakeVersion = "3.22.1"
    val sourceCompatibility = JavaVersion.VERSION_17
    val targetCompatibility = JavaVersion.VERSION_17
    val jvmTarget = JavaVersion.VERSION_17.toString()

    private const val version = "0.0.1"
    const val versionCode = 1
    val versionName by lazy {
        if (CI.isCiBuild) {
            "$version-${CI.commitHash}-SNAPSHOT"
        } else version
    }

    object AppModule {
        const val namespace = "io.blockify.app"
        const val appId = "io.blockify"
    }

    const val TreeViewNamespace = "io.blockify.treeview"

    object BuildTypes {
        const val dev = "dev"
        const val debug = "debug"
        const val release = "release"
        const val staging = "staging"
    }

    object BuildModes {
        const val user = "user"
        const val admin = "admin"
        const val googlePlay = "googlePlay"
        const val fdroid = "fdroid"
        const val myket = "myket"
    }
}

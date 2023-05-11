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

import com.android.build.gradle.BaseExtension

@Suppress("DSL_SCOPE_VIOLATION") // https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    id("build-logic.root-project")
    id("com.android.application") apply false
    id("com.android.library") apply false
    kotlin("android") apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")
    }
}


fun Project.configureBaseExtension() {
    extensions.findByType(BaseExtension::class)?.run {
        compileSdkVersion(ModulesConfig.compileSdk)
        buildToolsVersion = ModulesConfig.buildToolsVersion

        defaultConfig {
            minSdk = ModulesConfig.minSdk
            targetSdk = ModulesConfig.targetSdk
            versionCode = ModulesConfig.versionCode
            versionName = ModulesConfig.versionName
        }

        compileOptions {
            sourceCompatibility = ModulesConfig.sourceCompatibility
            targetCompatibility = ModulesConfig.targetCompatibility
        }

        buildTypes {
            getByName(ModulesConfig.BuildTypes.release) {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            }

            getByName(ModulesConfig.BuildTypes.debug) {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            }
        }
    }
}

subprojects {
    plugins.withId("com.android.application") {
        configureBaseExtension()
    }

    plugins.withId("com.android.library") {
        configureBaseExtension()
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}

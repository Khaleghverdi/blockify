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

import org.gradle.internal.classpath.Instrumented.systemProperty
import java.util.Properties
import java.io.FileInputStream
import java.net.URI

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "io.blockify.app"
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        applicationId = AppConfig.AppCoordinates.APP_ID
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
        versionCode = AppConfig.AppCoordinates.VERSION_CODE
        versionName = AppConfig.AppCoordinates.VERSION_NAME
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    val keystorePropertiesFile = rootProject.file("keystore.properties")
    val keystoreProperties = Properties().apply {
        load(FileInputStream(keystorePropertiesFile))
    }

    signingConfigs {
        create("general") {
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
        }
    }

    buildTypes {
        getByName(AppConfig.BuildTypes.RELEASE) {
            manifestPlaceholders["hostName"] = "king-m-a-kh.ir"
            signingConfig = signingConfigs.getByName("general")
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false

            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            testProguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguardTest-rules.pro"
            )
            multiDexKeepProguard = file("multidex-config.pro")
        }

        getByName(AppConfig.BuildTypes.DEBUG) {
            manifestPlaceholders["hostName"] = "king-m-a-kh.ir"
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("general")
            isDebuggable = true
        }
    }

    flavorDimensions("environment")

    productFlavors {

        create(AppConfig.BuildModes.USER) {
            dimension = "environment"
            buildConfigField("String", "VAR", "\"this is string from user build script\"")
        }

        create(AppConfig.BuildModes.ADMIN) {
            dimension = "environment"
            applicationIdSuffix = ".admin"
            buildConfigField("String", "VAR", "\"this one is from admin build script\"")
        }
    }

    // Always show the result of every unit test, even if it passes.
    testOptions.unitTests {
        isIncludeAndroidResources = true

        all { test ->
            with(test) {
                testLogging {
                    events = setOf(
                        org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                        org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
                        org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                        org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT,
                        org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR,
                    )
                }
            }
        }
    }

    testOptions.unitTests.all {
        systemProperty("robolectric.enabledSdks", "26")
    }

    compileOptions {
//        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }


    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
            freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.Experimental"
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    dependenciesInfo {
        // Disables dependency metadata when building APKs and Bundles.
        includeInApk = false
        includeInBundle = false
    }

    lint {
        warningsAsErrors = true
        abortOnError = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // multidex
    implementation(libs.multidex)

    // modules
    implementation(projects.feature.android.intro)
    implementation(projects.common)
    implementation(projects.feature.compose)
    implementation(projects.feature.components)

    // androidx
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraint.layout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    // test implementation
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.espresso.core)

    // about
    implementation("com.github.daniel-stoneuk:material-about-library:3.1.2")
    implementation("com.github.TutorialsAndroid:crashx:v6.0.19")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.20")
}

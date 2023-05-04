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

import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    kotlin("android")
}


val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "io.blockify.app"
    compileSdk = AppConfig.COMPILE_SDK

    val test = rootProject.extra["sdkVersion"]

    defaultConfig {
        applicationId = AppConfig.AppCoordinates.APP_ID
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
        versionCode = AppConfig.AppCoordinates.VERSION_CODE
        versionName = AppConfig.AppCoordinates.VERSION_NAME
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += "room.incremental" to "true"
            }
        }
    }

    buildFeatures {
        viewBinding = true
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
            isMinifyEnabled = true
            isShrinkResources = true
//            signingConfig = signingConfigs.getByName("general")

            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
            testProguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguardTest-rules.pro"
            )
            multiDexKeepProguard = file("multidex-config.pro")

        }

        getByName(AppConfig.BuildTypes.DEBUG) {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }

        /**
         * The `initWith` property lets you copy configurations from other build types,
         * then configure only the settings you want to change. This one copies the debug build
         * type, and then changes the manifest placeholder and application ID.
         */
        create(AppConfig.BuildTypes.STAGING) {
            initWith(getByName(AppConfig.BuildTypes.DEBUG))
            manifestPlaceholders["hostName"] = "internal.example.com"
            applicationIdSuffix = ".debugStaging"
        }
    }

    /**
     * The productFlavors block is where you can configure multiple product flavors.
     * This lets you create different versions of your app that can
     * override the defaultConfig block with their own settings. Product flavors
     * are optional, and the build system does not create them by default.
     *
     * This example creates a free and paid product flavor. Each product flavor
     * then specifies its own application ID, so that they can exist on the Google
     * Play Store, or an Android device, simultaneously.
     *
     * If you declare product flavors, you must also declare flavor dimensions
     * and assign each flavor to a flavor dimension.
     */

    flavorDimensions += listOf("api", "mode")


    productFlavors {

        create(AppConfig.BuildModes.FULL) {
            dimension = "mode"
            //            resourceConfigurations("en", "xxhdpi")

        }

        create(AppConfig.BuildModes.DEMO) {
            // Assigns this product flavor to the "mode" flavor dimension.
            dimension = "mode"
            applicationIdSuffix = ".demo"

            // Specifies a sorted list of fallback flavors that the plugin
            // can try to use when a dependency's matching dimension does
            // not include a "free" flavor. Specify as many
            // fallbacks as you like; the plugin selects the first flavor
            // that's available in the dependency's "tier" dimension.
            matchingFallbacks += listOf("demo", "trial")
        }


        create(AppConfig.BuildModes.ADMIN) {
            // Assigns this product flavor to the "mode" flavor dimension.
            dimension = "mode"
            applicationIdSuffix = ".admin"
            matchingFallbacks += listOf("demo", "trial")
        }


        // Configurations in the "api" product flavors override those in "mode"
        // flavors and the defaultConfig block. Gradle determines the priority
        // between flavor dimensions based on the order in which they appear next
        // to the flavorDimensions property, with the first dimension having a higher
        // priority than the second, and so on.
        create(AppConfig.BuildModes.MIN_API24) {
            dimension = "api"
            minSdk = 24
            // To ensure the target device receives the version of the app with
            // the highest compatible API level, assign version codes in increasing
            // value with API level.
            versionCode = 30000 + (android.defaultConfig.versionCode ?: 0)
            versionNameSuffix = "-minApi24"
        }

        create(AppConfig.BuildModes.MIN_API23) {
            dimension = "api"
            minSdk = 23
            versionCode = 20000 + (android.defaultConfig.versionCode ?: 0)
            versionNameSuffix = "-minApi23"
        }

        create(AppConfig.BuildModes.MIN_API21) {
            dimension = "api"
            minSdk = 21
            versionCode = 10000 + (android.defaultConfig.versionCode ?: 0)
            versionNameSuffix = "-minApi21"
        }
    }

    androidComponents {
        beforeVariants { variantBuilder ->
            // To check for a certain build type, use variantBuilder.buildType == "<buildType>"
            if (variantBuilder.productFlavors.containsAll(
                    listOf(
                        "api" to AppConfig.BuildModes.MIN_API21,
                        "mode" to AppConfig.BuildModes.DEMO
                    )
                )
            ) {
                // Gradle ignores any variants that satisfy the conditions above.
                variantBuilder.enable = false
            }
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
            freeCompilerArgs += "-opt-in=kotlin.Experimental"
        }
    }

    packagingOptions {
        excludes += "META-INF/AL2.0"
        excludes += "META-INF/LGPL2.1"
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    dependenciesInfo {
        // Disables dependency metadata when building APKs.
        includeInApk = false
        // Disables dependency metadata when building Android App Bundles.
        includeInBundle = false
    }
    lint {
        warningsAsErrors = true
        abortOnError = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(projects.feature.android.intro)
    implementation(projects.feature.compose)
    implementation(projects.feature.components)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraint.layout)
    implementation(libs.androidx.core.ktx)
    implementation("com.google.android.material:material:1.8.0")

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.espresso.core)

    // Adds the local "mylibrary" module as a dependency to the "free" flavor.
//    "freeImplementation"(project(":mylibrary"))

//    implementation("some-library") {
//        exclude(group = "com.example.imgtools", module = "native")
//    }

    val multidex_version = "2.0.1"
    implementation("androidx.multidex:multidex:$multidex_version")

    //    implementation("androidx.appcompat:appcompat:${rootProject.ext.appcompatVersion}")


}
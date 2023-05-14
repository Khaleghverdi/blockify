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

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = ModulesConfig.AppModule.namespace

    defaultConfig {
        applicationId = ModulesConfig.AppModule.appId
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        vectorDrawables.generatedDensities("mdpi", "hdpi", "xhdpi", "xxhdpi")

//        externalNativeBuild {
//            cmake {
//                version = ModulesConfig.cmakeVersion
//                arguments(
//                    "-DANDROID_STL=c++_static",
//                    "-DANDROID_PLATFORM=android-16",
//                    "-j=16"
//                )
//            }
//        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
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
            this.enableV1Signing = true
            this.enableV2Signing = true
            this.enableV3Signing = true
            this.enableV4Signing = true
        }
    }

    buildTypes {
        getByName(ModulesConfig.BuildTypes.release) {
            manifestPlaceholders["hostName"] = "king-m-a-kh.ir"
            signingConfig = signingConfigs.getByName("general")
            isMinifyEnabled = true
            isShrinkResources = true
            isJniDebuggable = true
            multiDexEnabled = true
            isDebuggable = false
            ndk.debugSymbolLevel = "FULL"
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            testProguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguardTest-rules.pro"
            )
            multiDexKeepProguard = file("multidex-config.pro")
        }

        getByName(ModulesConfig.BuildTypes.debug) {
            manifestPlaceholders["hostName"] = "king-m-a-kh.ir"
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("general")

            isMinifyEnabled = false
            isDebuggable = true
            isJniDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            multiDexEnabled = true
            ndk.debugSymbolLevel = "FULL"
        }
    }

    flavorDimensions("environment", "store")

    productFlavors {

        create(ModulesConfig.BuildModes.user) {
            dimension = "environment"
            buildConfigField("String", "VAR", "\"this is string from user build script\"")
        }

        create(ModulesConfig.BuildModes.admin) {
            dimension = "environment"
            buildConfigField("String", "VAR", "\"this one is from admin build script\"")
        }

        create(ModulesConfig.BuildModes.googlePlay) {
            dimension = "store"
        }

        create(ModulesConfig.BuildModes.fdroid) {
            dimension = "store"
        }

        create(ModulesConfig.BuildModes.myket) {
            dimension = "store"
            val marketApplicationId = "io.blockify"
            val marketBindAddress = "ir.mservices.market.InAppBillingService.BIND"
            val manifestPlaceholders = mapOf(
                "marketApplicationId" to marketApplicationId,
                "marketBindAddress" to marketBindAddress,
                "marketPermission" to "${marketApplicationId}.BILLING"
            )
            buildConfigField("String", "IAB_PUBLIC_KEY", "\"{MYKET_PUBLIC_KEY}\"")
        }

        create(ModulesConfig.BuildModes.bazaar) {
            dimension = "store"
            val marketApplicationId = "io.blockify"
            val marketBindAddress = "ir.cafebazaar.pardakht.InAppBillingService.BIND"
            val manifestPlaceholders = mapOf(
                "marketApplicationId" to marketApplicationId,
                "marketBindAddress" to marketBindAddress,
                "marketPermission" to "io.blockify.permission.PAY_THROUGH_BAZAAR"
            )
            buildConfigField("String", "IAB_PUBLIC_KEY", "\\\"{BAZAAR_PUBLIC_KEY}\\\"")
        }
    }

//        externalNativeBuild {
//            cmake {
//                path = 'jni/CMakeLists.txt'
//            }
//        }

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
        isCoreLibraryDesugaringEnabled = true
    }

    androidResources {
        additionalParameters.add("--warn-manifest-validation")
    }


    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
            freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.Experimental"
        }
    }

    kotlinOptions {
        jvmTarget = ModulesConfig.jvmTarget
    }

    dependenciesInfo {
        // Disables dependency metadata when building APKs and Bundles.
        includeInApk = false
        includeInBundle = false
    }

    lint {
//        warningsAsErrors = true
//        abortOnError = true

//        abortOnError = false
//        checkReleaseBuilds = false

        disable.add("VectorPath")
        disable.add("NestedWeights")
        disable.add("ContentDescription")
        disable.add("SmallSp")
        disable.add("MissingTranslation")
        disable.add("ExtraTranslation")
        disable.add("BlockedPrivateApi")
    }
//    packagingOptions {
//        // Multiple dependency bring these files in. Exclude them to enable
//        // our test APK to build (has no effect on our AARs)
//        resources.excludes += "/META-INF/AL2.0"
//        resources.excludes += "/META-INF/LGPL2.1"
//        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        jniLibs.useLegacyPackaging = true
//    }
}

androidComponents {
    onVariants(selector().withBuildType("release")) {
        // Only exclude *.version files in release mode as debug mode requires
        // these files for layout inspector to work.
        it.packaging.resources.excludes.add("META-INF/*.version")

        // Multiple dependency bring these files in. Exclude them to enable
        // our test APK to build (has no effect on our AARs)
        it.packaging.resources.excludes.add("/META-INF/AL2.0")
        it.packaging.resources.excludes.add("/META-INF/LGPL2.1")
        it.packaging.resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
//        it.packaging.jniLibs.useLegacyPackaging = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // multidex
    implementation(libs.multidex)

    // Desugar
    coreLibraryDesugaring(libs.desugar)

    // modules
    implementation(projects.common)
    implementation(projects.components)

    // androidx
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraint.layout)
    implementation(libs.material)
    implementation(libs.palette)

    implementation(libs.androidx.core)
    implementation(libs.core.splashscreen)
    implementation(libs.core.google.shortcuts)
    implementation(libs.annotation)
    implementation(libs.palette)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.dynamic.features.fragment)
    implementation(libs.navigation.testing)

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.compiler)

    implementation(libs.fragment)

    implementation(libs.preference)

    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    implementation(libs.work.runtime)

    // other libs
    implementation(libs.about)
    implementation(libs.crashx)
    implementation(libs.myket.billing.client)
    implementation(libs.tapsell.plus)

    // test implementation
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.espresso.core)
}

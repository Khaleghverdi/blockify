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

// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

@Suppress("DSL_SCOPE_VIOLATION") // https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    id("build-logic.root-project")
    id("com.android.application") apply false
    id("com.android.library") apply false
    kotlin("android") apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.versions)
    alias(libs.plugins.publish) apply false
    cleanup
    base
}

allprojects {
    group = PUBLISHING_GROUP
}

val detektFormatting = libs.detekt.formatting


subprojects {
    plugins.withId("com.android.application") {
        configureBaseExtension()
    }
    plugins.withId("com.android.library") {
        configureBaseExtension()
    }

//    plugins.withId("com.vanniktech.maven.publish.base") {
//        configure<MavenPublishBaseExtension> {
//            group = "io.github.Rosemoe.sora-editor"
//            version = Versions.versionName
//            pomFromGradleProperties()
//            publishToMavenCentral(SonatypeHost.S01)
//            signAllPublications()
//            if ("bom" != this@subprojects.name) {
//                configure(AndroidSingleVariantLibrary(publishJavadocJar = false))
//            }
//        }
//    }

    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    detekt {
        config = rootProject.files("config/detekt/detekt.yml")
    }

    dependencies {
        detektPlugins(detektFormatting)
    }

}
fun Project.configureBaseExtension() {
//    extensions.findByType(BaseExtension::class)?.run {
//        compileSdkVersion(Versions.compileSdkVersion)
//        buildToolsVersion = Versions.buildToolsVersion
//
//        defaultConfig {
//            minSdk = if (highApiProjects.contains(this@configureBaseExtension.name)) Versions.minSdkVersionHighApi else Versions.minSdkVersion
//            targetSdk = Versions.targetSdkVersion
//            versionCode = Versions.versionCode
//            versionName = Versions.versionName
//        }
//
//        compileOptions {
//            sourceCompatibility = JavaVersion.VERSION_11
//            targetCompatibility = JavaVersion.VERSION_11
//        }
//    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    }
}

// This block encapsulates custom properties and makes them available to all
// modules in the project. The following are only a few examples of the types
// of properties you can define.
ext {
    extra["sdkVersion"] = 33
    // You can also create properties to specify versions for dependencies.
    // Having consistent versions between modules can avoid conflicts with behavior.
    extra["appcompatVersion"] = "1.6.1"
}

tasks {
    withType<DependencyUpdatesTask>().configureEach {
        rejectVersionIf {
            candidate.version.isStableVersion().not()
        }
    }
}

//tasks.register<Delete>("clean").configure {
//    delete(rootProject.buildDir)
//}

val excludeProjectName = arrayOf("app", "buildSrc")

tasks.register("bundleAll") {
    group = "blockify"
    allprojects
        .filter { it.name !in excludeProjectName }
        .forEach { dependsOn(it.getTasksByName("bundleReleaseAar", false)) }
}

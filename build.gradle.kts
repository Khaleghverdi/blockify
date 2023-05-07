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

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import java.io.File
import java.nio.file.Files

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
    group = publishingGroup
}

val detektFormatting = libs.detekt.formatting


subprojects {
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

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath("com.google.gms:google-services:4.3.15")
    }
}

tasks {
    withType<DependencyUpdatesTask>().configureEach {
        rejectVersionIf {
            candidate.version.isStableVersion().not()
        }
    }
}


tasks.register("deleteUnimportedJavaFiles") {
    // TODO: this dont work
    doLast {
        val selectedFiles = mutableListOf<File>()

        // Select Java files
        println("Select Java files:")
        while (true) {
            val input = readLine()?.trim()
            if (input.isNullOrBlank()) {
                break
            }
            val file = File(input)
            if (file.isFile && file.extension.equals("java", ignoreCase = true)) {
                selectedFiles.add(file)
            }
        }

        // Get imported classes
        val importedClasses = mutableSetOf<String>()
        selectedFiles.forEach { file ->
            file.readLines().forEach { line ->
                if (line.trim().startsWith("import ")) {
                    importedClasses.add(
                        line.trim().substringAfter("import ").substringBeforeLast(";").trim()
                    )
                }
            }
        }

        // Delete unimported Java files
        selectedFiles.forEach { file ->
            val className = file.nameWithoutExtension
            if (!importedClasses.contains(className)) {
                val path = file.toPath()
                Files.delete(path)
                println("Deleted ${path.toAbsolutePath()}")
            }
        }
    }
}

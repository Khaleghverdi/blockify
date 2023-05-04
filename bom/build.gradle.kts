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

plugins {
    id("com.vanniktech.maven.publish.base")
    id("java-platform")
}

dependencies {
    constraints {
        project.rootProject.subprojects.forEach { subproject ->
            if (subproject.name != "bom") {
                api(subproject)
            }
        }
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(project.components["javaPlatform"])
    }
}
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

import java.io.File

/**
 * Information about the CI build.
 *
 * @author Akash Yadav
 */
object CI {

    /** The short commit hash. */
    val commitHash by lazy {
        val sha = System.getenv("GITHUB_SHA") ?: return@lazy ""
        shortSha(sha)
    }

    /** Name of the current branch. */
    val branchName by lazy {
        System.getenv("GITHUB_REF_NAME") ?: "main" // by default, 'main'
    }

    /** Whether the current build is a CI build. */
    val isCiBuild by lazy { "true" == System.getenv("CI") }

    private fun shortSha(sha: String): String {
        return ProcessBuilder("git", "rev-parse", "--short", sha)
            .directory(File("."))
            .redirectErrorStream(true)
            .start()
            .inputStream
            .bufferedReader()
            .readText()
            .trim()
    }
}
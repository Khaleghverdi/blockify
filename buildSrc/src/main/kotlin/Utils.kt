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
 * Edited by kingm on 5/7/2023
 */

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

fun main() {
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
                importedClasses.add(line.trim().substringAfter("import ").substringBeforeLast(";").trim())
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
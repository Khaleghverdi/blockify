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

package io.blockify.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class TestUtil {

    public static boolean isWindows() {
        return System.getProperty("os.name", "")
                .startsWith("Windows");
    }

    public static boolean isDalvik() {
        return System.getProperty("java.vm.name", "")
                .contains("Dalvik");
    }

    public static File getResourcesDirectory() {
        File currentDirFile = Paths.get(".").toFile();
        String helper = currentDirFile.getAbsolutePath();
        String currentDir = helper.substring(0,
                helper.length() - 1);
        return new File(new File(currentDir), "src/test/resources");
    }
}
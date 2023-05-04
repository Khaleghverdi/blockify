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

package io.blockify.feature.android

import android.content.Context
import android.widget.Toast

object ToastUtil {
    fun showToast(context: Context, message: String): Toast =
        Toast.makeText(context, message, Toast.LENGTH_SHORT).also {
            it.show()
        }
}

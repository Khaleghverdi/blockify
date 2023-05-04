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

package io.blockify.feature.components

object FactorialCalculator {
    private const val MAX_FACTORIAL_64BIT = 20

    tailrec fun computeFactorial(input: Long, temp: Long = 1L): Long =
        when {
            input < 0 -> error("Factorial is not defined for negative numbers")
            input > MAX_FACTORIAL_64BIT -> error("Only a factorial up to 20 can fit in a 64-bit Long")
            input == 0L -> temp
            else -> computeFactorial(input - 1, temp * input)
        }
}

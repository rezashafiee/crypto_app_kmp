package com.tilda.crypto.presentation.utils

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle

actual object NumberFormatter {
    actual fun format(value: Double, fractionDigits: Int): String {
        val formatter = NSNumberFormatter()
        formatter.numberStyle = NSNumberFormatterDecimalStyle
        formatter.minimumFractionDigits = fractionDigits.toULong()
        formatter.maximumFractionDigits = fractionDigits.toULong()
        return formatter.stringFromNumber(NSNumber(double = value)) ?: "$value"
    }
}
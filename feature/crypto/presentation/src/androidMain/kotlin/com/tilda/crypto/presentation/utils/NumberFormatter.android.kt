package com.tilda.crypto.presentation.utils

import java.text.NumberFormat
import java.util.Locale

actual object NumberFormatter {
    actual fun format(value: Double, fractionDigits: Int): String {
        val format = NumberFormat.getNumberInstance(Locale.getDefault())
        format.minimumFractionDigits = fractionDigits
        format.maximumFractionDigits = fractionDigits
        return format.format(value)
    }
}
package com.tilda.crypto.presentation.utils

expect object NumberFormatter {
    fun format(value: Double, fractionDigits: Int = 2): String
}

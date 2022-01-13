package com.mackhartley.temptracker.utils

fun Double.toTempNumberString(): String {
    return String.format("%.2f", this)
}
package com.mackhartley.temptracker.utils

fun Double.toTempString(): String {
    return String.format("%.2f", this)
}

fun Double.toF(): Double {
    return (this * (9/5))+32
}

fun Double.toC(): Double {
    return (this - 32) * (5/9)
}
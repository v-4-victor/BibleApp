package com.v4vic.ui_kit

fun Int.format(): String {
    return if (this < 10) "0$this"
    else this.toString()
}

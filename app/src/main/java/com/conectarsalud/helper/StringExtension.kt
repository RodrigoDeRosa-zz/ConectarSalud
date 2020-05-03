package com.conectarsalud.helper

import java.security.MessageDigest

fun String.sha256(): String {
    val bytes = MessageDigest
        .getInstance("SHA-256")
        .digest(this.toByteArray())
    val result = StringBuilder(bytes.size * 2)

    val hexChars = "0123456789abcdef"
    bytes.forEach {
        val i = it.toInt()
        result.append(hexChars[i shr 4 and 0x0f])
        result.append(hexChars[i and 0x0f])
    }

    return result.toString()
}

package com.igs.nfc_rw.utils

import android.util.Log
import androidx.compose.runtime.mutableStateListOf

object Logger {
    private val logs = mutableStateListOf<String>()


    fun d(tag: String, message: String) {
        val logMessage = "[$tag] d $message"
        logs.add(logMessage)
        Log.d(tag, message)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        val logMessage = "[$tag] e $message"
        logs.add(logMessage)
        Log.e(tag, message, throwable)
    }

    fun getLogs(): List<String> = logs.toList()
}


package com.igs.nfc_rw.utils

import android.util.Log
import androidx.compose.runtime.mutableStateListOf

object Logger {
    private val logs = mutableStateListOf<String>()


    fun d(tag: String, message: String) {
        val logMessage = "[$tag] $message"
        logs.add(logMessage)
        Log.d(tag, message)
    }

    fun getLogs(): List<String> = logs.toList()
}
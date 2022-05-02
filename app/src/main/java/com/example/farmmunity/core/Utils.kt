package com.example.farmmunity.core

import android.util.Log

class Utils {
    companion object {
        fun printError(message: String) {
            Log.e(AppConstants.TAG, "printError: $message")
        }
    }
}
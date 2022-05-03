package com.example.farmmunity.authentication.core

import android.util.Log

class AuthenticationUtils {
    companion object {
        fun printError(message: String) {
            Log.e(AuthenticationConstants.TAG, "printError: $message")
        }

        fun printError(exception: Exception) {
            Log.e(AuthenticationConstants.TAG, "printError: ${exception.message}")
        }
    }
}
package com.example.farmmunity.authentication.core

import com.google.firebase.auth.PhoneAuthCredential

sealed class PhoneAuthResult {
    data class OnVerificationCompleted(val credential: PhoneAuthCredential) :
        PhoneAuthResult()

    data class OnCodeSent(val verificationId: String) : PhoneAuthResult()
}
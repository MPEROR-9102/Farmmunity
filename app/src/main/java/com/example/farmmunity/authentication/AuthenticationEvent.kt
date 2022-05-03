package com.example.farmmunity.authentication

import com.google.firebase.auth.FirebaseUser

sealed class AuthenticationEvent {
    object SignInWithGoogle : AuthenticationEvent()
    data class UpdateUI(val user: FirebaseUser?) : AuthenticationEvent()
}

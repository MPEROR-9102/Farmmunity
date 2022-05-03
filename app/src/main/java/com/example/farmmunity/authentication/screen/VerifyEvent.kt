package com.example.farmmunity.authentication.screen

sealed class VerifyEvent {
    object OnCodeSubmit : VerifyEvent()
}
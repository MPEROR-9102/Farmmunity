package com.example.farmmunity.authentication.screen

sealed class PhoneEvent {
    data class PhoneReceived(val phone: String) : PhoneEvent()
}
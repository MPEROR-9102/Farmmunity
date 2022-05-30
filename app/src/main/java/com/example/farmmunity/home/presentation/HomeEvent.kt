package com.example.farmmunity.home.presentation

sealed class HomeEvent {
    object OnGalleryClicked : HomeEvent()
    object ShowAuthenticationScreen : HomeEvent()
}
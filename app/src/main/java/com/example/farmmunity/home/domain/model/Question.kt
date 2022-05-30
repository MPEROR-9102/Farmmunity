package com.example.farmmunity.home.domain.model

data class Question(
    val uid: String = "",
    val profile: Profile = Profile(),
    val title: String = "",
    val description: String = "",
    val photoUrl: String = "",
    val posted: Long = System.currentTimeMillis()
) {
    data class Profile(
        val name: String = "",
        val photo: String = ""
    )
}

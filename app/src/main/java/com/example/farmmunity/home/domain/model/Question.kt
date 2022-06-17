package com.example.farmmunity.home.domain.model

data class Question(
    val uid: String = "",
    val profile: Profile = Profile(),
    val title: String = "",
    val description: String = "",
    val photoUrl: String = "",
    val answerCount: Int = 0,
    val posted: Long = System.currentTimeMillis()
)

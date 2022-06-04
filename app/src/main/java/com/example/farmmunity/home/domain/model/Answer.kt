package com.example.farmmunity.home.domain.model

data class Answer(
    val uid: String = "",
    val title: String = "",
    val posted: Long = System.currentTimeMillis(),
    val profile: Profile = Profile(),
    val upVotes: Int = 0,
    val downVotes: Int = 0
)

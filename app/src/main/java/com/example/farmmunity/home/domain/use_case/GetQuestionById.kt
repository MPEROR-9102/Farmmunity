package com.example.farmmunity.home.domain.use_case

import com.example.farmmunity.home.domain.repository.QuestionsRepository
import javax.inject.Inject

class GetQuestionById @Inject constructor(
    private val questionsRepository: QuestionsRepository
) {

    operator fun invoke(uid: String) =
        questionsRepository.getQuestionById(uid)
}
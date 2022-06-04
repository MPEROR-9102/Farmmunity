package com.example.farmmunity.home.domain.use_case

import com.example.farmmunity.home.domain.repository.QuestionsRepository
import javax.inject.Inject

class AddAnswer @Inject constructor(
    private val questionsRepository: QuestionsRepository
) {

    operator fun invoke(questionId: String, answerTitle: String) =
        questionsRepository.addAnswer(questionId, answerTitle)
}
package com.example.farmmunity.home.domain.use_case

import com.example.farmmunity.home.core.InvalidAnswerException
import com.example.farmmunity.home.domain.repository.QuestionsRepository
import javax.inject.Inject

class AddAnswer @Inject constructor(
    private val questionsRepository: QuestionsRepository
) {
    @Throws(InvalidAnswerException::class)
    operator fun invoke(questionId: String, answerTitle: String) =
        if (answerTitle.isNotBlank()) {
            questionsRepository.addAnswer(questionId, answerTitle)
        } else {
            throw InvalidAnswerException("Invalid Answer")
        }
}
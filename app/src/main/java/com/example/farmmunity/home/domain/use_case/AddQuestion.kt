package com.example.farmmunity.home.domain.use_case

import android.net.Uri
import com.example.farmmunity.home.core.InvalidDescriptionException
import com.example.farmmunity.home.core.InvalidQuestionException
import com.example.farmmunity.home.domain.repository.QuestionsRepository
import javax.inject.Inject

class AddQuestion @Inject constructor(
    private val questionsRepository: QuestionsRepository
) {

    @Throws(InvalidQuestionException::class, InvalidDescriptionException::class)
    operator fun invoke(title: String, description: String, photo: Uri) =
        when {
            title.isBlank() -> {
                throw InvalidQuestionException("Invalid Question")
            }
            description.isBlank() -> {
                throw InvalidDescriptionException("Invalid Description")
            }
            else -> {
                questionsRepository.addQuestion(
                    title = title,
                    description = description,
                    photo = photo
                )
            }
        }
}
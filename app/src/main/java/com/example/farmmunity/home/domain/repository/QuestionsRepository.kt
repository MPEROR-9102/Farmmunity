package com.example.farmmunity.home.domain.repository

import android.net.Uri
import com.example.farmmunity.home.domain.model.Answer
import com.example.farmmunity.home.domain.model.Question
import com.example.farmmunity.home.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface QuestionsRepository {

    fun getQuestions(): Flow<Response<List<Question>>>

    fun addQuestion(
        title: String,
        description: String,
        photo: Uri
    ): Flow<Response<Void?>>

    fun getQuestionById(uid: String): Flow<Response<Question>>

    fun addAnswer(questionId: String, answerTitle: String): Flow<Response<Void?>>

    fun getAnswers(questionId: String): Flow<Response<List<Answer>>>

    fun updateAnswerCount(questionId: String, count: Int)
}
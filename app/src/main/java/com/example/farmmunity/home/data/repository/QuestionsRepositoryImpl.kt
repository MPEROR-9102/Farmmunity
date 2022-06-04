package com.example.farmmunity.home.data.repository

import android.net.Uri
import com.example.farmmunity.home.domain.model.Answer
import com.example.farmmunity.home.domain.model.Profile
import com.example.farmmunity.home.domain.model.Question
import com.example.farmmunity.home.domain.model.Response
import com.example.farmmunity.home.domain.repository.QuestionsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.suspendCoroutine

class QuestionsRepositoryImpl(
    private val questionsRef: CollectionReference,
    private val storageReference: StorageReference,
    firebaseAuth: FirebaseAuth
) : QuestionsRepository {

    private val currentUser = firebaseAuth.currentUser

    override fun getQuestions(): Flow<Response<List<Question>>> = callbackFlow {
        val snapshot = questionsRef.addSnapshotListener { value, error ->
            val response = if (value != null) {
                val questions = value.toObjects(Question::class.java)
                Response.Success(questions)
            } else {
                Response.Error(error?.localizedMessage ?: error.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshot.remove()
        }
    }

    override fun addQuestion(
        title: String,
        description: String,
        photo: Uri
    ): Flow<Response<Void?>> =
        flow {
            try {
                emit(Response.Loading)
                val questionId = questionsRef.document().id
                val photoUrl = if (photo != Uri.EMPTY) suspendCoroutine<Uri> {
                    storageReference.child("images/$questionId").putFile(photo)
                        .addOnCompleteListener { uploadTask ->
                            if (uploadTask.isSuccessful) {
                                storageReference.child("images/$questionId").downloadUrl
                                    .addOnCompleteListener { downloadTask ->
                                        if (downloadTask.isSuccessful) {
                                            it.resumeWith(Result.success(downloadTask.result))
                                        }
                                    }
                            }
                        }
                } else Uri.EMPTY
                val question = Question(
                    uid = questionId,
                    title = title,
                    description = description,
                    photoUrl = photoUrl.toString(),
                    profile = Profile(
                        name = currentUser?.displayName ?: "",
                        photo = currentUser?.photoUrl.toString()
                    )
                )
                val addition = questionsRef.document(questionId).set(question).await()
                emit(Response.Success(addition))
            } catch (exception: Exception) {
                emit(Response.Error(exception.localizedMessage ?: exception.toString()))
            }
        }

    override fun getQuestionById(uid: String): Flow<Response<Question>> = flow {
        emit(Response.Loading)
        try {
            val question =
                questionsRef.document(uid).get().await().toObject(Question::class.java)
                    ?: Question()
            emit(Response.Success(question))
        } catch (exception: Exception) {
            emit(Response.Error(exception.localizedMessage ?: exception.toString()))
        }
    }

    override fun addAnswer(questionId: String, answerTitle: String)
            : Flow<Response<Void?>> = flow {
        emit(Response.Loading)
        try {
            val answerId =
                questionsRef.document(questionId).collection("answers").document().id
            val answer = Answer(
                answerId,
                title = answerTitle
            )
            val addition =
                questionsRef.document(questionId)
                    .collection("answers")
                    .document(answerId)
                    .set(answer).await()
            emit(Response.Success(addition))
        } catch (exception: Exception) {
            emit(Response.Error(exception.localizedMessage ?: exception.toString()))
        }
    }

    override fun getAnswers(questionId: String): Flow<Response<List<Answer>>> = callbackFlow {
        val snapshot =
            questionsRef.document(questionId)
                .collection("answers").addSnapshotListener { value, error ->
                    val response = if (value != null) {
                        val answers = value.toObjects(Answer::class.java)
                        Response.Success(answers)
                    } else {
                        Response.Error(error?.localizedMessage ?: error.toString())
                    }
                    trySend(response).isSuccess
                }
        awaitClose {
            snapshot.remove()
        }
    }
}
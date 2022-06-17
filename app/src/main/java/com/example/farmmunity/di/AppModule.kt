package com.example.farmmunity.di

import com.example.farmmunity.home.core.HomeConstants
import com.example.farmmunity.home.data.repository.QuestionsRepositoryImpl
import com.example.farmmunity.home.domain.repository.QuestionsRepository
import com.example.farmmunity.home.domain.use_case.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideImageRef(): StorageReference = Firebase.storage.reference

    @Provides
    @Singleton
    fun provideQuestionsRef(firebaseFirestore: FirebaseFirestore): CollectionReference =
        firebaseFirestore.collection(HomeConstants.QUESTIONS)

    @Provides
    @Singleton
    fun provideQuestionsRepository(
        questionsRef: CollectionReference,
        firebaseAuth: FirebaseAuth,
        storageReference: StorageReference
    ): QuestionsRepository =
        QuestionsRepositoryImpl(
            questionsRef = questionsRef,
            firebaseAuth = firebaseAuth,
            storageReference = storageReference
        )

    @Provides
    @Singleton
    fun provideUseCases(questionsRepository: QuestionsRepository): UseCases =
        UseCases(
            getQuestions = GetQuestions(questionsRepository),
            addQuestion = AddQuestion(questionsRepository),
            getQuestionById = GetQuestionById(questionsRepository),
            addAnswer = AddAnswer(questionsRepository),
            getAnswers = GetAnswers(questionsRepository),
            updateAnswerCount = UpdateAnswerCount(questionsRepository)
        )
}
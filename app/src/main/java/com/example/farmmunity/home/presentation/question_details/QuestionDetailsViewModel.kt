package com.example.farmmunity.home.presentation.question_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmmunity.core.AppConstants
import com.example.farmmunity.home.domain.model.Answer
import com.example.farmmunity.home.domain.model.Question
import com.example.farmmunity.home.domain.model.Response
import com.example.farmmunity.home.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionDetailsViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val questionId = savedStateHandle.get<String>("questionId") ?: ""
    var openDialog = mutableStateOf(false)

    private val _questionDetailsState = mutableStateOf<Response<Question>>(Response.Loading)
    val questionDetailsState: State<Response<Question>> = _questionDetailsState

    private val _answersState = mutableStateOf<Response<List<Answer>>>(Response.Loading)
    val answersState: State<Response<List<Answer>>> = _answersState

    private val answerAdditionState = mutableStateOf<Response<Void?>>(Response.Success(null))

    private var answerCount = 0

    init {
        viewModelScope.launch {
            useCases.getQuestionById(questionId).collect {
                _questionDetailsState.value = it
                if (it is Response.Success) {
                    answerCount = it.data.answerCount
                    Log.d(AppConstants.TAG, ": Count == $answerCount")
                }
            }
            useCases.getAnswers(questionId).collect {
                _answersState.value = it
            }
        }
    }

    fun onEvent(questionDetailsEvent: QuestionDetailsEvent) {
        when (questionDetailsEvent) {
            is QuestionDetailsEvent.OnSubmitAnswerClicked -> {
                openDialog.value = false
                viewModelScope.launch {
                    useCases.addAnswer(
                        questionId,
                        questionDetailsEvent.answer
                    ).collect {
                        answerAdditionState.value = it
                        if (it is Response.Success) {
                            answerCount += 1
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
//        Log.d(AppConstants.TAG, "onCleared: ViewModel Cleared & AnswerCount = $answerCount")
        GlobalScope.launch {
            useCases.updateAnswerCount(
                questionId,
                answerCount
            )
        }
    }
}
package com.example.farmmunity.home.presentation.question_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmmunity.home.domain.model.Answer
import com.example.farmmunity.home.domain.model.Question
import com.example.farmmunity.home.domain.model.Response
import com.example.farmmunity.home.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _answerAdditionState = mutableStateOf<Response<Void?>>(Response.Success(null))

    init {
        viewModelScope.launch {
            useCases.getQuestionById(questionId).collect {
                _questionDetailsState.value = it
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
                        _answerAdditionState.value = it
                    }
                }
            }
        }
    }
}
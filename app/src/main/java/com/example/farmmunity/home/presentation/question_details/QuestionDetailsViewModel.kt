package com.example.farmmunity.home.presentation.question_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmmunity.home.domain.model.Question
import com.example.farmmunity.home.domain.model.Response
import com.example.farmmunity.home.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    useCases: UseCases
) : ViewModel() {

    private val questionId = savedStateHandle.get<String>("questionId") ?: ""

    private val _questionDetailsState = mutableStateOf<Response<Question>>(Response.Loading)
    val questionDetailsState: State<Response<Question>> = _questionDetailsState

    init {
        viewModelScope.launch {
            useCases.getQuestionById(questionId).collect {
                _questionDetailsState.value = it
            }
        }
    }
}
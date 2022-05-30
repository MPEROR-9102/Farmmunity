package com.example.farmmunity.home.presentation.questions

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmmunity.core.AppConstants
import com.example.farmmunity.home.domain.model.Question
import com.example.farmmunity.home.domain.model.Response
import com.example.farmmunity.home.domain.use_case.UseCases
import com.example.farmmunity.home.presentation.HomeEvent
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val useCases: UseCases
) : ViewModel() {

    private val _eventChannel = Channel<HomeEvent>()
    val homeEventFlow = _eventChannel.receiveAsFlow()

    private val _questionsState = mutableStateOf<Response<List<Question>>>(Response.Loading)
    val questionsState: State<Response<List<Question>>> = _questionsState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getQuestions()
    }

    fun onEvent(questionsEvent: QuestionsEvent) {
        when (questionsEvent) {
            QuestionsEvent.OnAddQuestionClicked -> {
                viewModelScope.launch {
                    Log.d(AppConstants.TAG, "onEvent: B")
                    _eventFlow.emit(UIEvent.OnAddQuestionClicked)
                }
            }
            QuestionsEvent.OnProfileClicked -> {
                viewModelScope.launch {
                    _eventChannel.send(HomeEvent.ShowAuthenticationScreen)
                }
            }
        }
    }

    fun getUser() = firebaseAuth.currentUser

    private fun getQuestions() {
        viewModelScope.launch {
            useCases.getQuestions().collect { questions ->
                _questionsState.value = questions
            }
        }
    }

    sealed class UIEvent {
        object OnAddQuestionClicked : UIEvent()
    }
}
package com.example.farmmunity.home.presentation.add_question

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmmunity.home.domain.model.Response
import com.example.farmmunity.home.domain.use_case.UseCases
import com.example.farmmunity.home.presentation.HomeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddQuestionViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _eventChannel = Channel<HomeEvent>()
    val homeEventFlow = _eventChannel.receiveAsFlow()

    private val _isQuestionAdded = mutableStateOf<Response<Void?>>(Response.Loading)
    val isQuestionAdded: State<Response<Void?>> = _isQuestionAdded

    private val _showProgressBar = mutableStateOf<Response<Void?>>(Response.Success(null))
    val showProgressBar: State<Response<Void?>> = _showProgressBar

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val questionTitle = mutableStateOf("")
    val questionDescription = mutableStateOf("")
    val uploadUrl = mutableStateOf(Uri.EMPTY)

    fun onEvent(addQuestionEvent: AddQuestionEvent) {
        when (addQuestionEvent) {
            AddQuestionEvent.OnBackNavClicked -> {
                viewModelScope.launch {
                    _eventFlow.emit(UIEvent.ShowQuestionsScreen)
                }
            }
            AddQuestionEvent.OnGalleryClicked -> {
                viewModelScope.launch {
                    _eventChannel.send(HomeEvent.OnGalleryClicked)
                }
            }
            AddQuestionEvent.OnSubmitClicked -> {
                addQuestion()
            }
        }
    }

    private fun addQuestion() {
        viewModelScope.launch {
            try {
                useCases.addQuestion(
                    title = questionTitle.value,
                    description = questionDescription.value,
                    photo = uploadUrl.value
                ).collect {
                    _showProgressBar.value = it
                    _isQuestionAdded.value = it
                }
            } catch (exception: Exception) {
                _eventFlow.emit(UIEvent.ShowSnackBar(exception.message ?: exception.toString()))
            }
        }
    }

    sealed class UIEvent {
        object ShowQuestionsScreen : UIEvent()
        data class ShowSnackBar(val message: String) : UIEvent()
    }
}
package com.ulises.features.event.add.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ulises.common.time.utils.TimeHelper.toISODate
import com.ulises.common.time.utils.TimeHelper.toLocalDate
import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.events.AddScheduledEventUseCase
import com.ulises.events.GetScheduledEventUseCase
import com.ulises.events.UpdateEventUseCase
import com.ulises.features.event.add.models.Actions
import com.ulises.features.event.add.models.UiState
import com.ulises.navigation.Screens
import com.ulises.session.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import models.Kid
import models.ScheduledEvent
import models.User
import outcomes.OutcomeScheduledEvent
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ScheduleDateDetailViewModel @Inject constructor(
    private val dispatchers: ScheduleDispatchers,
    private val addScheduledEventUseCase: AddScheduledEventUseCase,
    private val getScheduledEventUseCase: GetScheduledEventUseCase,
    private val updateEventUseCase: UpdateEventUseCase,
    savedStateHandle: SavedStateHandle,
    userSessionManager: UserSessionManager,
) : ViewModel() {

    private var listOfKids = listOf(Kid("Renata", 0), Kid("Lando", 0))
    private val editItemId = savedStateHandle.toRoute<Screens.AddItem>().id

    //
    private val _uiState = MutableStateFlow(
        UiState(
            kids = listOfKids,
            isFetchingData = editItemId != null,
            isEdit = editItemId != null,
        )
    )
    val uiState = _uiState.asStateFlow()
    private var user: User? = null
    private var event: ScheduledEvent? = null

    //
    private var comments by mutableStateOf("")
    private val textFieldsCombination =
        combine(snapshotFlow { comments }, userSessionManager.userSessionState) { comment, user ->
            this.user = user
            comment.isNotBlank()
        }.map { value ->
            _uiState.update { it.copy(allTextFieldsFilled = value) }
        }.launchIn(viewModelScope)

    init {
        if (editItemId != null) {
            fetchEventData(editItemId)
        }
    }

    fun onHandleIntent(action: Actions.Interaction) {
        when (action) {
            is Actions.Interaction.DisplayCalendarDialog -> onDateDialogVisibilityChange(action.visible)
            is Actions.Interaction.SelectDate -> onDateSelected(action.date)
            is Actions.Interaction.UpdateTextField -> onUpdateTextField(action.type, action.value)
            is Actions.Interaction.SelectKid -> onUpdateSelectedKid(action.kid)
            Actions.Interaction.AddEvent -> onAddSchedule()
            Actions.Interaction.UpdateEvent -> onUpdateEvent()
        }
    }

    fun getTextField(type: TextFieldType): String {
        return when (type) {
            TextFieldType.Comment -> comments
        }
    }

    private fun fetchEventData(eventId: String) {
        viewModelScope.launch {
            runCatching {
                //  Fetch the event
                val event = getScheduledEventUseCase(eventId)
                this@ScheduleDateDetailViewModel.event = event
                val date = LocalDate.parse(event.date)
                Timber.d("$event")
                //  Fill fields with data from event
                comments = event.comments
                _uiState.update {
                    it.copy(
                        isFetchingData = false,
                        selectedDate = date,
                        selectedKids = event.selectedKids
                    )
                }
            }.onFailure {
                Timber.e("Error fetching the event with ID: $eventId", it)
            }
        }
    }

    private fun onDateDialogVisibilityChange(visible: Boolean) {
        _uiState.update { it.copy(isDateDialogVisible = visible) }
    }

    private fun onUpdateTextField(type: TextFieldType, text: String) {
        when (type) {
            is TextFieldType.Comment -> {
                if (text.length <= MAX_COMMENT_LENGTH) {
                    comments = text
                }
            }
        }
    }

    private fun onUpdateSelectedKid(kid: Kid) {
        val list = _uiState.value.selectedKids
        val toggleKid = list.find { it.name == kid.name }
        val newList = if (toggleKid == null) {
            list + setOf(kid)
        } else {
            list - setOf(toggleKid)
        }
        _uiState.update { it.copy(selectedKids = newList) }
    }

    private fun onDateSelected(millis: Long?) {
        viewModelScope.launch(dispatchers.main) {
            runCatching {
                checkNotNull(millis)
                millis.toLocalDate()
            }.onFailure {
                Timber.e(it, "Error parsing Date")
            }.onSuccess { date ->
                Timber.d("New selected time: $date")
                _uiState.update {
                    it.copy(
                        isDateDialogVisible = false,
                        selectedDate = date
                    )
                }
            }
        }
    }

    private fun onAddSchedule() {
        viewModelScope.launch(dispatchers.main) {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                checkNotNull(user)
                val scheduleEvent = OutcomeScheduledEvent(
                    id = Date().time.toString(),
                    date = _uiState.value.selectedDate.toISODate(),
                    createdBy = user!!.name,
                    createdOn = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    comments = comments,
                    createdById = user!!.id,
                    selectedKids = _uiState.value.selectedKids,
                )
                addScheduledEventUseCase(scheduleEvent)
                scheduleEvent
            }.onSuccess { event ->
                Timber.d("Event created: $event")
                _uiState.update { it.copy(isLoading = false, addComplete = true) }
            }.onFailure {
                Timber.e(it, "Error creating this event")
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun onUpdateEvent() {
        viewModelScope.launch(dispatchers.io) {
            runCatching {
                _uiState.update { it.copy(isLoading = true) }
                val outputEvent = OutcomeScheduledEvent(
                    id = event!!.id,
                    date = _uiState.value.selectedDate.toISODate(),
                    createdBy = event!!.createdBy,
                    createdById = event!!.createdBy,
                    createdOn = event!!.createdOn,
                    selectedKids = _uiState.value.selectedKids,
                    comments = comments
                )
                updateEventUseCase(outputEvent)
                outputEvent
            }.onSuccess { response ->
                Timber.d("Event Updated: $response")
                _uiState.update { it.copy(isLoading = false, addComplete = true) }
            }.onFailure {
                Timber.d("Error Updating the event")
                _uiState.update { it.copy(isLoading = false, addComplete = true) }
            }
        }
    }

    companion object {
        const val MAX_COMMENT_LENGTH = 250
    }
}

sealed interface TextFieldType {
    data object Comment : TextFieldType
}
package com.ulises.features.event.add.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.common.time.utils.TimeHelper.toISODate
import com.ulises.common.time.utils.TimeHelper.toLocalDate
import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.events.AddScheduledEventUseCase
import com.ulises.features.event.add.models.Intents
import com.ulises.features.event.add.models.UiState
import com.ulises.session.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import models.AvailableKids
import models.Kid
import models.User
import outcomes.OutcomeScheduledEvent
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ScheduleDateDetailViewModel @Inject constructor(
    private val dispatchers: ScheduleDispatchers,
    private val addScheduledEventUseCase: AddScheduledEventUseCase,
    userSessionManager: UserSessionManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    private var user: User? = null

    //
    private var comments by mutableStateOf("")
    private val textFieldsCombination =
        combine(snapshotFlow { comments }, userSessionManager.userSessionState) { comment, user ->
            this.user = user
            comment.isNotBlank()
        }.map { value ->
            _uiState.update { it.copy(allTextFieldsFilled = value) }
        }.launchIn(viewModelScope)

    fun onHandleIntent(intent: Intents) {
        when (intent) {
            is Intents.DisplayCalendarDialog -> onDateDialogVisibilityChange(intent.visible)
            is Intents.SelectDate -> onDateSelected(intent.date)
            is Intents.UpdateTextField -> onUpdateTextField(intent.type, intent.value)
            is Intents.SelectKid -> onUpdateSelectedKid(intent.kids)
            Intents.AddItem -> onAddSchedule()
            else -> Timber.i("Not handled: $intent")
        }
    }

    fun getTextField(type: TextFieldType): String {
        return when (type) {
            TextFieldType.Comment -> comments
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

    private fun onUpdateSelectedKid(kids: AvailableKids) {
        when (kids) {
            AvailableKids.Ambos -> {
                _uiState.update {
                    it.copy(
                        selectedKids = listOf(
                            AvailableKids.Renata.name,
                            AvailableKids.Lando.name
                        )
                    )
                }
            }

            else -> {
                _uiState.update { it.copy(selectedKids = listOf(kids.name)) }
            }
        }
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
                    rating = 0,
                    comments = comments,
                    createdById = user!!.id,
                    selectedKids = _uiState.value.selectedKids.map { Kid(it, 0) },
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

    companion object {
        const val MAX_COMMENT_LENGTH = 250
    }
}

sealed interface TextFieldType {
    data object Comment : TextFieldType
}
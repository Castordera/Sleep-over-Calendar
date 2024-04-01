package com.ulises.features.events.list.models

import models.Kid
import models.ScheduledEvent

data class UiState(
    val scheduleEvents: List<ScheduledEvent>? = null,
    val isLoading: Boolean = false,
    val showDialogDelete: Boolean = false,
    val showDialogRating: Boolean = false,
    val selectedEvent: ScheduledEvent? = null,
    val selectedKid: Kid? = null,
    val error: String? = null,
)
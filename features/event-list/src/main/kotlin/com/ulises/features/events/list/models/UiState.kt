package com.ulises.features.events.list.models

import models.Kid
import models.ScheduledEvent

data class UiState(
    val scheduleEvents: List<ScheduledEvent>? = null,
    val isLoading: Boolean = false,
    val selectedKid: Kid? = null,
    val error: String? = null,//v1
    val selectedYear: String = "",
    val years: List<String> = emptyList(),
    //  V2
    val deleteDialogState: DeleteDialogState = DeleteDialogState(),
    val message: String = "",
)

data class YearsData(
    val initialYear: String,
    val yearsList: List<String>,
)

data class DeleteDialogState(
    val isVisible: Boolean = false,
    val event: ScheduledEvent? = null,
)
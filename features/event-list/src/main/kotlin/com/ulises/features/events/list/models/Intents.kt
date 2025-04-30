package com.ulises.features.events.list.models

import models.Kid
import models.ScheduledEvent

sealed interface Intents {
    data class ClickItem(val item: ScheduledEvent) : Intents
    data class ChangeRateDialogState(
        val isVisible: Boolean,
        val item: ScheduledEvent?,
        val kid: Kid?
    ) : Intents

    data class ChangeDeleteDialogState(val isVisible: Boolean, val item: ScheduledEvent?) : Intents
    data class UpdateRating(val newRating: Int) : Intents
    data object DeleteItem : Intents
    data object ClearError : Intents
}
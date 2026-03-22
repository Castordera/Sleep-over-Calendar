package com.ulises.features.events.list.models

import models.Kid
import models.ScheduledEvent

sealed interface Actions {
    sealed interface Navigation : Actions {
        data class UpdatePressed(val item: ScheduledEvent) : Navigation
        data object AddPressed : Navigation
        data object GoToProfile : Navigation
    }

    sealed interface Interaction : Actions {
        data class ClickItem(val item: ScheduledEvent) : Interaction
        data class ChangeRateDialogState(
            val isVisible: Boolean,
            val item: ScheduledEvent?,
            val kid: Kid?
        ) : Interaction

        data class ChangeDeleteDialogState(
            val isVisible: Boolean,
            val item: ScheduledEvent?
        ) : Interaction

        data class UpdateRating(val newRating: Int) : Interaction
        data class ShowDeleteDialog(val event: ScheduledEvent) : Interaction
        data object DismissDeleteDialog : Interaction
        data object DeleteItem : Interaction
        data object ClearError : Interaction
        data class ChangeSelectedYear(val year: String) : Interaction
        data class ShowMessage(val message: String) : Interaction
        data object DismissMessage : Interaction
    }
}
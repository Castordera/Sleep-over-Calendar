package com.ulises.features.event.add.models

import com.ulises.features.event.add.ui.TextFieldType
import models.Attendee
import models.Kid

sealed interface Actions {
    sealed interface Interaction : Actions {
        data class DisplayCalendarDialog(val visible: Boolean) : Interaction
        data object DismissCalendarDialog : Interaction
        data class SelectDate(val date: Long?) : Interaction
        data class UpdateTextField(val type: TextFieldType, val value: String) : Interaction
        data class SelectKid(val kid: Kid) : Interaction
        data object AddEvent : Interaction
        data class AddScheduledEventClicked(
            val comments: String
        ) : Interaction

        data class SelectAttendeeClicked(val attendee: Attendee) : Interaction
        data class MoodSelected(val attendee: Attendee, val newMood: Attendee.Mood) : Interaction

        data object UpdateEvent : Interaction
    }

    sealed interface Navigation : Actions {
        data object NavigateBack : Navigation
    }
}
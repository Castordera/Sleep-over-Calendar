package com.ulises.features.event.add.models

import com.ulises.features.event.add.ui.TextFieldType
import models.Kid

sealed interface Actions {
    sealed interface Interaction : Actions {
        data class DisplayCalendarDialog(val visible: Boolean) : Interaction
        data class SelectDate(val date: Long?) : Interaction
        data class UpdateTextField(val type: TextFieldType, val value: String) : Interaction
        data class SelectKid(val kid: Kid) : Interaction
        data object AddEvent : Interaction
        data object UpdateEvent : Interaction
    }

    sealed interface Navigation : Actions {
        data object NavigateBack : Navigation
    }
}
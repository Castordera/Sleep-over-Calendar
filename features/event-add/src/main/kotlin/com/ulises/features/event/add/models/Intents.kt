package com.ulises.features.event.add.models

import com.ulises.features.event.add.ui.TextFieldType
import models.Kid

sealed interface Intents {
    data class DisplayCalendarDialog(val visible: Boolean) : Intents
    data class SelectDate(val date: Long?) : Intents
    data class UpdateTextField(val type: TextFieldType, val value: String) : Intents
    data class SelectKid(val kid: Kid) : Intents
    data object AddEvent : Intents
    data object UpdateEvent : Intents
    data object NavigateBack : Intents
}
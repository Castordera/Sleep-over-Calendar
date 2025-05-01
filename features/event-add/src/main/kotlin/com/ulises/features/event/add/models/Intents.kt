package com.ulises.features.event.add.models

import com.ulises.features.event.add.ui.TextFieldType
import models.AvailableKids

sealed interface Intents {
    data class DisplayCalendarDialog(val visible: Boolean) : Intents
    data class SelectDate(val date: Long?) : Intents
    data class UpdateTextField(val type: TextFieldType, val value: String) : Intents
    data class SelectKid(val kids: AvailableKids) : Intents
    data object AddItem : Intents
    data object NavigateBack : Intents
}
package com.ulises.features.events.list.models

sealed interface DialogType {
    data object Rating: DialogType
    data object Delete: DialogType
}
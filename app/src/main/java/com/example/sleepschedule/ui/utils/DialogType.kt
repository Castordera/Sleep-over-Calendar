package com.example.sleepschedule.ui.utils

sealed interface DialogType {
    object Rating: DialogType
    object Delete: DialogType
}
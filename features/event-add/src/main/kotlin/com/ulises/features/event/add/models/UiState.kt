package com.ulises.features.event.add.models

import java.time.LocalDate

data class UiState(
    val createdText: String = "",
    val comments: String = "",
    val isLoading: Boolean = false,
    val isReadyToSend: Boolean = false,
    val selectedKids: List<String> = emptyList(),
    val addComplete: Boolean = false,
    val isDateDialogVisible: Boolean = false,
    val selectedDate: LocalDate = LocalDate.now()
)
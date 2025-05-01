package com.ulises.features.event.add.models

import java.time.LocalDate

data class UiState(
    val isLoading: Boolean = false,
    val allTextFieldsFilled: Boolean = false,
    val selectedKids: List<String> = emptyList(),
    val addComplete: Boolean = false,
    val isDateDialogVisible: Boolean = false,
    val selectedDate: LocalDate = LocalDate.now()
)
package com.ulises.features.event.add.models

import models.Kid
import java.time.LocalDate

data class UiState(
    val isLoading: Boolean = false,
    val allTextFieldsFilled: Boolean = false,
    val selectedKids: List<Kid> = emptyList(),
    val kids: List<Kid> = emptyList(),
    val addComplete: Boolean = false,
    val isDateDialogVisible: Boolean = false,
    val selectedDate: LocalDate = LocalDate.now()
)
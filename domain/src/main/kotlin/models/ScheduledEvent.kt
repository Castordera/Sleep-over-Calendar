package models

data class ScheduledEvent(
    val id: String,
    val date: String,
    val createdBy: String,
    val createdOn: String,
    val comments: String,
    val isExpanded: Boolean = false,
    val selectedKids: List<Kid>,
)
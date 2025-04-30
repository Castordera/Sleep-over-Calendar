package models

data class ScheduledEvent(
    val id: String,
    val date: String,
    val createdBy: String,
    val createdOn: String,
    val rating: Int,
    val kidName: String,
    val comments: String,
    val isExpanded: Boolean = false,
    val selectedKids: List<Kid>,
)
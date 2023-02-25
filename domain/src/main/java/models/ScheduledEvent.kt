package models

data class ScheduledEvent(
    val id: String,
    val date: String,
    val createdBy: String,
    val createdOn: String,
    val rating: Int,
    val kidName: String
)
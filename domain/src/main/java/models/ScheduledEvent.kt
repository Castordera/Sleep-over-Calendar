package models

data class ScheduledEvent(
    val id: String,
    val date: Long,
    val createdBy: String,
    val createdOn: Long,
    val rating: Int,
    val kidName: String,
    val comments: String
)
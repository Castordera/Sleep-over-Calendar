package outcomes

data class OutcomeScheduledEvent(
    val id: String,
    val date: String,
    val createdBy: String,
    val createdOn: String,
    val rating: Int,
    val kidName: String,
    val comments: String
)
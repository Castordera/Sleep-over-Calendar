package outcomes

data class OutcomeScheduledEvent(
    val id: String,
    val date: Long,
    val createdBy: String,
    val createdOn: Long,
    val rating: Int,
    val kidName: String = "Renata"
)
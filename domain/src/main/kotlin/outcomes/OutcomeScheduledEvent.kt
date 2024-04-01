package outcomes

import models.Kid

data class OutcomeScheduledEvent(
    val id: String,
    val date: String,
    val createdBy: String,
    val createdById: String,
    val createdOn: String,
    val rating: Int,
    val comments: String,
    val selectedKids: List<Kid>,
)
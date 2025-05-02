package outcomes

import androidx.annotation.Keep
import models.Kid

@Keep
data class OutcomeScheduledEvent(
    val id: String,
    val date: String,
    val createdBy: String,
    val createdById: String,
    val createdOn: String,
    val comments: String,
    val selectedKids: List<Kid>,
)
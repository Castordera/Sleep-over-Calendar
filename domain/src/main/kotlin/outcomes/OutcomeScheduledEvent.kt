package outcomes

import androidx.annotation.Keep
import models.Attendee
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

fun List<Attendee>.toKidsList(): List<Kid> {
    return this.map { Kid(it.name, it.mood.toRate()) }
}

private fun Attendee.Mood.toRate(): Int {
    return when (this) {
        Attendee.Mood.GOOD -> 1
        Attendee.Mood.NEUTRAL -> 0
        Attendee.Mood.BAD -> -1
    }
}
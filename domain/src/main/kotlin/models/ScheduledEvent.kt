package models

data class ScheduledEvent(
    val id: String,
    val date: String,
    val createdBy: String,
    val createdOn: String,
    val rating: Int,
    val kidName: String,
    val comments: String,
    val cardFace: CardFace = CardFace.FRONT,
    val selectedKids: List<Kid>,
)

enum class CardFace {
    FRONT,
    BACK
}
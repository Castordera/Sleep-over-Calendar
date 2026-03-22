package models

data class Attendee(
    val name: String,
    val mood: Mood,
) {
    enum class Mood {
        GOOD,
        NEUTRAL,
        BAD,
    }
}

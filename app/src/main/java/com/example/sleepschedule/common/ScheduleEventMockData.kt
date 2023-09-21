package com.example.sleepschedule.common

import models.CardFace
import models.ScheduledEvent

val scheduleEventMockList = listOf(
    ScheduledEvent(
        id = "111-222-333-444-555",
        date = "2023-02-05",
        createdBy = "Ulises the number one",
        rating = 1,
        createdOn = "2023-03-12T21:11:48.789045",
        kidName = "Renata",
        comments = "This is my comment just to provide some extra data to fill in and use more space that it might require, but lets see how this ends",
        cardFace = CardFace.FRONT
    ),
    ScheduledEvent(
        id = "112-222-333-444-555",
        date = "2023-03-05",
        createdBy = "Ulises the number one",
        rating = 1,
        createdOn = "2023-03-12T21:11:48.789045",
        kidName = "Renata",
        comments = "This is my comment just to provide some extra data to fill in and use more space that it might require, but lets see how this ends",
        cardFace = CardFace.FRONT
    ),
    ScheduledEvent(
        id = "113-222-333-444-555",
        date = "2023-04-05",
        createdBy = "Ulises the number one",
        rating = 1,
        createdOn = "2023-03-12T21:11:48.789045",
        kidName = "Renata",
        comments = "This is my comment just to provide some extra data to fill in and use more space that it might require, but lets see how this ends",
        cardFace = CardFace.FRONT
    )
)
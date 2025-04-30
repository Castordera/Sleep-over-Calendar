package com.ulises.features.events.list.utils

import models.Kid
import models.ScheduledEvent

val scheduleEventMockList = listOf(
    ScheduledEvent(
        id = "111-222-333-444-555",
        date = "2023-07-28",
        createdBy = "Ulises the number one",
        rating = 0,
        createdOn = "2023-03-12T21:11:48.789045",
        kidName = "",
        comments = "This is my comment just to provide some extra data to fill in and use more space that it might require, but lets see how this ends",
        selectedKids = listOf(Kid("Renata", 0), Kid("Lando", 1), Kid("Another", -1)),
    ),
    ScheduledEvent(
        id = "112-222-333-444-555",
        date = "2023-03-05",
        createdBy = "Ulises the number one",
        rating = -1,
        createdOn = "2023-03-12T21:11:48.789045",
        kidName = "Renata",
        comments = "This is my comment just to provide some extra data to fill in and use more space that it might require, but lets see how this ends",
        selectedKids = emptyList(),
    ),
    ScheduledEvent(
        id = "113-222-333-444-555",
        date = "2023-04-05",
        createdBy = "Ulises the number one",
        rating = 0,
        createdOn = "2023-03-12T21:11:48.789045",
        kidName = "Renata",
        comments = "This is my comment just to provide some extra data to fill in and use more space that it might require, but lets see how this ends",
        selectedKids = emptyList(),
    )
)
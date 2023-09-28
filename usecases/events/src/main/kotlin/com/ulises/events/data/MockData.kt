package com.ulises.events.data

import models.ScheduledEvent
import outcomes.OutcomeScheduledEvent

val outcomeEvent = OutcomeScheduledEvent(
    id = "123",
    date = "This date",
    createdBy = "Demo user",
    createdOn = "Today",
    createdById = "111",
    rating = 0,
    kidName = "Kid name",
    comments = "Demo comments"
)

val event = ScheduledEvent(
    id = "123",
    date = "This date",
    createdBy = "Demo user",
    createdOn = "Today",
    rating = 0,
    kidName = "Kid name",
    comments = "Demo comments"
)
package com.ulises.events.data

import models.ScheduledEvent
import outcomes.OutcomeScheduledEvent

val outcomeEvent = OutcomeScheduledEvent(
    id = "123",
    date = "This date",
    createdBy = "Demo user",
    createdOn = "Today",
    createdById = "111",
    comments = "Demo comments",
    selectedKids = emptyList()
)

val event = ScheduledEvent(
    id = "123",
    date = "This date",
    createdBy = "Demo user",
    createdOn = "Today",
    comments = "Demo comments",
    selectedKids = emptyList(),
)
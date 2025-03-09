package com.ptit.event.entities.values;

import com.ptit.event.entities.enums.EventState;

import java.sql.Timestamp;

public class EventFilter {
    private Long ownerId;
    private String name;
    private EventState state;
    private Timestamp startDate;
    private Timestamp endDate;
}

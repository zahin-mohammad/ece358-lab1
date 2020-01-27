class Event {
    EventType type;
    double occurrenceTime;

    Event(EventType type, double occurrenceTime) {
        this.type = type;
        this.occurrenceTime = occurrenceTime;
    }
}

enum EventType {
    ARRIVAL, DEPARTURE, OBSERVE
}

class Event {
    EventType type;
    double occurrenceTime;

    Event(EventType type, double occurrenceTime) {
        this.type = type;
        this.occurrenceTime = occurrenceTime;
    }
}

class Departure extends Event {
    Event arrival;
    Departure(EventType type, double occurrenceTime, Event arrival) {
        super(type, occurrenceTime);
        assert arrival.type == EventType.ARRIVAL;
        this.arrival = arrival;
    }
}

enum EventType {
    ARRIVAL, DEPARTURE, OBSERVE
}

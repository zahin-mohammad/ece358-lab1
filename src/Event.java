class Event {
    EventType type;
    double occurrenceTime;

    Event(EventType type, double occurrenceTime) {
        this.type = type;
        this.occurrenceTime = occurrenceTime;
    }
}

class Arrival extends Event {
    double transmissionTime;

    Arrival(double occurrenceTime, double transmissionTime) {
        super(EventType.ARRIVAL, occurrenceTime);
        this.transmissionTime = transmissionTime;
    }
}

enum EventType {
    ARRIVAL, DEPARTURE, OBSERVE
}

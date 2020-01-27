import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

public class Simulation {
    private double simulationTime;
    private double packetLength;
    private double packetGenerationAvg;
    private double linkCapacity;
    private int bufferSize = -1;

    Simulation(SimulationParams simulationParams){
        this.simulationTime = simulationParams.simulationTime;
        this.packetLength = simulationParams.packetLength;
        this.packetGenerationAvg =
                (simulationParams.queueUtilization * simulationParams.linkCapacity)/simulationParams.packetLength;
        this.linkCapacity = simulationParams.linkCapacity;
        this.bufferSize = simulationParams.bufferSize;
    }

    public SimulationResponse runSimulation() {
        ArrayList<Event> eventQueue = generateEvents();
        LinkedList<Event> buffer = new LinkedList<>();
        HashSet<Event> packetsDropped = new HashSet<>();

        double packetArrivalCounter = 0.0;
        double idleCounter = 0.0;
        double observerCounter = 0.0;
        double averageNumPacketsInBuffer = 0.0;

        for (Event event : eventQueue) {
            switch (event.type){
                case ARRIVAL:
                    packetArrivalCounter++;
                    if (this.bufferSize >-1 && buffer.size()>=this.bufferSize){
                       packetsDropped.add(event);
                    }else{
                        buffer.add(event);
                    }
                    break;
                case DEPARTURE:
                    if (!packetsDropped.contains(((Departure) event).arrival)){
                        assert buffer.size() != 0;
                        buffer.pop();
                    }
                    break;
                case OBSERVE:
                    // Increment idle time whenever packet arrives to an empty buffer
                    if (buffer.isEmpty()){
                        idleCounter++;
                    }
                    averageNumPacketsInBuffer += buffer.size();
                    observerCounter++;
                    break;
            }
        }
        return new SimulationResponse(
                idleCounter / observerCounter,
                (double) packetsDropped.size() / packetArrivalCounter,
                averageNumPacketsInBuffer / observerCounter);
    }

    private  ArrayList<Event> generateEvents() {
        ArrayList<Event> eventQueue = new ArrayList<>();
        ArrayList<Event> arrivals = new ArrayList<>();
        ArrayList<Event> departures = new ArrayList<>();
        ArrayList<Event> observers = new ArrayList<>();

        PoissonDistribution arrivalDistribution = new PoissonDistribution(this.packetGenerationAvg);
        PoissonDistribution transmissionPacketLengthDistribution = new PoissonDistribution(1.0/this.packetLength);
        PoissonDistribution observerDistribution = new PoissonDistribution(this.packetGenerationAvg *5);

        double currentTime = 0.0;
        // Generate events up until simulation time
        while (currentTime < simulationTime) {
            double departureOccurrenceTime = currentTime;
            // Packet has to wait until previous packet has departed
            if (!departures.isEmpty() && departures.get(departures.size()-1).occurrenceTime > currentTime){
                departureOccurrenceTime = departures.get(departures.size()-1).occurrenceTime;
            }
            double transitionTime = transmissionPacketLengthDistribution.generateTimeInterval() / this.linkCapacity;
            departureOccurrenceTime += transitionTime;

            Event newArrival = new Event(EventType.ARRIVAL,currentTime);
            Event newDeparture = new Departure(EventType.DEPARTURE, departureOccurrenceTime, newArrival);
            departures.add(newDeparture);
            arrivals.add(newArrival);
            currentTime += arrivalDistribution.generateTimeInterval();
        }

        currentTime = 0.0;
        while (currentTime < simulationTime){
            double observationTime = observerDistribution.generateTimeInterval();
            Event newObserver = new Event(EventType.OBSERVE, currentTime);
            observers.add(newObserver);
            currentTime += observationTime;
        }

        eventQueue.addAll(arrivals);
        eventQueue.addAll(departures);
        eventQueue.addAll(observers);
        eventQueue.sort(Comparator.comparingDouble(s -> s.occurrenceTime));
        return eventQueue;
    }
}

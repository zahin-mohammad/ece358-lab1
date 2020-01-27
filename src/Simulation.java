import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class Simulation {
    private double simulationTime = 1000.0;
    private int packetLength;
    private double packetGenerationAvg;
    private int bufferSize = -1;

    Simulation(int packetLength, int linkCapacity, double simulationTime, double queueUtilization){
        this.simulationTime = simulationTime;
        this.packetLength = packetLength;
        this.packetGenerationAvg = (queueUtilization * linkCapacity)/ packetLength;
    }
    Simulation(int packetLength, int linkCapacity, double simulationTime, double queueUtilization, int bufferSize){
        this.simulationTime = simulationTime;
        this.packetLength = packetLength;
        this.packetGenerationAvg = (queueUtilization * linkCapacity)/ packetLength;
        this.bufferSize = bufferSize;
    }

    public SimulationResponse runSimulation() {
        ArrayList<Event> eventQueue = generateEvents();
        LinkedList<Event> buffer = new LinkedList<Event>();

        int packetArrivalCounter = 0;
        int packetDepartureCounter = 0;

        double idleTime = 0.0;
        double lastDepartureTime = 0.0;
        double observerCounter = 0.0;
        double packetsDropped = 0.0;

        double averageNumPacketsInBuffer = 0.0;

        for (Event event : eventQueue) {
            switch (event.type){
                case ARRIVAL:
                    if (buffer.isEmpty()){
                        idleTime += event.occurrenceTime - lastDepartureTime;
                    }
                    packetArrivalCounter++;
                    if (this.bufferSize >-1 && buffer.size()>=this.bufferSize){
                        packetsDropped++;
                    }else{
                        buffer.add(event);
                    }
                    break;
                case DEPARTURE:
                    lastDepartureTime = event.occurrenceTime;
                    packetDepartureCounter++;
                    if (!buffer.isEmpty()){
                        buffer.pop();
                    }
                    break;
                case OBSERVE:
                    averageNumPacketsInBuffer += buffer.size();
                    observerCounter++;
                    break;
            }
        }
        return new SimulationResponse(
                (idleTime/simulationTime),
                (packetsDropped/packetArrivalCounter),
                (averageNumPacketsInBuffer/observerCounter));
    }

    private  ArrayList<Event> generateEvents() {
        ArrayList<Event> eventQueue = new ArrayList<>();

        ArrayList<Arrival> arrivals = new ArrayList<>();
        ArrayList<Event> departures = new ArrayList<>();
        ArrayList<Event> observers = new ArrayList<>();

        PoissonDistribution arrivalDistribution = new PoissonDistribution(this.packetGenerationAvg);
        PoissonDistribution transmissionTimeDistribution = new PoissonDistribution(this.packetLength);
        PoissonDistribution observerDistribution = new PoissonDistribution(this.packetGenerationAvg *5);

        double currentTime = 0.0;
        while (currentTime < simulationTime) {
            double departureOccurrenceTime = currentTime;
            if (!departures.isEmpty() && departures.get(departures.size()-1).occurrenceTime > currentTime){
                departureOccurrenceTime = departures.get(departures.size()-1).occurrenceTime;
            }
            departureOccurrenceTime += transmissionTimeDistribution.generateTimeInterval();

            Arrival newArrival = new Arrival(currentTime, transmissionTimeDistribution.generateTimeInterval());
            Event newDeparture = new Event(EventType.DEPARTURE, departureOccurrenceTime);
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

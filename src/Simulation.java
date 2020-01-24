import java.util.ArrayList;
import java.util.Comparator;

public class Simulation {
    private static double simulationTime = 1000.0;

    public static void main(String[] args) {
        ArrayList<Event> eventQueue = generateEvents(simulationTime);
        runSimulation(eventQueue);
    }

    private static void runSimulation(ArrayList<Event> eventQueue) {
        for (Event event : eventQueue) {
            System.out.println(event.type.toString() + " at " + event.occurrenceTime);
        }
    }

    private static ArrayList<Event> generateEvents(double simulationTime) {
        ArrayList<Event> eventQueue = new ArrayList<>();

        // Generate Arrivals
        ArrayList<Arrival> arrivals = new ArrayList<>();
        PoissonDistribution arrivalDistribution = new PoissonDistribution(1000);
        double currentTime = 0.0;
        while (currentTime < simulationTime) {
            // Hardcoded transmissionTime for now, should be a function of packet size (also needs to be in Arrival)
            Arrival newArrival = new Arrival(EventType.ARRIVAL, currentTime, 0.001);
            arrivals.add(newArrival);
            currentTime += arrivalDistribution.generateTimeInterval();
        }

        // Generate Departures
        ArrayList<Event> departures = new ArrayList<>();


        // Generate Observers
        ArrayList<Event> observers = new ArrayList<>();

        eventQueue.addAll(arrivals);
        eventQueue.addAll(departures);
        eventQueue.addAll(observers);
        eventQueue.sort(Comparator.comparingDouble(s -> s.occurrenceTime));
        return eventQueue;
    }
}

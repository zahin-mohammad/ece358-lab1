import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class Simulation {
    private double simulationTime = 1000.0;
    private int c;
    private int l;
    private double roh;
    private double lambda;
    Simulation(int l, int c, double simulationTime, double roh){
        this.simulationTime = simulationTime;
        this.l = l;
        this.c = c;
        this.roh = roh;
        this.lambda = (roh*c)/l;
    }

    public void runSimulation() {
        ArrayList<Event> eventQueue = generateEvents();
        LinkedList<Event> buffer = new LinkedList<Event>();

        for (Event event : eventQueue) {
            System.out.println(event.type.toString().charAt(0) + "\t,at,\t" + event.occurrenceTime);
            switch (event.type){
                case ARRIVAL:
                    buffer.add(event);
                    break;
                case DEPARTURE:
                    buffer.pop();
                    break;
                case OBSERVE:
                    break;
            }
        }
        System.out.println("Buffer Size: "+buffer.size());
    }

    private  ArrayList<Event> generateEvents() {
        ArrayList<Event> eventQueue = new ArrayList<>();

        ArrayList<Arrival> arrivals = new ArrayList<>();
        ArrayList<Event> departures = new ArrayList<>();
        ArrayList<Event> observers = new ArrayList<>();

        PoissonDistribution arrivalDistribution = new PoissonDistribution(this.lambda);
        PoissonDistribution transmissionTimeDistribution = new PoissonDistribution(this.l);
        PoissonDistribution observerDistribution = new PoissonDistribution(this.lambda*5);

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

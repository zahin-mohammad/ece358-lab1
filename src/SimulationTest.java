import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

class SimulationTest {

    public static void main(String[] args) {
        System.out.println("Simulation: Vary Time Infinite Buffer");
        int simulationTimeStep = 1000;
        ArrayList<SimulationResponse> simulationResponseList = new ArrayList<>();
        for (int i=1; i<11; i++){
            System.out.printf("Test %d with time %d\n", i, simulationTimeStep*i);

            Simulation simulation = new Simulation(
                    2000, 1000000, simulationTimeStep*i, 0.25);
            SimulationResponse simulationResponse = simulation.runSimulation();
            simulationResponseList.add(simulationResponse);
            printSimulationResponse(simulationResponse);
        }

        try {
            createCSV(simulationResponseList, "./vary-time-infinite-buffer.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Simulation: Vary Time Finite Buffer");
        simulationTimeStep = 1000;
        for (int i=1; i<11; i++){
            System.out.printf("Test %d with time %d\n", i, simulationTimeStep*i);

            Simulation simulation = new Simulation(
                    2000, 1000000, simulationTimeStep*i, 4, 10);
            SimulationResponse simulationResponse = simulation.runSimulation();
            printSimulationResponse(simulationResponse);
        }

        System.out.println("Simulation: Vary Queue Utilization Infinite Buffer");
        simulationResponseList = new ArrayList<>();
        for (double i = 0.25; i <= 0.95; i += 0.1){
            System.out.printf("Test with roh %f\n", i);
            Simulation simulation = new Simulation(
                    2000, 1000000, 1000, i);
            SimulationResponse simulationResponse  = simulation.runSimulation();
            simulationResponseList.add(simulationResponse);
            printSimulationResponse(simulationResponse);
        }

        try {
            createCSV(simulationResponseList, "./vary-queue-utilization-infinite-buffer.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Simulation: 1.2 Queue Utilization Infinite Buffer");
        Simulation simulation = new Simulation(
                2000, 1000000, 1000, 1.2);
        SimulationResponse simulationResponse  = simulation.runSimulation();
        printSimulationResponse(simulationResponse);
    }

    private static void printSimulationResponse(SimulationResponse simulationResponse){
//        System.out.printf("Pidle, the proportion of the time that the buffer is empty: %f\n", simulationResponse.pIdle);
//        System.out.printf("Ploss, the probability of a packet being dropped (only relevant for finite buffers): %f\n",
//        simulationResponse.pLoss);
//        System.out.printf("E[N], the average number of packets in the buffer during the simulation: %f\n",
//        simulationResponse.avgPacketsInBuffer);
    }
    private static void createCSV(List<SimulationResponse> simulationResponseList, String csvName) throws IOException {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("E[N],pIdel, pLoss\n");

        PrintWriter printWriter = new PrintWriter(new FileWriter(csvName));
        printWriter.printf("E[N],pIdel,pLoss\n");

        for (SimulationResponse simulationResponse: simulationResponseList){
//            String row = String.format("%f,%f,%f\n",
//                    simulationResponse.avgPacketsInBuffer, simulationResponse.pIdle, simulationResponse.pLoss);
            printWriter.printf("%f,%f,%f\n",
                    simulationResponse.avgPacketsInBuffer, simulationResponse.pIdle, simulationResponse.pLoss);
//            stringBuilder.append(row);
        }
        printWriter.close();
    }

}


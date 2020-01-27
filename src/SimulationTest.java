import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

class SimulationTest {

    public static void main(String[] args) {
        q3();
//        System.out.println("Simulation: Vary Time Infinite Buffer");
//        int simulationTimeStep = 1000;
//        ArrayList<SimulationResponse> simulationResponseList = new ArrayList<>();
//        for (int i=1; i<11; i++){
//            System.out.printf("Test %d with time %d\n", i, simulationTimeStep*i);
//
//            Simulation simulation = new Simulation(
//                    2000, 1000000, simulationTimeStep*i, 0.25);
//            SimulationResponse simulationResponse = simulation.runSimulation();
//            simulationResponseList.add(simulationResponse);
//            printSimulationResponse(simulationResponse);
//        }
//
//        try {
//            createCSV(simulationResponseList, "./vary-time-infinite-buffer.csv");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Simulation: Vary Time Finite Buffer");
//        simulationTimeStep = 1000;
//        for (int i=1; i<11; i++){
//            System.out.printf("Test %d with time %d\n", i, simulationTimeStep*i);
//
//            Simulation simulation = new Simulation(
//                    2000, 1000000, simulationTimeStep*i, 4, 10);
//            SimulationResponse simulationResponse = simulation.runSimulation();
//            printSimulationResponse(simulationResponse);
//        }
//
//
//
//        System.out.println("Simulation: 1.2 Queue Utilization Infinite Buffer");
//        Simulation simulation = new Simulation(
//                2000, 1000000, 1000, 1.2);
//        SimulationResponse simulationResponse  = simulation.runSimulation();
//        printSimulationResponse(simulationResponse);
    }

    private static void q1(){
        int lambda = 75;
        int loop = 1000;
        ArrayList<Double> data = new ArrayList<>();
        PoissonDistribution distribution = new PoissonDistribution(lambda);

        for (int i =0; i<loop; i++){
            data.add(distribution.generateTimeInterval());
        }
        System.out.printf("q1 results:\n");
        System.out.printf("\tlambda: %d", lambda);
        System.out.printf("\titerations: %d", loop);
        System.out.printf("\tmean: %f", getMean(data));
        System.out.printf("\tvariance: %f", getVariance(data));
    }

    private static void q3(){
        System.out.println("Simulation: Vary Queue Utilization Infinite Buffer");
        ArrayList<SimulationResponse> simulationResponseList = new ArrayList<>();
        ArrayList<SimulationParams> simulationParamsList = new ArrayList<>();

        for (double i = 0.25; i <= 0.95; i += 0.1){
            SimulationParams simulationParams = new SimulationParams(
                    1000,2000, 1000000,i);
            Simulation simulation = new Simulation(simulationParams);
            SimulationResponse simulationResponse  = simulation.runSimulation();
            simulationResponseList.add(simulationResponse);
            simulationParamsList.add(simulationParams);
        }

        try {
            createCSV(simulationResponseList, simulationParamsList, "./output/vary-queue-utilization-infinite-buffer.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void printSimulationResponse(SimulationResponse simulationResponse){
        System.out.printf("Pidle, the proportion of the time that the buffer is empty: %f\n", simulationResponse.pIdle);
        System.out.printf("Ploss, the probability of a packet being dropped (only relevant for finite buffers): %f\n",
        simulationResponse.pLoss);
        System.out.printf("E[N], the average number of packets in the buffer during the simulation: %f\n",
        simulationResponse.avgPacketsInBuffer);
    }

    private static void createCSV(List<SimulationResponse> simulationResponseList, String csvName) throws IOException {

        PrintWriter printWriter = new PrintWriter(new FileWriter(csvName));
        printWriter.printf("E[N],pIdel,pLoss\n");

        for (SimulationResponse simulationResponse: simulationResponseList){
            printWriter.printf("%f,%f,%f\n",
                    simulationResponse.avgPacketsInBuffer, simulationResponse.pIdle, simulationResponse.pLoss);
        }
        printWriter.close();
    }

    private static void createCSV(
            List<SimulationResponse> simulationResponseList,
            List<SimulationParams> simulationParamsList,
            String csvName
    ) throws IOException {

        PrintWriter printWriter = new PrintWriter(new FileWriter(csvName));
        printWriter.printf("SimulationTime,BufferSize,LinkCapacity,AvgPacketLength,QueueUtilization,E[N],pIdel,pLoss\n");

        for (int i = 0; i < simulationResponseList.size(); i++){
            SimulationResponse simulationResponse = simulationResponseList.get(i);
            SimulationParams simulationParams = simulationParamsList.get(i);

            printWriter.printf("%f,%d,%d,%d,%f%f,%f,%f\n",
                    simulationParams.simulationTime, simulationParams.bufferSize, simulationParams.linkCapacity,
                    simulationParams.packetLength, simulationParams.queueUtilization,
                    simulationResponse.avgPacketsInBuffer, simulationResponse.pIdle, simulationResponse.pLoss);
        }
        printWriter.close();
    }

    static Double getMean(List<Double> data) {
        double sum = 0;
        for (double a: data ){
            sum+=a;
        }
        return sum/data.size();
    }

    static Double getVariance(List<Double> data) {
        double mean = getMean(data);
        double sum = 0;
        for(double a :data) {
            sum += (a-mean)*(a-mean);
        }
        return sum/(data.size());
    }

}


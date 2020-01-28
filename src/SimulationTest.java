import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

class SimulationTest {

    public static void main(String[] args) {
        HashSet<String> argsMap = new HashSet<>(Arrays.asList(args));
        File directory = new File("./output");
        if (! directory.exists()){
            directory.mkdir();
        }

        if (argsMap.contains("q1") || argsMap.contains("Q1") || args.length <=1){
           q1();
        }
        if (argsMap.contains("q3") || argsMap.contains("Q3") || args.length <=1){
            q3();
        }
        if (argsMap.contains("q4") || argsMap.contains("Q4") || args.length <=1){
            q4();
        }
        if (argsMap.contains("q6") || argsMap.contains("Q6") || args.length <=1){
            q6();
        }
    }

    private static void q1(){
        int lambda = 75;
        int loop = 1000;
        ArrayList<Double> data = new ArrayList<>();
        PoissonDistribution distribution = new PoissonDistribution(lambda);

        for (int i =0; i<loop; i++){
            data.add(distribution.generateTimeInterval());
        }
        ArrayList<String> rows = new ArrayList<>();
        double mean = getMean(data);
        double variance = getVariance(data);

        rows.add("lambda, Iterations,Mean,1/Mean,Variance,sqrt(1/Variance)");
        rows.add(String.format("%d,%d,%f,%f,%f,%f", lambda, loop, mean, 1.0/mean, variance, Math.sqrt(1.0/variance)));
        try {
            createCSV(rows, "./output/q1.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void q3(){
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
            createCSV(simulationResponseList, simulationParamsList, "./output/q3.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void q4(){
        ArrayList<SimulationResponse> simulationResponseList = new ArrayList<>();
        ArrayList<SimulationParams> simulationParamsList = new ArrayList<>();

        SimulationParams simulationParams = new SimulationParams(
                1000,2000, 1000000,1.2);
        Simulation simulation = new Simulation(simulationParams);
        SimulationResponse simulationResponse  = simulation.runSimulation();
        simulationResponseList.add(simulationResponse);
        simulationParamsList.add(simulationParams);

        try {
            createCSV(simulationResponseList, simulationParamsList, "./output/q4.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void q6(){
        ArrayList<SimulationResponse> simulationResponseList = new ArrayList<>();
        ArrayList<SimulationParams> simulationParamsList = new ArrayList<>();

        for (double i = 0.5; i <= 1.5; i += 0.1){
            SimulationParams simulationParams1 = new SimulationParams(
                    1000,2000, 1000000,i,10);
            simulationParamsList.add(simulationParams1);
            simulationResponseList.add(new Simulation(simulationParams1).runSimulation());

            SimulationParams simulationParams2 = new SimulationParams(
                    1000,2000, 1000000,i,25);
            simulationParamsList.add(simulationParams2);
            simulationResponseList.add(new Simulation(simulationParams2).runSimulation());

            SimulationParams simulationParams3 = new SimulationParams(
                    1000,2000, 1000000,i,50);
            simulationParamsList.add(simulationParams3);
            simulationResponseList.add(new Simulation(simulationParams3).runSimulation());
        }

        try {
            createCSV(simulationResponseList, simulationParamsList, "./output/q6.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createCSV(List<String> rows, String csvName) throws IOException {

        PrintWriter printWriter = new PrintWriter(new FileWriter(csvName));
        for (String row: rows){
            printWriter.println(row);
        }
        printWriter.close();
    }

    private static void createCSV(
            List<SimulationResponse> simulationResponseList,
            List<SimulationParams> simulationParamsList,
            String csvName
    ) throws IOException {

        PrintWriter printWriter = new PrintWriter(new FileWriter(csvName));
        printWriter.printf("SimulationTime,BufferSize,LinkCapacity,AvgPacketLength,QueueUtilization,E[N],pIdle,pLoss\n");

        for (int i = 0; i < simulationResponseList.size(); i++){
            SimulationResponse simulationResponse = simulationResponseList.get(i);
            SimulationParams simulationParams = simulationParamsList.get(i);

            printWriter.printf("%f,%d,%d,%d,%f,%f,%f,%f\n",
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


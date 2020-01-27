
class SimulationTest {

    public static void main(String[] args) {
        System.out.println("Simulation: Vary Time Infinite Buffer");
        int simulationTimeStep = 1000;
        for (int i=1; i<11; i++){
            System.out.printf("Test %d with time %d\n", i, simulationTimeStep*i);

            Simulation simulation = new Simulation(
                    2000, 1000000, simulationTimeStep*i, 0.25);
            simulation.runSimulation();
        }

        System.out.println("Simulation: Vary Time Finite Buffer");
        simulationTimeStep = 1000;
        for (int i=1; i<11; i++){
            System.out.printf("Test %d with time %d\n", i, simulationTimeStep*i);

            Simulation simulation = new Simulation(
                    2000, 1000000, simulationTimeStep*i, 4, 10);
            simulation.runSimulation();
        }

        System.out.println("Simulation: Vary Queue Utilization Infinite Buffer");
        for (double i = 0.25; i <= 0.95; i += 0.1){
            System.out.printf("Test with roh %f\n", i);
            Simulation simulation = new Simulation(
                    2000, 1000000, 1000, i);
            simulation.runSimulation();
        }

        System.out.println("Simulation: Vary Queue Utilization Infinite Buffer");
        for (double i = 0.25; i <= 0.95; i += 0.1){
            System.out.printf("Test with roh %f\n", i);
            Simulation simulation = new Simulation(
                    2000, 1000000, 1000, i);
            simulation.runSimulation();
        }

        System.out.println("Simulation: 1.2 Queue Utilization Infinite Buffer");
        Simulation simulation = new Simulation(
                2000, 1000000, 1000, 1.2);
        simulation.runSimulation();
    }
}
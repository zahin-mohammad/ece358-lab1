import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void runSimulationVaryTimeInfiniteBuffer() {
        int simulationTimeStep = 1000;
        for (int i=1; i<11; i++){
            System.out.printf("Test %d with time %d\n", i, simulationTimeStep*i);

            Simulation simulation = new Simulation(
                    2000, 1000000, simulationTimeStep*i, 0.25);
            simulation.runSimulation();
        }
    }

    @Test
    void runSimulationVaryTimeFiniteBuffer() {
        int simulationTimeStep = 1000;
        for (int i=1; i<11; i++){
            System.out.printf("Test %d with time %d\n", i, simulationTimeStep*i);

            Simulation simulation = new Simulation(
                    2000, 1000000, simulationTimeStep*i, 4, 10);
            simulation.runSimulation();
        }
    }

    @Test
    void runSimulationVaryQueueUtilizationInfiniteBuffer() {
        for (double i = 0.25; i <= 0.95; i += 0.1){
            System.out.printf("Test with roh %f\n", i);
            Simulation simulation = new Simulation(
                    2000, 1000000, 1000, i);
            simulation.runSimulation();
        }
    }

    @Test
    void runSimulationQueueUtilizationConstantInfiniteBuffer() {
        Simulation simulation = new Simulation(
                2000, 1000000, 1000, 1.2);
        simulation.runSimulation();
    }
}
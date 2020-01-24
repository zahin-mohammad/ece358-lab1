import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void runSimulation() {
        int simulationTimeStep = 1000;
        for (int i=1; i<11; i++){
            System.out.printf("Test %d with time %d\n", i, simulationTimeStep*i);
            Simulation simulation = new Simulation(
                    2000, 1000000, simulationTimeStep*i, 0.25);
            simulation.runSimulation();
        }

    }
}
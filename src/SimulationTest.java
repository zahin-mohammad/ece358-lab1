import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void runSimulation() {
        Simulation simulation = new Simulation(2000, 1000000, 8, 0.25);
        simulation.runSimulation();
    }
}
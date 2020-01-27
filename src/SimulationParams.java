public class SimulationParams {
    double simulationTime;
    int packetLength;
    int linkCapacity;
    double queueUtilization;
    int bufferSize = -1;

    public SimulationParams(double simulationTime, int packetLength, int linkCapacity, double queueUtilization, int bufferSize) {
        this.simulationTime = simulationTime;
        this.packetLength = packetLength;
        this.linkCapacity = linkCapacity;
        this.queueUtilization = queueUtilization;
        this.bufferSize = bufferSize;
    }
    public SimulationParams(double simulationTime, int packetLength, int linkCapacity, double queueUtilization) {
        this.simulationTime = simulationTime;
        this.packetLength = packetLength;
        this.linkCapacity = linkCapacity;
        this.queueUtilization = queueUtilization;
    }
}

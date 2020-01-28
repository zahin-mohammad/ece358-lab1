public class SimulationResponse {
    double pIdle;
    double pLoss;
    double avgPacketsInBuffer;

    public SimulationResponse(double pIdle, double pLoss, double avgPacketsInBuffer) {
        this.pIdle = pIdle;
        this.pLoss = pLoss;
        this.avgPacketsInBuffer = avgPacketsInBuffer;
    }
}

import java.util.Random;

class PoissonDistribution {
    private int lambda;
    private Random r;
    PoissonDistribution(int lambda) {
        this.lambda = lambda;
        // seeded with time
        this.r = new Random();
    }

    double generateTimeInterval() {
        double U = r.nextDouble();
        return (-1.0/lambda) * Math.log(1.0-U);
    }
}

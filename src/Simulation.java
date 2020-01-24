public class Simulation {

    private static PoissonDistribution poissonDistribution = new PoissonDistribution(1000);

    public static void main(String[] args) {
        double sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += (poissonDistribution.generateTimeInterval());
        }
        System.out.println(sum/100.0);
        System.out.println(1.0/1000.0);
    }
}

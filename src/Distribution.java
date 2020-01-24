import java.util.Random;

class Distribution {
    private int lambda;
    Distribution(int lambda) {
        this.lambda = lambda;
    }

    double generateTimeInterval() {
        Random r = new Random();

        double U = r.nextDouble();
        return (-1.0/lambda) * Math.log(1.0-U);
    }
}

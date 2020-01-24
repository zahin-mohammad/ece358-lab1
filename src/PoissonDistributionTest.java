import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PoissonDistributionTest {
    @org.junit.jupiter.api.Test
    void generateTimeInterval() {
        int lambda = 75;
        int loop = 1000;
        ArrayList<Double> data = new ArrayList<>();
        PoissonDistribution distribution = new PoissonDistribution(lambda);

        for (int i =0; i<loop; i++){
            data.add(distribution.generateTimeInterval());
        }

        assertEquals(1.0/lambda, getMean(data), 0.0009);
        assertEquals(1.0/(lambda*lambda), getVariance(data), 0.0009);
    }

    Double getMean(List<Double> data) {
        double sum = 0;
        for (double a: data ){
            sum+=a;
        }
        return sum/data.size();
    }

    Double getVariance(List<Double> data) {
        Double mean = getMean(data);
        double sum = 0;
        for(double a :data) {
            sum += (a-mean)*(a-mean);
        }
        return sum/(data.size());
    }
}


import java.util.ArrayList;
import java.util.List;

class PoissonDistributionTest {
    public static void main(String[] args) {
        System.out.println("Generate Time Interval Test");
        int lambda = 75;
        int loop = 1000;
        ArrayList<Double> data = new ArrayList<>();
        PoissonDistribution distribution = new PoissonDistribution(lambda);

        for (int i =0; i<loop; i++){
            data.add(distribution.generateTimeInterval());
        }
        // Mean of the poisson distribution is 1/lambda
        if (Math.abs((1.0/lambda) - getMean(data)) > 0.0009){
            System.out.println("FAIL");
        }
        // Variance of the poisson distribution is 1/lambda^2
        if (Math.abs((1.0/(lambda*lambda)) - getVariance(data)) > 0.0009){
            System.out.println("FAIL");
        }

    }
    static Double getMean(List<Double> data) {
        double sum = 0;
        for (double a: data ){
            sum+=a;
        }
        return sum/data.size();
    }

    static Double getVariance(List<Double> data) {
        double mean = getMean(data);
        double sum = 0;
        for(double a :data) {
            sum += (a-mean)*(a-mean);
        }
        return sum/(data.size());
    }
}


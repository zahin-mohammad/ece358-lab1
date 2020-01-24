public class Simulation {

    private static Distribution distribution = new Distribution(1000);

    public static void main(String[] args) {
        double sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += (distribution.generateTimeInterval());
        }
        System.out.println(sum/100.0);
        System.out.println(1.0/1000.0);
    }
}

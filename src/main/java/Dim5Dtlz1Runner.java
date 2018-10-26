public class Dim5Dtlz1Runner extends AbstractBenchRunner {
    @Override
    int getDim() {
        return 5;
    }

    @Override
    protected int getRunCount() {
        return 3;
    }

    @Override
    protected int getPopSize() {
        return 100;
    }

    @Override
    protected int getTrueParetoFrontSize() {
        return 20;
    }

    @Override
    int getNumberOfEvaluations() {
        return 5000000;
    }

    public static void main(String[] args) throws Exception {
        final Dim5Dtlz1Runner runner = new Dim5Dtlz1Runner();
        runner.levelLockJfbyT6();
        runner.testNSGAII();
        runner.jfbySerial();
    }
}

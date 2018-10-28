public class Dim6Dtlz1Runner extends AbstractBenchRunner {
    @Override
    int getDim() {
        return 6;
    }

    @Override
    protected int getRunCount() {
        return 3;
    }

    @Override
    protected int getPopSize() {
        return 4000;
    }

    @Override
    int getNumberOfEvaluations() {
        return 1000000;
    }


    public static void main(String[] args) throws Exception {
        final Dim6Dtlz1Runner runner = new Dim6Dtlz1Runner();
        runner.levelLockJfbyT6();
        runner.testNSGAII();
        runner.jfbySerial();
    }
}

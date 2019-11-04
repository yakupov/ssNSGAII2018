package runner;

import org.moeaframework.core.Solution;
import org.moeaframework.problem.DTLZ.DTLZ1;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class MathLovingDTLZ1 extends DTLZ1 {
    public MathLovingDTLZ1(int numberOfObjectives) {
        super(numberOfObjectives);
    }

    public MathLovingDTLZ1(int numberOfVariables, int numberOfObjectives) {
        super(numberOfVariables, numberOfObjectives);
    }

    @Override
    public void evaluate(Solution solution) {
        double[] doubles = new double[10_000];
        for (int i = 0; i < doubles.length; ++i) {
            doubles[i] = ThreadLocalRandom.current().nextDouble();
        }
        Arrays.sort(doubles);
        super.evaluate(solution);
    }
}

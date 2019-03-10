package runner;

import org.moeaframework.core.Solution;
import org.moeaframework.problem.DTLZ.DTLZ1;

public class RetardedDTLZ1 extends DTLZ1 {
    public RetardedDTLZ1(int numberOfObjectives) {
        super(numberOfObjectives);
    }

    public RetardedDTLZ1(int numberOfVariables, int numberOfObjectives) {
        super(numberOfVariables, numberOfObjectives);
    }

    @Override
    public void evaluate(Solution solution) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        super.evaluate(solution);
    }
}

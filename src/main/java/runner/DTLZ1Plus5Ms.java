package runner;

import org.moeaframework.core.Solution;
import org.moeaframework.problem.DTLZ.DTLZ1;

public class DTLZ1Plus5Ms extends DTLZ1 {
    public DTLZ1Plus5Ms(int numberOfObjectives) {
        super(numberOfObjectives);
    }

    public DTLZ1Plus5Ms(int numberOfVariables, int numberOfObjectives) {
        super(numberOfVariables, numberOfObjectives);
    }

    @Override
    public void evaluate(Solution solution) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        super.evaluate(solution);
    }
}

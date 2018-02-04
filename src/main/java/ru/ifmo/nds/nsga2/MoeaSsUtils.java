package ru.ifmo.nds.nsga2;

import org.moeaframework.core.Solution;

public class MoeaSsUtils {
    public static MOEAIndividual convertSolution(final Solution solution) {
        if (solution == null) {
            return null;
        } else {
            final MOEAIndividual res = new MOEAIndividual(solution.getNumberOfVariables(), solution.getNumberOfObjectives(), solution.getNumberOfConstraints());
            for(int j = 0; j < solution.getNumberOfVariables(); ++j) {
                res.setVariable(j, solution.getVariable(j));
            }
            return res;
        }
    }
}

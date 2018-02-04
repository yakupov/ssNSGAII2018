package ru.ifmo.nds.nsga2;

import org.moeaframework.core.Solution;
import ru.ifmo.nds.IIndividual;
import ru.ifmo.nds.IManagedPopulation;
import ru.ifmo.nds.INonDominationLevel;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class MoeaSsUtils {
    public static MOEAIndividual convertSolution(final Solution solution) {
        if (solution == null) {
            return null;
        } else {
            final MOEAIndividual res = new MOEAIndividual(solution.getNumberOfVariables(), solution.getNumberOfObjectives(), solution.getNumberOfConstraints());
            for(int j = 0; j < solution.getNumberOfVariables(); ++j) {
                res.setVariable(j, solution.getVariable(j));
            }
//            final String cdAttrName = "crowdingDistance";
//            res.setAttribute(cdAttrName, (Serializable) solution.getAttribute(cdAttrName));
            return res;
        }
    }

    public static double calculateZeroLevelHv(@Nonnull final IManagedPopulation population) {
        if (population.getLevels().isEmpty()) {
            return 0;
        } else {
            final INonDominationLevel level = population.getLevels().get(0);
            if (level.getMembers() == null || level.getMembers().isEmpty()) {
                return 0;
            } else {
                final int dim = level.getMembers().get(0).getObjectives().length;
                final double[] mins = new double[dim];
                Arrays.fill(mins, Double.POSITIVE_INFINITY);
                final double[] maxs = new double[dim];
                Arrays.fill(maxs, Double.NEGATIVE_INFINITY);

                for (IIndividual individual : level.getMembers()) {
                    final double[] obj = individual.getObjectives();
                    for (int i = 0; i < dim; ++i) {
                        mins[i] = Math.min(mins[i], obj[i]);
                        maxs[i] = Math.max(maxs[i], obj[i]);
                    }
                }

                double res = maxs[0] - mins[0];
                for (int i = 1; i < dim; ++i) {
                    res *= (maxs[i] - mins[i]);
                }
                return res;
            }
        }
    }
}

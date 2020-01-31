package ru.ifmo.nds.nsga2;

import ru.ifmo.nds.IIndividual;
import ru.ifmo.nds.IManagedPopulation;
import ru.ifmo.nds.INonDominationLevel;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class MoeaSsUtils {

   public static <T> double calculateZeroLevelHv(@Nonnull final IManagedPopulation<T> population) {
        if (population.getLevelsUnsafe().isEmpty()) {
            return 0;
        } else {
            final INonDominationLevel<T> level = population.getLevelsUnsafe().get(0);
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

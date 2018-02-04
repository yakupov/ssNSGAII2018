package ru.ifmo.nds.nsga2;

import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.NondominatedSortingPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Variation;
import org.moeaframework.core.comparator.ChainedComparator;
import org.moeaframework.core.comparator.CrowdingComparator;
import org.moeaframework.core.comparator.ParetoDominanceComparator;
import org.moeaframework.core.operator.TournamentSelection;
import org.moeaframework.core.spi.OperatorFactory;
import ru.ifmo.nds.IManagedPopulation;
import ru.ifmo.nds.nsga2.init.MOEAIndInitialization;

import java.util.Properties;

/**
 * Copied utility code from the MOEA framework.
 * Usage of standard MOEA classes is impossible, because we need to gather specific test data.
 */
@SuppressWarnings("unused")
public class NSGAIIMoeaRunner {

    /**
     * Returns a new {@link NSGAII} instance.
     *
     * @param populationSize population size
     * @param problem the problem
     * @return a new {@code NSGAII} instance
     */
    public static NSGAII newNSGAII(int populationSize, Problem problem) {
        return newNSGAII(populationSize, problem, false);
    }

    @SuppressWarnings("SameParameterValue")
    private static NSGAII newNSGAII(int populationSize, Problem problem, boolean withReplacement) {
        final Initialization initialization =
                new org.moeaframework.core.operator.RandomInitialization(problem, populationSize);
        final NondominatedSortingPopulation population = new NondominatedSortingPopulation();

        final TournamentSelection selection;
        if (withReplacement) {
            selection = new TournamentSelection(2, new ChainedComparator(
                    new ParetoDominanceComparator(),
                    new CrowdingComparator()));
        } else {
            selection = null;
        }

        final Variation variation = getVariation(problem);
        return new NSGAII(problem, population, null, selection, variation, initialization);
    }

    public static SSNSGAII newSSNSGAII(int populationSize, Problem problem, IManagedPopulation population) {
        final MOEAIndInitialization initialization =
                new ru.ifmo.nds.nsga2.init.RandomInitialization(problem, populationSize);
        final Variation variation = getVariation(problem);
        return new SSNSGAII(problem, variation, initialization, population);
    }

    private static Variation getVariation(Problem problem) {
        return OperatorFactory.getInstance().getVariation(null, new Properties(), problem);
    }
}

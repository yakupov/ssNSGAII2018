package ru.ifmo.nds.nsga2;

import org.apache.commons.math3.random.MersenneTwister;
import org.moeaframework.algorithm.AbstractAlgorithm;
import org.moeaframework.core.*;
import org.moeaframework.core.comparator.ChainedComparator;
import org.moeaframework.core.comparator.CrowdingComparator;
import org.moeaframework.core.comparator.DominanceComparator;
import org.moeaframework.core.comparator.ParetoDominanceComparator;
import org.moeaframework.core.operator.TournamentSelection;
import ru.ifmo.nds.IIndividual;
import ru.ifmo.nds.IManagedPopulation;
import ru.ifmo.nds.impl.CDIndividual;
import ru.ifmo.nds.nsga2.init.MOEAIndInitialization;

import java.util.List;

import static ru.ifmo.nds.nsga2.MoeaSsUtils.convertSolution;

/**
 * Steady-state modification of NSGA2, implemented using incremental NDS.
 * <p>
 * References:
 * <ol>
 * <li>Deb, K. et al.  "A Fast Elitist Multi-Objective Genetic Algorithm:
 * NSGA-II."  IEEE Transactions on Evolutionary Computation, 6:182-197,
 * 2000.
 * <li>Kollat, J. B., and Reed, P. M.  "Comparison of Multi-Objective
 * Evolutionary Algorithms for Long-Term Monitoring Design."  Advances in
 * Water Resources, 29(6):792-807, 2006.
 * </ol>
 */
public class SSNSGAII extends AbstractAlgorithm implements EpsilonBoxEvolutionaryAlgorithm {
    private final Variation variation;
    private final MOEAIndInitialization initialization;
    private final IManagedPopulation population;

    private final DominanceComparator comparator = new ChainedComparator(
            new ParetoDominanceComparator(),
            new CrowdingComparator());

    public SSNSGAII(Problem problem, Variation variation, MOEAIndInitialization initialization, IManagedPopulation population) {
        super(problem);
        this.variation = variation;
        this.initialization = initialization;
        this.population = population;
    }

    @Override
    public void iterate() {
        try {
            doIterate();
        } catch (ArrayIndexOutOfBoundsException e) {
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                if (stackTraceElement.getClassName().contains(MersenneTwister.class.getSimpleName())) {
                    System.err.println("MersenneTwister has failed");
                    return;
                }
            }
            e.printStackTrace();
            throw e;
        }
    }

    public MOEAIndividual doIterate() {
        final MOEAIndividual addend = generateOffspring();
        population.addIndividual(addend);
        return addend;
    }

    private static final String CD_ATTR_NAME = "crowdingDistance";

    private MOEAIndividual generateOffspring() {
        final List<CDIndividual> mutationCandidates = population.getRandomSolutions(2 * variation.getArity());
        if (mutationCandidates.size() < 2 * variation.getArity()) {
            throw new RuntimeException("Failed to get enough mutation candidates: " + mutationCandidates);
        }
        final Solution[] parents = new Solution[variation.getArity()];
        //System.err.println(mutationCandidates);
        for (int i = 0; i < mutationCandidates.size() - 1; i += 2) {
            final CDIndividual ind1 = mutationCandidates.get(i);
            final MOEAIndividual solution1 = ((MOEAIndividual) ind1.getIndividual()).copy();
            solution1.setAttribute(CD_ATTR_NAME, ind1.getCrowdingDistance());

            final CDIndividual ind2 = mutationCandidates.get(i + 1);
            final MOEAIndividual solution2 = ((MOEAIndividual) ind2.getIndividual()).copy();
            solution2.setAttribute(CD_ATTR_NAME, ind2.getCrowdingDistance());

            parents[i / 2] = TournamentSelection.binaryTournament(
                    solution1,
                    solution2,
                    comparator);
        }

        final MOEAIndividual solution = convertSolution(variation.evolve(parents)[0]);
        evaluate(solution);
        return solution;
    }

    @Override
    protected void initialize() {
        super.initialize();

        final MOEAIndividual[] initialSolutions = initialization.initialize();
        evaluateAll(initialSolutions);
        for (MOEAIndividual s: initialSolutions) {
            population.addIndividual(s);
            //System.out.println(Arrays.toString(s.getObjectives()));
        }
    }

    @Override
    public EpsilonBoxDominanceArchive getArchive() {
        throw new UnsupportedOperationException("Working with archive is not supported");
    }

    @Override
    public NondominatedSortingPopulation getPopulation() {
        throw new UnsupportedOperationException("Working with NondominatedPopulation is not supported");
    }

    @Override
    public NondominatedPopulation getResult() {
        throw new UnsupportedOperationException("Working with NondominatedPopulation is not supported");
    }
}

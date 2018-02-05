package ru.ifmo.nds.nsga2;

import org.junit.Ignore;
import org.junit.Test;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.indicator.Hypervolume;
import org.moeaframework.problem.DTLZ.DTLZ1;
import ru.ifmo.nds.IIndividual;
import ru.ifmo.nds.IManagedPopulation;
import ru.ifmo.nds.dcns.concurrent.LevelLockJFBYPopulation;

import java.util.concurrent.TimeUnit;

public class ManualSSNSGAIITest {
    @Test
    @Ignore
    public void levelSyncDtlz1Dim3Pop100() {
        final int testDurationInSeconds = 3;
        final int popSize = 100;
        final int dim = 3;
        final IManagedPopulation pop = new LevelLockJFBYPopulation(popSize);

        final DTLZ1 problem = new DTLZ1(dim);

        final SSNSGAII nsga = NSGAIIMoeaRunner.newSSNSGAII(popSize, problem, pop);
        nsga.step();

        final int trueFrontSize = 100;
        final double[][] trueParetoFront = new double[trueFrontSize][];
        final NondominatedPopulation trueParetoNP = new NondominatedPopulation();
        for (int i = 0; i < trueFrontSize; ++i) {
            final Solution solution = problem.generate();
            trueParetoNP.add(solution);
            trueParetoFront[i] = solution.getObjectives();
        }

        double[][] d1 = new double[pop.getLevels().get(0).getMembers().size()][];
        NondominatedPopulation np = new NondominatedPopulation();
        int i = 0;
        for (IIndividual iIndividual : pop.getLevels().get(0).getMembers()) {
            d1[i++] = iIndividual.getObjectives();
            np.add((Solution) iIndividual);
        }

        //System.out.println(pop.getLevels().get(0).getMembers().stream().map(IIndividual::getObjectives).map(Arrays::toString).collect(Collectors.toList()));
        System.out.println("was HV JMetal: " + new jmetal.qualityIndicator.Hypervolume().hypervolume(d1, trueParetoFront, dim));
        System.out.println("was HV MOEA: " + new Hypervolume(problem, trueParetoNP).evaluate(np));

        final long startTs = System.nanoTime();
        while (TimeUnit.SECONDS.toNanos(testDurationInSeconds) > (System.nanoTime() - startTs)) {
            nsga.step();
        }

        d1 = new double[pop.getLevels().get(0).getMembers().size()][];
        np = new NondominatedPopulation();
        i = 0;
        for (IIndividual iIndividual : pop.getLevels().get(0).getMembers()) {
            d1[i++] = iIndividual.getObjectives();
            np.add((Solution) iIndividual);
        }

        //System.out.println(pop.getLevels().get(0).getMembers().stream().map(IIndividual::getObjectives).map(Arrays::toString).collect(Collectors.toList()));
        System.out.println("is HV JMetal: " + new jmetal.qualityIndicator.Hypervolume().hypervolume(d1, trueParetoFront, dim));
        System.out.println("is HV MOEA: " + new Hypervolume(problem, trueParetoNP).evaluate(np));
    }
}

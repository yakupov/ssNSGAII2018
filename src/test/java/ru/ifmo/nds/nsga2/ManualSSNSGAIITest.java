package ru.ifmo.nds.nsga2;

import org.junit.Ignore;
import org.junit.Test;
import org.moeaframework.problem.DTLZ.DTLZ3;
import ru.ifmo.nds.IManagedPopulation;
import ru.ifmo.nds.dcns.concurrent.LevelLockJFBYPopulation;

import java.util.concurrent.TimeUnit;

public class ManualSSNSGAIITest {

    @Test
    @Ignore
    public void levelSyncDtlz3Dim3Pop1000() {
        final int testDurationInSeconds = 10;
        final int popSize = 1000;
        final int dim = 3;
        final IManagedPopulation pop = new LevelLockJFBYPopulation(popSize);

        final SSNSGAII nsga = NSGAIIMoeaRunner.newSSNSGAII(popSize, new DTLZ3(dim), pop);
        nsga.step();

        System.out.println(pop.getLevels().get(0).getMembers().size());
        System.out.println("Was HV: " + (int) MoeaSsUtils.calculateZeroLevelHv(pop));
        final long startTs = System.nanoTime();
        while (TimeUnit.SECONDS.toNanos(testDurationInSeconds) > (System.nanoTime() - startTs)) {
            nsga.step();
        }
        System.out.println(pop.getLevels().get(0).getMembers().size());
        System.out.println("Is HV: " + (int) MoeaSsUtils.calculateZeroLevelHv(pop));
    }
}

package ru.ifmo.nds.nsga2;

import org.junit.Ignore;
import org.junit.Test;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.indicator.Hypervolume;
import org.moeaframework.problem.DTLZ.DTLZ;
import org.moeaframework.problem.DTLZ.DTLZ1;
import ru.ifmo.nds.IIndividual;
import ru.ifmo.nds.IManagedPopulation;
import ru.ifmo.nds.dcns.concurrent.CJFBYPopulation;
import ru.ifmo.nds.dcns.concurrent.LevelLockJFBYPopulation;
import ru.ifmo.nds.dcns.jfby.JFBYPopulation;
import ru.ifmo.nds.dcns.jfby.TotalSyncJFBYPopulation;

import javax.annotation.Nonnull;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Ignore
public class ManualSSNSGAIITest {
    private DTLZ getProblem() {
        return new DTLZ1(getDim());
    }

    private int getDim() {
        return 3;
    }

    private int getTestDurationInSeconds() {
        return 10;
    }

    private int getPopSize() {
        return 500;
    }

    private int getTrueParetoFrontSize() {
        return 10000;
    }

    private int getRunCount() {
        return 1;
    }

    private final Hypervolume hypervolume;

    public ManualSSNSGAIITest() {
        final NondominatedPopulation trueParetoNP = new NondominatedPopulation();
        final DTLZ problem = getProblem();
        for (int i = 0; i < getTrueParetoFrontSize(); ++i) {
            final Solution solution = problem.generate();
            trueParetoNP.add(solution);
        }

        hypervolume = new Hypervolume(problem, trueParetoNP);
    }

    private void printHV(IManagedPopulation pop, int stackDepth, int runId) {
        final NondominatedPopulation np = new NondominatedPopulation();
        for (IIndividual iIndividual : pop.getLevels().get(0).getMembers()) {
            np.add((Solution) iIndividual);
        }
        printHV(np, stackDepth + 1, runId);
    }

    private void printHV(final NondominatedPopulation np, int stackDepth, int runId) {
        final DTLZ problem = getProblem();
        final String testMethod = getMethodName(stackDepth + 1);
        System.out.printf("%d\t%s\t%s\t%d\t%d\t%d\t%f\n", runId, testMethod, problem.getName(), getDim(),
                getPopSize(), getTestDurationInSeconds(), hypervolume.evaluate(np));
        System.out.flush();
    }

    private String getMethodName(int stackDepth) {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        return ste[stackDepth + 2].getMethodName();
    }

    @Test
    public void jfbySerial() {
        System.out.println("Starting " + getMethodName(0));

        final int testDurationInSeconds = getTestDurationInSeconds();
        final int popSize = getPopSize();
        final DTLZ problem = getProblem();

        for (int i = 0; i < getRunCount(); ++i) {
            final IManagedPopulation pop = new JFBYPopulation(popSize);
            final SSNSGAII nsga = NSGAIIMoeaRunner.newSSNSGAII(popSize, problem, pop);
            nsga.step();

            final long startTs = System.nanoTime();
            while (TimeUnit.SECONDS.toNanos(testDurationInSeconds) > (System.nanoTime() - startTs)) {
                for (int j = 0; j < 100; ++j) {
                    nsga.step();
                }
            }
            printHV(pop, 0, i);
        }
    }

    private void concurrentTestCommon(final int threadsCount, @Nonnull final Supplier<IManagedPopulation> popSupplier) throws InterruptedException {
        System.out.println("Starting " + getMethodName(1));

        final int testDurationInSeconds = getTestDurationInSeconds();
        final int popSize = getPopSize();
        final DTLZ problem = getProblem();

        final ExecutorService es = Executors.newFixedThreadPool(threadsCount);

        try {
            for (int i = 0; i < getRunCount(); ++i) {
                final IManagedPopulation pop = popSupplier.get();
                final SSNSGAII nsga = NSGAIIMoeaRunner.newSSNSGAII(popSize, problem, pop);
                nsga.step();

                final CountDownLatch latch = new CountDownLatch(threadsCount);
                final long startTs = System.nanoTime();
                for (int t = 0; t < threadsCount; ++t) {
                    es.submit(() -> {
                        try {
                            while (TimeUnit.SECONDS.toNanos(testDurationInSeconds) > (System.nanoTime() - startTs)) {
                                for (int j = 0; j < 100; ++j) {
                                    nsga.step();
                                }
                            }
                        } catch (Throwable th) {
                            th.printStackTrace();
                            System.err.flush();
                            throw th;
                        } finally {
                            latch.countDown();
                            //System.out.println(latch.toString());
                        }
                    });
                }
                latch.await();

                printHV(pop, 1, i);
            }
        } finally {
            es.shutdownNow();
        }
    }

    @Test
    public void levelLockJfbyT3() throws InterruptedException {
        concurrentTestCommon(3, () -> new LevelLockJFBYPopulation(getPopSize()));
    }

    @Test
    public void cjfbyAltT3() throws InterruptedException {
        concurrentTestCommon(3, () -> new CJFBYPopulation(getPopSize(), true));
    }

    @Test
    public void tsT3() throws InterruptedException {
        concurrentTestCommon(3, () -> new TotalSyncJFBYPopulation(getPopSize()));
    }

    @Test
    public void levelLockJfbyT6() throws InterruptedException {
        concurrentTestCommon(6, () -> new LevelLockJFBYPopulation(getPopSize()));
    }

    @Test
    public void cjfbyAltT6() throws InterruptedException {
        concurrentTestCommon(6, () -> new CJFBYPopulation(getPopSize(), true));
    }

    @Test
    public void tsT6() throws InterruptedException {
        concurrentTestCommon(6, () -> new TotalSyncJFBYPopulation(getPopSize()));
    }


    @Test
    public void levelLockJfbyT12() throws InterruptedException {
        concurrentTestCommon(12, () -> new LevelLockJFBYPopulation(getPopSize()));
    }

    @Test
    public void cjfbyAltT12() throws InterruptedException {
        concurrentTestCommon(12, () -> new CJFBYPopulation(getPopSize(), true));
    }


    @Test
    public void testNSGAII() {
        for (int i = 0; i < getRunCount(); ++i) {
            final NondominatedPopulation result = new Executor()
                    .withProblem(getProblem())
                    .withAlgorithm("NSGAII")
                    .withProperty("populationSize", getPopSize())
                    .withMaxTime(getTestDurationInSeconds() * 1000)
                    .run();
            printHV(result, 0, i);
        }
    }
}

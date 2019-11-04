package runner;

import org.moeaframework.problem.DTLZ.DTLZ;

public class ConcurrentConfigurationSearcher {
    public static void main(String[] args) throws Exception {
        final AbstractBenchRunner2 d3Runner = new AbstractBenchRunner2() {
            @Override
            DTLZ getProblem() {
                return new RetardedDTLZ1(getDim());
            }

            @Override
            int getDim() {
                return 3;
            }

            @Override
            int getNumberOfEvaluations() {
                return 300000;
            }
        };

        System.out.println("dim3 ll");
        long prevDuration = Integer.MAX_VALUE;
        for (int i = 66; i < 0; ++i) {
            System.out.println(i);
            final long start = System.currentTimeMillis();
            d3Runner.levelLockJfby(i);
            final long duration = System.currentTimeMillis() - start;
            if (duration > prevDuration * 2)
                break;
            prevDuration = duration;
        }

        System.out.println("dim3 cj");
        prevDuration = Integer.MAX_VALUE;
        for (int i = 16; i < 301; ++i) {
            System.out.println(i);
            final long start = System.currentTimeMillis();
            d3Runner.cjfbyAlt(i);
            final long duration = System.currentTimeMillis() - start;
            if (duration > prevDuration * 2)
                break;
            prevDuration = duration;
        }

        System.out.println("dim3 ts");
        prevDuration = Integer.MAX_VALUE;
        for (int i = 1; i < 301; ++i) {
            System.out.println(i);
            final long start = System.currentTimeMillis();
            d3Runner.ts(i);
            final long duration = System.currentTimeMillis() - start;
            if (duration > prevDuration * 2)
                break;
            prevDuration = duration;
        }

        final AbstractBenchRunner2 d4Runner = new AbstractBenchRunner2() {
            @Override
            DTLZ getProblem() {
                return new RetardedDTLZ1(getDim());
            }

            @Override
            int getDim() {
                return 4;
            }

            @Override
            int getNumberOfEvaluations() {
                return 200000;
            }
        };

        System.out.println("dim4 ll");
        prevDuration = Integer.MAX_VALUE;
        for (int i = 1; i < 301; ++i) {
            System.out.println(i);
            final long start = System.currentTimeMillis();
            d4Runner.levelLockJfby(i);
            final long duration = System.currentTimeMillis() - start;
            if (duration > prevDuration * 2)
                break;
            prevDuration = duration;
        }

        System.out.println("dim4 cj");
        prevDuration = Integer.MAX_VALUE;
        for (int i = 1; i < 301; ++i) {
            System.out.println(i);
            final long start = System.currentTimeMillis();
            d4Runner.cjfbyAlt(i);
            final long duration = System.currentTimeMillis() - start;
            if (duration > prevDuration * 2)
                break;
            prevDuration = duration;
        }

        System.out.println("dim4 ts");
        prevDuration = Integer.MAX_VALUE;
        for (int i = 1; i < 301; ++i) {
            System.out.println(i);
            final long start = System.currentTimeMillis();
            d4Runner.ts(i);
            final long duration = System.currentTimeMillis() - start;
            if (duration > prevDuration * 2)
                break;
            prevDuration = duration;
        }

        final AbstractBenchRunner2 d5Runner = new AbstractBenchRunner2() {
            @Override
            DTLZ getProblem() {
                return new RetardedDTLZ1(getDim());
            }

            @Override
            int getDim() {
                return 5;
            }

            @Override
            int getNumberOfEvaluations() {
                return 800000;
            }

            @Override
            protected int getRunCount() {
                return 3;
            }

            @Override
            protected int getPopSize() {
                return 800;
            }
        };

        System.out.println("dim5 ll");
        prevDuration = Integer.MAX_VALUE;
        for (int i = 1; i < 301; ++i) {
            System.out.println(i);
            final long start = System.currentTimeMillis();
            d5Runner.levelLockJfby(i);
            final long duration = System.currentTimeMillis() - start;
            if (duration > prevDuration * 2)
                break;
            prevDuration = duration;
        }

        System.out.println("dim5 cj");
        prevDuration = Integer.MAX_VALUE;
        for (int i = 1; i < 301; ++i) {
            System.out.println(i);
            final long start = System.currentTimeMillis();
            d5Runner.cjfbyAlt(i);
            final long duration = System.currentTimeMillis() - start;
            if (duration > prevDuration * 2)
                break;
            prevDuration = duration;
        }

        System.out.println("dim5 ts");
        prevDuration = Integer.MAX_VALUE;
        for (int i = 1; i < 301; ++i) {
            System.out.println(i);
            final long start = System.currentTimeMillis();
            d5Runner.ts(i);
            final long duration = System.currentTimeMillis() - start;
            if (duration > prevDuration * 2)
                break;
            prevDuration = duration;
        }
    }
}

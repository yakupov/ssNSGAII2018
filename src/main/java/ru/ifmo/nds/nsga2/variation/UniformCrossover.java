package ru.ifmo.nds.nsga2.variation;

import org.moeaframework.core.Solution;
import org.moeaframework.core.Variable;
import org.moeaframework.core.Variation;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class UniformCrossover implements Variation {
    private final double probability;

    public UniformCrossover(double probability) {
        this.probability = probability;
    }

    public double getProbability() {
        return this.probability;
    }

    public Solution[] evolve(Solution[] parents) {
        Solution result1 = parents[0].copy();
        Solution result2 = parents[1].copy();
        final Random random = ThreadLocalRandom.current();
        if (random.nextDouble() <= this.probability) {
            for(int i = 0; i < result1.getNumberOfVariables(); ++i) {
                if (random.nextBoolean()) {
                    Variable temp = result1.getVariable(i);
                    result1.setVariable(i, result2.getVariable(i));
                    result2.setVariable(i, temp);
                }
            }
        }

        return new Solution[]{result1, result2};
    }

    public int getArity() {
        return 2;
    }
}


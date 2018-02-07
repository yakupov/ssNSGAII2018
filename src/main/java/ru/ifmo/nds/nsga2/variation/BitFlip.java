package ru.ifmo.nds.nsga2.variation;

import org.moeaframework.core.Solution;
import org.moeaframework.core.Variable;
import org.moeaframework.core.Variation;
import org.moeaframework.core.variable.BinaryVariable;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BitFlip implements Variation {
    private final double probability;

    public BitFlip(double probability) {
        this.probability = probability;
    }

    public double getProbability() {
        return this.probability;
    }

    public Solution[] evolve(Solution[] parents) {
        Solution result = parents[0].copy();

        for(int i = 0; i < result.getNumberOfVariables(); ++i) {
            Variable variable = result.getVariable(i);
            if (variable instanceof BinaryVariable) {
                evolve((BinaryVariable)variable, this.probability);
            }
        }

        return new Solution[]{result};
    }

    public static void evolve(BinaryVariable variable, double probability) {
        final Random random = ThreadLocalRandom.current();
        for(int i = 0; i < variable.getNumberOfBits(); ++i) {
            if (random.nextDouble() <= probability) {
                variable.set(i, !variable.get(i));
            }
        }

    }

    public int getArity() {
        return 1;
    }
}

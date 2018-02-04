package ru.ifmo.nds.nsga2.init;

import org.moeaframework.core.Problem;
import ru.ifmo.nds.nsga2.MOEAIndividual;

import static ru.ifmo.nds.nsga2.MoeaSsUtils.convertSolution;

public class RandomInitialization implements MOEAIndInitialization {
    private final Problem problem;
    private final int populationSize;

    public RandomInitialization(Problem problem, int populationSize) {
        this.problem = problem;
        this.populationSize = populationSize;
    }

    public MOEAIndividual[] initialize() {
        final MOEAIndividual[] initialPopulation = new MOEAIndividual[this.populationSize];

        for(int i = 0; i < this.populationSize; ++i) {
            final MOEAIndividual res = convertSolution(this.problem.newSolution());

            for(int j = 0; j < res.getNumberOfVariables(); ++j) {
                res.getVariable(j).randomize();
            }

            initialPopulation[i] = res;
        }

        return initialPopulation;
    }
}

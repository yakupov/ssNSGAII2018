package ru.ifmo.nds.nsga2;

import org.moeaframework.core.Solution;
import ru.ifmo.nds.IIndividual;

public class MOEAIndividual extends Solution implements IIndividual {
    public MOEAIndividual(double[] objectives) {
        super(objectives);
    }

    public MOEAIndividual(int numberOfVariables, int numberOfObjectives, int numberOfConstraints) {
        super(numberOfVariables, numberOfObjectives, numberOfConstraints);
    }
}

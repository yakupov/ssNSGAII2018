package ru.ifmo.nds.nsga2;

import org.apache.commons.lang3.SerializationUtils;
import org.moeaframework.core.Solution;
import ru.ifmo.nds.IIndividual;

import java.io.Serializable;
import java.util.Map;

public class MOEAIndividual extends Solution implements IIndividual {
    public MOEAIndividual(double[] objectives) {
        super(objectives);
    }

    public MOEAIndividual(int numberOfVariables, int numberOfObjectives, int numberOfConstraints) {
        super(numberOfVariables, numberOfObjectives, numberOfConstraints);
    }

    public MOEAIndividual(Solution solution) {
        super(solution);
    }

    @Override
    public MOEAIndividual copy() {
        return new MOEAIndividual(this);
    }

    @Override
    public MOEAIndividual deepCopy() {
        final MOEAIndividual copy = copy();
        for (Map.Entry<String, Serializable> entry : getAttributes().entrySet()) {
            copy.setAttribute(
                    entry.getKey(),
                    SerializationUtils.clone(entry.getValue()));
        }
        return copy;
    }
}

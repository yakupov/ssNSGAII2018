package ru.ifmo.nds.nsga2;

public class Dim5DtlzTest extends AbstractManualSSNSGAIIDtlzTest {
    @Override
    int getDim() {
        return 5;
    }

    @Override
    protected int getRunCount() {
        return 3;
    }

    @Override
    protected int getPopSize() {
        return 800;
    }

    @Override
    int getNumberOfEvaluations() {
        return 800000;
    }
}

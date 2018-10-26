package ru.ifmo.nds.nsga2;

public class Dim6DtlzTest extends AbstractManualSSNSGAIIDtlzTest {
    @Override
    int getDim() {
        return 6;
    }

    @Override
    protected int getRunCount() {
        return 3;
    }

    @Override
    protected int getPopSize() {
        return 1000;
    }

    @Override
    int getNumberOfEvaluations() {
        return 300000;
    }
}

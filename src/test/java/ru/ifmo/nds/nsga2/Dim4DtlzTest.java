package ru.ifmo.nds.nsga2;

public class Dim4DtlzTest extends AbstractManualSSNSGAIIDtlzTest {
    @Override
    int getDim() {
        return 4;
    }

    @Override
    int getNumberOfEvaluations() {
        return 200000;
    }
}

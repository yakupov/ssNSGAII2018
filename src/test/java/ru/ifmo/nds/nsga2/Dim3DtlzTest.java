package ru.ifmo.nds.nsga2;

public class Dim3DtlzTest extends AbstractManualSSNSGAIIDtlzTest {
    @Override
    int getDim() {
        return 3;
    }

    @Override
    int getNumberOfEvaluations() {
        return 200000;
    }
}

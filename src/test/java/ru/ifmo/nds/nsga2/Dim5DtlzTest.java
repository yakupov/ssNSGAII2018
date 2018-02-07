package ru.ifmo.nds.nsga2;

public class Dim5DtlzTest extends AbstractManualSSNSGAIIDtlzTest {
    @Override
    int getDim() {
        return 5;
    }

    @Override
    int getNumberOfEvaluations() {
        return 100000;
    }
}

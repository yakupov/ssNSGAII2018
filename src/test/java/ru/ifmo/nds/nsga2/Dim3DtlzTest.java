package ru.ifmo.nds.nsga2;

public class Dim3DtlzTest {//extends AbstractManualSSNSGAIIDtlzTest {
    //@Override
    int getDim() {
        return 3;
    }

    //@Override //TODO mvn test
    int getNumberOfEvaluations() {
        return 300000;
    }
}

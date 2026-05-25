package com.aroos.qr.generator.modules.evaluators;

import org.testng.annotations.Test;

import com.aroos.qr.generator.modules.IQRCode;

public final class ModulePercentageEvaluatorTest extends EvaluatorTest
{
    public ModulePercentageEvaluatorTest()
    {
        super(new ModulePercentageEvaluator(), 30);
    }

    @Test
    public void modulePercentageEvaluatorTest()
    {
        this.testEvaluator();
    }

    @Override
    void prepQRCode(final IQRCode code)
    {
        // 1/3 dark to light module ratio, should result in a penalty of 30.
        code.setModule(1, 0, false);
        code.setModule(2, 0, false);
        code.setModule(3, 0, false);
        code.setModule(4, 0, false);
        code.setModule(5, 0, false);
        code.setModule(6, 0, false);

        code.setModule(8, 0, true);
        code.setModule(9, 0, true);
        code.setModule(10, 0, true);
    }
}
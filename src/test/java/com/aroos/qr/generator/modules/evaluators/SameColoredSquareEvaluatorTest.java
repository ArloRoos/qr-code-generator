package com.aroos.qr.generator.modules.evaluators;

import org.testng.annotations.Test;

import com.aroos.qr.generator.modules.IQRCode;

public final class SameColoredSquareEvaluatorTest extends EvaluatorTest
{
    public SameColoredSquareEvaluatorTest()
    {
        super(new SameColoredSquareEvaluator(), 24);
    }

    @Test
    public void sameColoredSquareEvaluatorTest()
    {
        this.testEvaluator();
    }

    @Override
    void prepQRCode(final IQRCode code)
    {
        // 2x2 square, dark, penalty 3
        code.setModule(0, 2, true);
        code.setModule(0, 3, true);
        code.setModule(1, 2, true);
        code.setModule(1, 3, true);

        // 2x2 square, light, penalty 3
        code.setModule(3, 2, false);
        code.setModule(3, 3, false);
        code.setModule(4, 2, false);
        code.setModule(4, 3, false);

        // 2x3 square, penalty 6
        code.setModule(6, 2, true);
        code.setModule(6, 3, true);
        code.setModule(6, 4, true);
        code.setModule(7, 2, true);
        code.setModule(7, 3, true);
        code.setModule(7, 4, true);

        // 3x3 square, penalty 12
        code.setModule(9, 2, false);
        code.setModule(10, 2, false);
        code.setModule(11, 2, false);
        code.setModule(9, 3, false);
        code.setModule(10, 3, false);
        code.setModule(11, 3, false);
        code.setModule(9, 4, false);
        code.setModule(10, 4, false);
        code.setModule(11, 4, false);
    }
}
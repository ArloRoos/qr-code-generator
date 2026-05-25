package com.aroos.qr.generator.modules.evaluators;

import org.testng.annotations.Test;

import com.aroos.qr.generator.modules.IQRCode;

public final class SameColoredLineEvaluatorTest extends EvaluatorTest
{
    public SameColoredLineEvaluatorTest()
    {
        super(new SameColoredLineEvaluator(), 11);
    }

    @Test
    public void sameColoredLineEvaluatorTest()
    {
        this.testEvaluator();
    }

    @Override
    void prepQRCode(final IQRCode code)
    {
        // Vertical line, length 5, penalty 3
        code.setModule(1, 2, true);
        code.setModule(1, 3, true);
        code.setModule(1, 4, true);
        code.setModule(1, 5, true);
        code.setModule(1, 6, true);

        // Vertical line, length 4, penalty 0
        code.setModule(3, 2, true);
        code.setModule(3, 3, true);
        code.setModule(3, 4, true);
        code.setModule(3, 5, true);

        // Vertical line, length 7, penalty 5
        code.setModule(5, 2, false);
        code.setModule(5, 3, false);
        code.setModule(5, 4, false);
        code.setModule(5, 5, false);
        code.setModule(5, 6, false);
        code.setModule(5, 7, false);
        code.setModule(5, 8, false);

        // Horizontal line, length 3, penalty 0
        code.setModule(7, 2, true);
        code.setModule(8, 2, true);
        code.setModule(9, 2, true);

        // Horizontal line, length 5, penalty 3
        code.setModule(7, 4, false);
        code.setModule(8, 4, false);
        code.setModule(9, 4, false);
        code.setModule(10, 4, false);
        code.setModule(11, 4, false);
    }
}
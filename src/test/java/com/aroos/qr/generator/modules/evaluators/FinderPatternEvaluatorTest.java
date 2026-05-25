package com.aroos.qr.generator.modules.evaluators;

import org.testng.annotations.Test;

import com.aroos.qr.generator.modules.IQRCode;

public final class FinderPatternEvaluatorTest extends EvaluatorTest
{
    public FinderPatternEvaluatorTest()
    {
        super(new FinderPatternEvaluator(), 160);
    }

    @Test
    public void finderPatternEvaluatorTest()
    {
        this.testEvaluator();
    }

    @Override
    void prepQRCode(final IQRCode code)
    {
        // Vertical left hand pattern, penalty 40
        code.setModule(1, 2, false);
        code.setModule(1, 3, false);
        code.setModule(1, 4, false);
        code.setModule(1, 5, false);
        code.setModule(1, 6, true);
        code.setModule(1, 7, false);
        code.setModule(1, 8, true);
        code.setModule(1, 9, true);
        code.setModule(1, 10, true);
        code.setModule(1, 11, false);
        code.setModule(1, 12, true);

        // Vertical right hand pattern, penalty 40
        code.setModule(3, 2, true);
        code.setModule(3, 3, false);
        code.setModule(3, 4, true);
        code.setModule(3, 5, true);
        code.setModule(3, 6, true);
        code.setModule(3, 7, false);
        code.setModule(3, 8, true);
        code.setModule(3, 9, false);
        code.setModule(3, 10, false);
        code.setModule(3, 11, false);
        code.setModule(3, 12, false);

        // Horizontal left hand pattern, penalty 40
        code.setModule(1, 14, false);
        code.setModule(2, 14, false);
        code.setModule(3, 14, false);
        code.setModule(4, 14, false);
        code.setModule(5, 14, true);
        code.setModule(6, 14, false);
        code.setModule(7, 14, true);
        code.setModule(8, 14, true);
        code.setModule(9, 14, true);
        code.setModule(10, 14, false);
        code.setModule(11, 14, true);

        // Horizontal right hand pattern, penalty 40
        code.setModule(1, 16, true);
        code.setModule(2, 16, false);
        code.setModule(3, 16, true);
        code.setModule(4, 16, true);
        code.setModule(5, 16, true);
        code.setModule(6, 16, false);
        code.setModule(7, 16, true);
        code.setModule(8, 16, false);
        code.setModule(9, 16, false);
        code.setModule(10, 16, false);
        code.setModule(11, 16, false);
    }
}
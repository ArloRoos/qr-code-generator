package com.aroos.qr.generator.modules.evaluators;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link SameColoredSquareEvaluator} class implements an evaluator which 
 * judges QR codes based on the presence of 2x2 squares of same-colored modules.
 */
public final class SameColoredSquareEvaluator implements IEvaluator
{
    /**
     * {@inheritDocs}
     */
    @Override
    public int evaluate(final IQRCode code)
    {
        return 0;
    }
}
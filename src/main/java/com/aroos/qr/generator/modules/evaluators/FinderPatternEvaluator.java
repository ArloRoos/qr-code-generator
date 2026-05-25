package com.aroos.qr.generator.modules.evaluators;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link FinderPatternEvaluator} class implements an evaluator which 
 * judges QR codes based on the presence of patterns which could be mistaken for
 * finder patterns.
 */
public final class FinderPatternEvaluator implements IEvaluator
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
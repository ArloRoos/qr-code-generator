package com.aroos.qr.generator.modules.evaluators;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link ModulePercentageEvaluator} class implements an evaluator which
 * judges QR codes based on the dark/light module ratio (the closer to 50% the 
 * better).
 */
public final class ModulePercentageEvaluator implements IEvaluator
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
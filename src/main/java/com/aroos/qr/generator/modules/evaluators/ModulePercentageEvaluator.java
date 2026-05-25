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
        int totalModules = 0;
        int darkModules = 0;

        for (int i = 0; i < code.getSize(); i++)
        {
            for (int j = 0; j < code.getSize(); j++)
            {
                if (code.isSet(i, j))
                {
                    totalModules++;

                    if (code.getModule(i, j))
                    {
                        darkModules++;
                    }
                }
            }
        }

        final double darkModulePercent = ((double)darkModules / (double)totalModules) * (double)100;
        final int rounded = Math.toIntExact(Math.round(darkModulePercent));
        final int lower5 = rounded - (rounded % 5);
        final int upper5 = lower5 + 5;

        final int lowerSubtracted = Math.abs(lower5 - 50);
        final int upperSubtracted = Math.abs(upper5 - 50);

        // These are garunteed to not truncate anything, since both numbers are
        // multiples of 5.
        final int lowerDivided = lowerSubtracted / 5;
        final int upperDivided = upperSubtracted / 5;

        return Math.min(lowerDivided, upperDivided) * 10;
    }
}
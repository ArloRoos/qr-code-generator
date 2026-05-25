package com.aroos.qr.generator.modules.evaluators;

import java.util.Collection;
import java.util.List;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link CompositeEvaluator} class implements behavior for a composite 
 * evaluation method which combines the penalty score from all other evaluator
 * implementations.
 */
public final class CompositeEvaluator implements IEvaluator
{
    private static final Collection<IEvaluator> DELEGATES = List.of(
        new SameColoredLineEvaluator(),
        new SameColoredSquareEvaluator(),
        new ModulePercentageEvaluator(),
        new FinderPatternEvaluator());

    /**
     * {@inheritDoc}
     */
    @Override
    public int evaluate(final IQRCode code)
    {
        return DELEGATES.stream()
            .mapToInt(eval -> eval.evaluate(code))
            .sum();
    }
}
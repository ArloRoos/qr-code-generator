package com.aroos.qr.generator.modules.evaluators;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link SameColoredLineEvaluator} class implements an evaluator which 
 * judges QR codes based on the presence of 5 or more same-colored modules in a
 * row.
 */
public final class SameColoredLineEvaluator implements IEvaluator
{
    /**
     * {@inheritDocs}
     */
    @Override
    public int evaluate(final IQRCode code)
    {
        final ScanState columnState = new ScanState();
        final ScanState rowState = new ScanState();
        int penalty = 0;

        for (int i = 0; i < code.getSize(); i++)
        {
            columnState.reset();
            rowState.reset();

            for (int j = 0; j < code.getSize(); j++)
            {
                if (code.isSet(i, j))
                {
                    penalty += columnState.getPenalty(code.getModule(i, j));
                }
                else
                {
                    columnState.reset();
                }

                if (code.isSet(j, i))
                {
                    penalty += rowState.getPenalty(code.getModule(j, i));
                }
                else
                {
                    rowState.reset();
                }
            }
        }

        return penalty;
    }

    private final class ScanState
    {
        private final AtomicBoolean currentColor;
        private final AtomicInteger currentCount;

        public ScanState()
        {
            this.currentColor = new AtomicBoolean(false);
            this.currentCount = new AtomicInteger(0);
        }

        public void reset()
        {
            this.currentColor.set(false);
            this.currentCount.set(0);
        }

        public int getPenalty(final boolean value)
        {
            if (currentColor.compareAndSet(!value, value))
            {
                currentCount.set(1);
            }
            else
            {
                currentCount.incrementAndGet();
            }

            return currentCount.get() < 5
                ? 0
                : currentCount.get() == 5
                    ? 3
                    : 1;
        }
    }
}
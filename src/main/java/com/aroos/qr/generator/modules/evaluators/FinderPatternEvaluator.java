package com.aroos.qr.generator.modules.evaluators;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link FinderPatternEvaluator} class implements an evaluator which 
 * judges QR codes based on the presence of patterns which could be mistaken for
 * finder patterns.
 */
public final class FinderPatternEvaluator implements IEvaluator
{
    private static final List<Boolean> LEFT_HAND_PATTERN =
        List.of(false, false, false, false, true, false, true, true, true, false, true);

    private static final List<Boolean> RIGHT_HAND_PATTERN =
        List.of(true, false, true, true, true, false, true, false, false, false, false);

    /**
     * {@inheritDocs}
     */
    @Override
    public int evaluate(final IQRCode code)
    {
        final ScanState columnState1 = new ScanState(LEFT_HAND_PATTERN);
        final ScanState columnState2 = new ScanState(RIGHT_HAND_PATTERN);
        final ScanState rowState1 = new ScanState(LEFT_HAND_PATTERN);
        final ScanState rowState2 = new ScanState(RIGHT_HAND_PATTERN);

        int penalty = 0;

        for (int i = 0; i < code.getSize(); i++)
        {
            columnState1.reset();
            columnState2.reset();
            rowState1.reset();
            rowState2.reset();

            for (int j = 0; j < code.getSize(); j++)
            {
                if (code.isSet(i, j))
                {
                    penalty += columnState1.getPenalty(code.getModule(i, j));
                    penalty += columnState2.getPenalty(code.getModule(i, j));
                }
                else
                {
                    columnState1.reset();
                    columnState2.reset();
                }

                if (code.isSet(j, i))
                {
                    penalty += rowState1.getPenalty(code.getModule(j, i));
                    penalty += rowState2.getPenalty(code.getModule(j, i));
                }
                else
                {
                    rowState1.reset();
                    rowState2.reset();
                }
            }
        }

        return penalty;
    }

    private final class ScanState
    {
        private final List<Boolean> pattern;
        private final AtomicInteger currentCount;

        public ScanState(final List<Boolean> pattern)
        {
            this.pattern = pattern;
            this.currentCount = new AtomicInteger(0);
        }

        public void reset()
        {
            this.currentCount.set(0);
        }

        public int getPenalty(final boolean value)
        {
            if (value == this.pattern.get(this.currentCount.get()))
            {
                this.currentCount.incrementAndGet();
            }
            else
            {
                this.reset();
            }

            if (this.currentCount.get() == this.pattern.size())
            {
                this.reset();

                return 40;
            }

            return 0;
        }
    }
}
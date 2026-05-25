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
        int penalty = 0;

        for (int i = 0; i < code.getSize(); i++)
        {
            for (int j = 0; j < code.getSize(); j++)
            {
                penalty += checkSquare(i, j, code) ? 3 : 0;
            }
        }

        return penalty;
    }

    private boolean checkSquare(final int x, final int y, final IQRCode code)
    {
        // Can't check a 2 by 2 square if it's on the edge of the grid.
        if (x == code.getSize() - 1 || y == code.getSize() - 1)
        {
            return false;
        }

        if (!code.isSet(x, y) || !code.isSet(x + 1, y) || !code.isSet(x, y + 1) || !code.isSet(x + 1, y + 1))
        {
            return false;
        }

        final boolean expected = code.getModule(x, y);

        return
            (code.getModule(x + 1, y) == expected) &&
            (code.getModule(x, y + 1) == expected) &&
            (code.getModule(x + 1, y + 1) == expected);
    }
}
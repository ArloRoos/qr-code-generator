package com.aroos.qr.generator.math;

import java.util.ArrayList;
import java.util.List;

abstract class MathTest
{
    protected static ITerm algebraicTerm(final double c, final int e)
    {
        return new AlgebraicTerm(c, e);
    }

    protected static ITerm galoisTerm(final int c, final int e)
    {
        return new GaloisTerm(c, e);
    }

    protected static IPolynomial poly(final String stringForm)
    {
        final String[] terms = stringForm.split(" *\\+ *");
        final List<ITerm> termList = new ArrayList<>();

        for (final String term : terms)
        {
            if (term.contains("x"))
            {
                final double c = term.startsWith("x")
                    ? 1
                    : Double.parseDouble(term.substring(0, term.indexOf("x")));

                final int e = term.endsWith("x")
                    ? 1
                    : Integer.parseInt(term.substring(term.indexOf("^") + 1));

                termList.add(algebraicTerm(c, e));
            }
            else
            {
                termList.add(algebraicTerm(Double.parseDouble(term), 0));
            }
        }

        return new Polynomial(termList);
    }
}
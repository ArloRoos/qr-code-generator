package com.aroos.qr.generator.math;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link IPolynomial} interface defines behavior for a polynomial comprised
 * of a list of {@link ITerm}s.
 */
public interface IPolynomial extends IOperand<IPolynomial>
{
    /**
     * Gets the terms of this polynomial.
     * @return The term list.
     */
    List<ITerm> getTerms();

    /**
     * Gets the term of this polynomial with the largest exponent.
     * @return The largest term.
     */
    ITerm getLargestTerm();

    /**
     * Divides this polynomial by another, returning both the quotient and
     * remainder polynomials.
     * @param other The polynomial divisor.
     * @return The quotient structs.
     */
    IQuotient dividedByExact(IPolynomial other);

    public interface IQuotient
    {
        /**
         * Gets the quotient of the division operation.
         * @return The quotient.
         */
        IPolynomial getQuotient();

        /**
         * Gets the remainder of the division operation.
         * @return The remainder.
         */
        IPolynomial getRemainder();
    }

    /**
     * Create an algebraic polynomial from a human-readable string. Only the
     * variable "x" is allowed as a term, and negative coefficients cannot be
     * shortened using the minus (-) operator. For instance:
     *
     * x^2 - 2  ---- Invalid
     * x^2 + -2 ---- Valid
     *
     * As shown in the previous example, lone constants are recognized. Lone
     * variables (as in, a term with an exponent of 1), are also recognized.
     * @param polyString The polynomial string.
     * @return A parsed algebraic polynomial object.
     */
    static IPolynomial from(final String polyString)
    {
        return IPolynomial.from(polyString, false);
    }

    /**
     * Create a polynomial from a human-readable string. Only the variable "x"
     * is allowed as a term, and negative coefficients cannot be shortened using
     * the minus (-) operator. For instance:
     *
     * x^2 - 2  ---- Invalid
     * x^2 + -2 ---- Valid
     *
     * As shown in the previous example, lone constants are recognized. Lone
     * variables (as in, a term with an exponent of 1), are also recognized.
     * @param polyString The polynomial string.
     * @param galios A boolean flag indicating whether the polynomial should be
     * constructed with galois terms.
     * @return A parsed polynomial object.
     */
    static IPolynomial from(final String polyString, final boolean galois)
    {
        final String[] terms = polyString.split(" *\\+ *");
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

                processTerm(c, e, termList, galois);
            }
            else
            {
                final double c = Double.parseDouble(term);

                processTerm(c, 0, termList, galois);
            }
        }

        return new Polynomial(termList);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private helpers
    ////////////////////////////////////////////////////////////////////////////

    private static void processTerm(final double c, final int e, final List<ITerm> terms, final boolean galois)
    {
        if (galois)
        {
            if (Math.floor(c) != c)
            {
                throw new IllegalArgumentException(
                    "Cannot parse galois term from non-integer coefficient %f".formatted(c));
            }

            terms.add(new GaloisTerm((int)c, e));
        }
        else
        {
            terms.add(new AlgebraicTerm(c, e));
        }
    }
}
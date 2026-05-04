package com.aroos.qr.generator.math;

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
}
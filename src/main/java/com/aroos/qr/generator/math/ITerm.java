package com.aroos.qr.generator.math;

/**
 * The {@link ITerm} interface defines behavior for one "term" in a polynomial.
 * A term is comprised of two values: the exponent power and the coefficient.
 */
public interface ITerm extends IOperand<ITerm>
{
    /**
     * Multiplies this term's coefficient by a scalar.
     * @param scalar The scalar to multiply by.
     * @return The product term.
     */
    ITerm multipliedBy(double scalar);

    /**
     * Divides this term's coefficient by a scalar.
     * @param scalar The scalar to divide by.
     * @return The result of the division.
     */
    ITerm dividedBy(double scalar);

    /**
     * Raise this term to a scalar.
     * @param scalar The scalar to multiply this term's exponent by.
     * @return The exponentiation term.
     */
    ITerm exponentiated(double scalar);

    /**
     * Gets this term's coefficient.
     * @return The coefficient.
     */
    double getCoefficient();

    /**
     * Gets this term's exponent.
     * @return The exponent.
     */
    double getExponent();

    /**
     * Creates a new term given the coefficient and exponent.
     * @param coefficient The coefficient.
     * @param exponent The exponent.
     * @return A new algebraic term.
     */
    static ITerm of(double coefficient, double exponent)
    {
        return new AlgebraicTerm(coefficient, exponent);
    }
}
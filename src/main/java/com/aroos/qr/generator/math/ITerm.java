package com.aroos.qr.generator.math;

/**
 * The {@link ITerm} interface defines behavior for one "term" in a polynomial.
 * A term is comprised of two values: the exponent power and the coefficient.
 */
public interface ITerm extends IOperand<ITerm>, Comparable<ITerm>
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
     * @return The quotient.
     */
    ITerm dividedBy(double scalar);

    /**
     * Raise this term to a scalar.
     * @param scalar The scalar to multiply this term's exponent by.
     * @return The exponentiation term.
     */
    ITerm exponentiated(int scalar);

    /**
     * Gets this term's coefficient.
     * @return The coefficient.
     */
    double getCoefficient();

    /**
     * Gets this term's exponent.
     * @return The exponent.
     */
    int getExponent();
}

package com.aroos.qr.generator.math;

import java.util.Objects;

/**
 * The {@link AlgebraicTerm} class implements an algebraic polynomial term,
 * operating with standard mathematical functions.
 */
public final class AlgebraicTerm implements ITerm
{
    private final double coefficient;
    private final double exponent;

    AlgebraicTerm(final double coefficient, final double exponent)
    {
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    ////////////////////////////////////////////////////////////////////////////
    // region Object
    ////////////////////////////////////////////////////////////////////////////

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        final String formattedCoefficient = (int)this.coefficient == this.coefficient
            ? "%d".formatted((int)this.coefficient)
            : "%.2f".formatted(this.coefficient);

        final String formattedExponent = (int)this.exponent == this.exponent
            ? "%d".formatted((int)this.exponent)
            : "%.2f".formatted(this.exponent);

        return "%sx^%s".formatted(formattedCoefficient, formattedExponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object other)
    {
        return other instanceof AlgebraicTerm term &&
            term.coefficient == this.coefficient &&
            term.exponent == this.exponent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(this.coefficient, this.exponent);
    }

    ////////////////////////////////////////////////////////////////////////////
    // region ITerm
    ////////////////////////////////////////////////////////////////////////////

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCoefficient()
    {
        return this.coefficient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getExponent()
    {
        return this.exponent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITerm exponentiated(final double scalar)
    {
        return new AlgebraicTerm(this.coefficient, this.exponent * scalar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITerm multipliedBy(final double scalar)
    {
        return new AlgebraicTerm(this.coefficient * scalar, this.exponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITerm dividedBy(final double scalar)
    {
        return new AlgebraicTerm(this.coefficient / scalar, this.exponent);
    }

    ////////////////////////////////////////////////////////////////////////////
    // region IOperand
    ////////////////////////////////////////////////////////////////////////////

    /**
     * {@inheritDoc}
     */
    @Override
    public ITerm dividedBy(final ITerm other)
    {
        return new AlgebraicTerm(
            this.coefficient / other.getCoefficient(),
            this.exponent - other.getExponent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITerm multipliedBy(final ITerm other)
    {
        return new AlgebraicTerm(
            this.coefficient * other.getCoefficient(),
            this.exponent + other.getExponent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITerm minus(final ITerm other)
    {
        if (this.getExponent() != other.getExponent())
        {
            throw new IllegalArgumentException(
                "Invalid operation (different exponents): %s - %s".formatted(this, other));
        }

        return new AlgebraicTerm(
            this.coefficient - other.getCoefficient(),
            this.exponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITerm plus(final ITerm other)
    {
        if (this.getExponent() != other.getExponent())
        {
            throw new IllegalArgumentException(
                "Invalid operation (different exponents): %s + %s".formatted(this, other));
        }

        return new AlgebraicTerm(
            this.coefficient + other.getCoefficient(),
            this.exponent);
    }
}

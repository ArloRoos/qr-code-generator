
package com.aroos.qr.generator.math;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * The {@link AlgebraicTerm} class implements an algebraic polynomial term,
 * operating with standard mathematical functions.
 */
public final class AlgebraicTerm implements ITerm
{
    private final double coefficient;
    private final int exponent;

    public AlgebraicTerm(final double coefficient, final int exponent)
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

        return "%sx^%d".formatted(formattedCoefficient, this.exponent);
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
    // region Comparable
    ////////////////////////////////////////////////////////////////////////////

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(ITerm other)
    {
        return Stream.of(
            Double.compare(other.getExponent(), this.getExponent()),
            Double.compare(other.getCoefficient(), this.getCoefficient()))
            .takeWhile(result -> result != 0)
            .findAny()
            .orElse(0);
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
    public int getExponent()
    {
        return this.exponent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITerm exponentiated(final int scalar)
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

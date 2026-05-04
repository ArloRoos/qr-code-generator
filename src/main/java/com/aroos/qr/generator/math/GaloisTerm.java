package com.aroos.qr.generator.math;

import java.util.Objects;
import java.util.stream.Stream;

/*
 * The {@link GaloisTerm} class implements a polynomial term which performs 
 * operations under a Galois field, specifically using bit-wise modulo 2 
 * arithmetic, and byte-wise modulo 100011101 (285 in decimal) arithmetic. 
 * Within this field, all addition and subtraction is performed using the XOR
 * operator, and no number can go beyond what can be represented with an 8-bit
 * byte (255 in decimal). If one does as a result of an operation, it must then
 * be XORed with 285.
 */
public final class GaloisTerm implements ITerm
{
    private final int coefficient;
    private final int exponent;

    public GaloisTerm(final int coefficient, final int exponent)
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
        return "%dx^%d".formatted(this.coefficient, this.exponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj)
    {
        return obj instanceof GaloisTerm term &&
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
            Integer.compare(other.getExponent(), this.getExponent()),
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
        return new GaloisTerm(this.coefficient, this.exponent * scalar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITerm multipliedBy(final double scalar)
    {
        return new GaloisTerm(galoisMultiply(this.coefficient, (int)scalar), this.exponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITerm dividedBy(final double scalar)
    {
        throw new UnsupportedOperationException("Division is not supported for a Galois field term.");
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
        // This is a special case, since it's only used in polynomial division.
        // Generic division isn't necessarily defined in a Galois field, so the
        // result is simply the thing that I'd want to multiply the generator
        // polynomial by during long division. In this case that's just the 
        // coefficient of this term.
        return new GaloisTerm(
            this.coefficient,
            0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITerm multipliedBy(final ITerm other)
    {
        return new GaloisTerm(
            galoisAdd(this.coefficient, (int)other.getCoefficient()),
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

        return new GaloisTerm(
            galoisAdd(this.coefficient, (int)other.getCoefficient()),
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

        return new GaloisTerm(
            galoisAdd(this.coefficient, (int)other.getCoefficient()),
            this.exponent);
    }

    ////////////////////////////////////////////////////////////////////////////
    // region Private helpers
    ////////////////////////////////////////////////////////////////////////////
    
    private static int galoisAdd(final int a, final int b)
    {
        return a ^ b;
    }

    private static int galoisMultiply(final int a, final int b)
    {
        final int aExp = Galois.antilog(a);
        final int bExp = Galois.antilog(b);
        final int expSum = (aExp + bExp) % 255;

        return Galois.log(expSum);
    }
}

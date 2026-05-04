package com.aroos.qr.generator.math;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.aroos.qr.generator.common.ResourceReader;

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
    ////////////////////////////////////////////////////////////////////////////
    // region Log/antilogs
    ////////////////////////////////////////////////////////////////////////////
    
    // Use when going from exponent -> integer
    private static final Map<Integer, Integer> LOGS = new HashMap<>();

    // Use when going from integer -> exponenet
    private static final Map<Integer, Integer> ANTILOGS = new HashMap<>();

    private static final Pattern LOG_ENTRY = Pattern.compile("(\\d+):(\\d+)");

    static
    {
        final String logs = ResourceReader.read("logs.txt");
        final String antilogs = ResourceReader.read("antilogs.txt");

        logs.lines()
            .map(LOG_ENTRY::matcher)
            .filter(Matcher::find)
            .forEach(m -> LOGS.put(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))));
            
        antilogs.lines()
            .map(LOG_ENTRY::matcher)
            .filter(Matcher::find)
            .forEach(m -> ANTILOGS.put(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))));
    }

    ////////////////////////////////////////////////////////////////////////////
    // region GaloisTerm
    ////////////////////////////////////////////////////////////////////////////
    
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

    ////////////////////////////////////////////////////////////////////////////
    // region Private helpers
    ////////////////////////////////////////////////////////////////////////////
    
    private static int addCoefficient(final int a, final int b)
    {

    }

    private static int addExponent(final int a, final int b)
    {

    }
}

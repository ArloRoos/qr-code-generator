package com.aroos.qr.generator.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The {@link Polynomial} class implements a polynomial. Note that all
 * mathematical operations do NOT create new objects, and will modify the terms
 * of this polynomial reference.
 */
public final class Polynomial implements IPolynomial
{
    private final Map<Integer, ITerm> terms;

    public Polynomial(final List<ITerm> terms)
    {
        this.terms = new HashMap<>();

        terms.stream()
            .filter(t -> t.getCoefficient() != 0)
            .forEach(t -> addSingleTerm(t, this.terms));
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
        return this.terms.values().stream()
            .sorted()
            .map(ITerm::toString)
            .collect(Collectors.joining(" + "));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj)
    {
        if (obj instanceof Polynomial poly)
        {
            final ITerm[] thisTerms = this.getTerms().stream()
                .sorted()
                .toArray(ITerm[]::new);
            final ITerm[] otherTerms = poly.getTerms().stream()
                .sorted()
                .toArray(ITerm[]::new);

            return Arrays.equals(thisTerms, otherTerms);
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return Arrays.hashCode(this.getTerms().stream().toArray());
    }

    ////////////////////////////////////////////////////////////////////////////
    // region IPolynomial
    ////////////////////////////////////////////////////////////////////////////

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ITerm> getTerms()
    {
        return List.copyOf(this.terms.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITerm getLargestTerm()
    {
        return this.getTerms().stream()
            .sorted()
            .toList()
            .get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IQuotient dividedByExact(final IPolynomial other)
    {
        final List<ITerm> quotientTerms = new ArrayList<>();
        IPolynomial dividend = this;

        while (dividend.getLargestTerm().getExponent() >= other.getLargestTerm().getExponent())
        {
            final ITerm dividendLargest = dividend.getLargestTerm();
            final ITerm divisorLargest = other.getLargestTerm();
            final ITerm multiplier = dividendLargest.dividedBy(divisorLargest);
            final IPolynomial multiplierPoly = new Polynomial(List.of(multiplier));
            final IPolynomial subtractorPoly = other.multipliedBy(multiplierPoly);

            dividend = dividend.minus(subtractorPoly);

            quotientTerms.add(multiplier);
        }

        final IPolynomial quotient = new Polynomial(quotientTerms);

        return new Quotient(quotient, dividend);
    }

    ////////////////////////////////////////////////////////////////////////////
    // region IOperand
    ////////////////////////////////////////////////////////////////////////////

    /**
     * {@inheritDoc}
     */
    @Override
    public IPolynomial plus(IPolynomial other)
    {
        final Map<Integer, ITerm> cpy = this.getMutableTermCopy();

        other.getTerms().forEach(t -> addSingleTerm(t, cpy));

        return fromTermMap(cpy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPolynomial minus(IPolynomial other)
    {
        final Map<Integer, ITerm> cpy = this.getMutableTermCopy();

        other.getTerms().forEach(t -> subtractSingleTerm(t, cpy));

        return fromTermMap(cpy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPolynomial multipliedBy(IPolynomial other)
    {
        final Map<Integer, ITerm> cpy = this.getMutableTermCopy();

        other.getTerms().forEach(t -> multiplySingleTerm(t, cpy));

        return fromTermMap(cpy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPolynomial dividedBy(final IPolynomial other)
    {
        throw new UnsupportedOperationException(
            "Non-remainder polynomial division not supported. Please use IPolynomial.dividedByExact().");
    }

    ////////////////////////////////////////////////////////////////////////////
    // region Public types
    ////////////////////////////////////////////////////////////////////////////

    private static class Quotient implements IQuotient
    {
        private final IPolynomial quotient;
        private final IPolynomial remainder;

        private Quotient(final IPolynomial quotient, final IPolynomial remainder)
        {
            this.quotient = quotient;
            this.remainder = remainder;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public IPolynomial getQuotient()
        {
            return this.quotient;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public IPolynomial getRemainder()
        {
            return this.remainder;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // region Private helpers
    ////////////////////////////////////////////////////////////////////////////

    private Map<Integer, ITerm> getMutableTermCopy()
    {
        final Map<Integer, ITerm> cpy = new HashMap<>();

        cpy.putAll(this.terms);

        return cpy;
    }

    private static void addSingleTerm(final ITerm term, final Map<Integer, ITerm> terms)
    {
        terms.merge(term.getExponent(), term, (t1, t2) -> t1.plus(t2));
    }

    private static void subtractSingleTerm(final ITerm term, final Map<Integer, ITerm> terms)
    {
        terms.merge(term.getExponent(), term.multipliedBy(-1), (t1, t2) -> t1.minus(t2.multipliedBy(-1)));
    }

    private static void multiplySingleTerm(final ITerm term, final Map<Integer, ITerm> terms)
    {
        final List<ITerm> termsCpy = terms.values().stream().toList();

        terms.clear();

        termsCpy.stream()
            .map(term::multipliedBy)
            .forEach(t -> addSingleTerm(t, terms));
    }

    private static IPolynomial fromTermMap(final Map<Integer, ITerm> terms)
    {
        return new Polynomial(terms.values().stream().toList());
    }
}
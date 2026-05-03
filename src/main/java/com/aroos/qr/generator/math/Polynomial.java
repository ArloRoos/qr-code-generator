package com.aroos.qr.generator.math;

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

        terms.forEach(this::addSingleTerm);
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

    public List<ITerm> getTerms()
    {
        return List.copyOf(this.terms.values());
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
        other.getTerms().forEach(this::addSingleTerm);

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPolynomial minus(IPolynomial other)
    {
        other.getTerms().forEach(this::subtractSingleTerm);

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPolynomial multipliedBy(IPolynomial other)
    {
        other.getTerms().forEach(this::multiplySingleTerm);

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPolynomial dividedBy(final IPolynomial other)
    {
        // TODO
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    // region Private helpers
    ////////////////////////////////////////////////////////////////////////////

    private void addSingleTerm(final ITerm term)
    {
        this.terms.merge(term.getExponent(), term, (t1, t2) -> t1.plus(t2));
    }

    private void subtractSingleTerm(final ITerm term)
    {
        this.terms.merge(term.getExponent(), term, (t1, t2) -> t1.minus(t2));
    }

    private void multiplySingleTerm(final ITerm term)
    {
        final List<ITerm> termsCpy = this.getTerms();

        this.terms.clear();

        termsCpy.stream()
            .map(term::plus)
            .forEach(this::addSingleTerm);
    }
}
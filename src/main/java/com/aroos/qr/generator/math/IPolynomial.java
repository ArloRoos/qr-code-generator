package com.aroos.qr.generator.math;

import java.util.List;

/**
 * The {@link IPolynomial} interface defines behavior for a polynomial comprised
 * of a list of {@link ITerm}s.
 */
public interface IPolynomial extends IOperand<IPolynomial>
{
    List<ITerm> getTerms();
}
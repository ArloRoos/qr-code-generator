package com.aroos.qr.generator.math;

abstract class MathTest
{
    protected static ITerm algebraicTerm(final double c, final int e)
    {
        return new AlgebraicTerm(c, e);
    }

    protected static ITerm galoisTerm(final int c, final int e)
    {
        return new GaloisTerm(c, e);
    }
}
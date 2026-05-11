package com.aroos.qr.generator.math;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.testng.annotations.Test;

public final class GaloisTermTest extends MathTest
{
    @Test
    public void multiplyTest()
    {
        final ITerm term1 = galoisTerm(10, 2);
        final ITerm term2 = galoisTerm(5, 3);

        assertThat(term1.multipliedBy(term2))
            .isEqualTo(galoisTerm(34, 5));

        assertThat(term2.multipliedBy(2))
            .isEqualTo(galoisTerm(10, 3));
    }

    @Test
    public void divideTest()
    {
        final ITerm term1 = galoisTerm(10, 3);
        final ITerm term2 = galoisTerm(5, 2);

        assertThat(term1.dividedBy(term2))
            .isEqualTo(galoisTerm(10, 1));
    }

    @Test
    public void minusTest()
    {
        final ITerm term1 = galoisTerm(10, 2);
        final ITerm term2 = galoisTerm(5, 2);

        assertThat(term1.minus(term2))
            .isEqualTo(galoisTerm(15, 2));
    }

    @Test
    public void plusTest()
    {
        final ITerm term1 = galoisTerm(10, 2);
        final ITerm term2 = galoisTerm(5, 2);

        assertThat(term1.plus(term2))
            .isEqualTo(galoisTerm(15, 2));
    }

    @Test
    public void invalidTest()
    {
        final ITerm term1 = galoisTerm(10, 2);
        final ITerm term2 = galoisTerm(5, 3);

        assertThatThrownBy(() -> term1.minus(term2))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("10x^2 - 5x^3");

        assertThatThrownBy(() -> term1.plus(term2))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("10x^2 + 5x^3");

        assertThatThrownBy(() -> term1.dividedBy(2))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
    }
}
package com.aroos.qr.generator.math;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.testng.annotations.Test;

public final class AlgebraicTermTest extends MathTest
{
    @Test
    public void exponentiatedTest()
    {
        final ITerm term = term(10, 5);

        assertThat(term.exponentiated(2))
            .isEqualTo(term(10, 10));
    }
    
    @Test
    public void multiplyTest()
    {
        final ITerm term1 = term(10, 2);
        final ITerm term2 = term(5, 3);

        assertThat(term1.multipliedBy(term2))
            .isEqualTo(term(50, 5));

        assertThat(term2.multipliedBy(2))
            .isEqualTo(term(10, 3));
    }

    @Test
    public void divideTest()
    {
        final ITerm term1 = term(10, 2);
        final ITerm term2 = term(5, 3);

        assertThat(term1.dividedBy(term2))
            .isEqualTo(term(2, -1));

        assertThat(term2.dividedBy(2))
            .isEqualTo(term(2.5, 3));
    }

    @Test
    public void minusTest()
    {
        final ITerm term1 = term(10, 2);
        final ITerm term2 = term(5, 2);

        assertThat(term1.minus(term2))
            .isEqualTo(term(5, 2));
    }

    @Test
    public void plusTest()
    {
        final ITerm term1 = term(10, 2);
        final ITerm term2 = term(5, 2);

        assertThat(term1.plus(term2))
            .isEqualTo(term(15, 2));
    }

    @Test
    public void invalidExponentTest()
    {
        final ITerm term1 = term(10, 2);
        final ITerm term2 = term(5, 3);

        assertThatThrownBy(() -> term1.minus(term2))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("10x^2 - 5x^3");

        assertThatThrownBy(() -> term1.plus(term2))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("10x^2 + 5x^3");
    }
}
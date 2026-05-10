package com.aroos.qr.generator.math;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aroos.qr.generator.math.IPolynomial.IQuotient;

public final class PolynomialTest extends MathTest
{
    @DataProvider
    public Object[][] additionTestCases()
    {
       return Stream.of(
            new Object[] { "2x^3 + x^2 + 1", "x + 3", "2x^3 + x^2 + x + 4"},
            new Object[] { "-1x^2 + -2x", "-3x^2 + x + -1", "-4x^2 + -1x + -1"},
            new Object[] { "0.2x + -2", "0.5x + -5", "0.7x + -7"})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "additionTestCases")
    public void addTest(final String p1, final String p2, final String expectedSum)
    {
        assertThat(IPolynomial.from(p1).plus(IPolynomial.from(p2)))
            .isEqualTo(IPolynomial.from(expectedSum));
    }

    @DataProvider
    public Object[][] subtractionTestCases()
    {
       return Stream.of(
            new Object[] { "2x^3 + x^2 + 1", "x + 3", "2x^3 + x^2 + -1x + -2"},
            new Object[] { "-1x^2 + -2x", "-3x^2 + x + -1", "2x^2 + -3x + 1"},
            new Object[] { "0.2x + -2", "0.5x + -5", "-0.3x + 3"})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "subtractionTestCases")
    public void subtractionTest(final String p1, final String p2, final String expectedDifference)
    {
        assertThat(IPolynomial.from(p1).minus(IPolynomial.from(p2)))
            .isEqualTo(IPolynomial.from(expectedDifference));
    }

    @DataProvider
    public Object[][] multiplicationTestCases()
    {
       return Stream.of(
            new Object[] { "2x^3 + x^2 + 1", "x + 3", "2x^4 + 7x^3 + 3x^2 + x + 3"},
            new Object[] { "-1x^2 + -2x", "-3x^2 + x + -1", "3x^4 + 5x^3 + -1x^2 + 2x"},
            new Object[] { "0.2x + -2", "0.5x + -5", "0.1x^2 + -2x + 10"},
            new Object[] { "x + 1", "3x^2", "3x^3 + 3x^2"})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "multiplicationTestCases")
    public void multiplicationTest(final String p1, final String p2, final String expectedProduct)
    {
        assertThat(IPolynomial.from(p1).multipliedBy(IPolynomial.from(p2)))
            .isEqualTo(IPolynomial.from(expectedProduct));
    }

    @DataProvider
    public Object[][] divisionTestCases()
    {
       return Stream.of(
            new Object[] { "3x^2 + x + -1", "x + 1", "3x + -2", "1"},
            new Object[] { "x^3 + -2x^2 + -4", "x + -3", "x^2 + x + 3", "5"})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "divisionTestCases")
    public void divisionTest(
        final String dividend,
        final String divisor,
        final String expectedQuotient,
        final String expectedRemainder)
    {
        final IQuotient quotient = IPolynomial.from(dividend).dividedByExact(IPolynomial.from(divisor));

        assertThat(quotient.getQuotient())
            .isEqualTo(IPolynomial.from(expectedQuotient));

        assertThat(quotient.getRemainder())
            .isEqualTo(IPolynomial.from(expectedRemainder));
    }
}
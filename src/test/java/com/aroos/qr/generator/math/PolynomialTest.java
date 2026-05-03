package com.aroos.qr.generator.math;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
        assertThat(poly(p1).plus(poly(p2)))
            .isEqualTo(poly(expectedSum));
    }

    @DataProvider
    public Object[][] subtractionTestCases()
    {
       return Stream.of(
            new Object[] { "2x^3 + x^2 + 1", "x + 3", "2x^3 + x^2 + x + -2"},
            new Object[] { "-1x^2 + -2x", "-3x^2 + x + -1", "2x^2 + -3x + -1"},
            new Object[] { "0.2x + -2", "0.5x + -5", "-0.3x + 3"})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "subtractionTestCases")
    public void subtractionTest(final String p1, final String p2, final String expectedDifference)
    {
        assertThat(poly(p1).minus(poly(p2)))
            .isEqualTo(poly(expectedDifference));
    }

    @DataProvider
    public Object[][] multiplicationTestCases()
    {
       return Stream.of(
            new Object[] { "2x^3 + x^2 + 1", "x + 3", "2x^4 + 7x^3 + 3x^2 + x + 3"},
            new Object[] { "-1x^2 + -2x", "-3x^2 + x + -1", "2x^2 + -3x + -1"},
            new Object[] { "0.2x + -2", "0.5x + -5", "-0.3x + 3"})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "subtractionTestCases")
    public void multiplicationTest(final String p1, final String p2, final String expectedDifference)
    {
        assertThat(poly(p1).minus(poly(p2)))
            .isEqualTo(poly(expectedDifference));
    }
}
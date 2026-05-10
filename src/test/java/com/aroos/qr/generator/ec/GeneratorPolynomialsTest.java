package com.aroos.qr.generator.ec;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aroos.qr.generator.math.Galois;
import com.aroos.qr.generator.math.IPolynomial;
import com.aroos.qr.generator.math.ITerm;

public final class GeneratorPolynomialsTest
{
    @DataProvider
    public Object[][] getTestCases()
    {
       return Stream.of(
            new Object[] { 2, new int[] { 0, 25, 1 }},
            new Object[] { 3, new int[] { 0, 198, 199, 3 }},
            new Object[] { 7, new int[] { 0, 87, 229, 146, 149, 238, 102, 21 }},
            new Object[] { 10, new int[] { 0, 251, 67, 46, 61, 118, 70, 64, 94, 32, 45} })
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "getTestCases")
    public void generatorPolynomialTest(final int codewords, final int[] expectedExponents)
    {
        final IPolynomial generator = GeneratorPolynomials.generate(codewords);

        // Assert that the exponents are every value from 0-codewords
        // (inclusive)

        assertThat(generator.getTerms())
            .hasSize(codewords + 1);

        for (int i = 0; i <= codewords; i++)
        {
            final int expectedExponent = i;

            assertThat(generator.getTerms().stream()
                .filter(t -> t.getExponent() == expectedExponent)
                .findAny())
                .isPresent();
        }

        // Assert that the coefficients are correct

        final List<ITerm> sorted = generator.getTerms().stream()
            .sorted()
            .toList();

        assertThat(sorted)
            .hasSameSizeAs(expectedExponents);

        for (int i = 0; i < expectedExponents.length; i++)
        {
            assertThat(Galois.antilog((int)sorted.get(i).getCoefficient()))
                .isEqualTo(expectedExponents[i]);
        }
    }
}

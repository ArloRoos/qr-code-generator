package com.aroos.qr.generator.ec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aroos.qr.generator.math.Galois;
import com.aroos.qr.generator.math.GaloisTerm;
import com.aroos.qr.generator.math.IPolynomial;
import com.aroos.qr.generator.math.Polynomial;

/**
 * The {@link GeneratorPolynomials} class implements a static utility for 
 * looking up the possible generator polynomials for error correction codeword
 * generation.
 */
public final class GeneratorPolynomials
{
    private static final Map<Integer, IPolynomial> GENERATOR_CACHE = new HashMap<>();

    static
    {
        // Add the initial generator polynomial for n=2.
        GENERATOR_CACHE.put(2, generatorStep(1).multipliedBy(generatorStep(2)));
    }

    public static IPolynomial generate(final int codewordCount)
    {
        if (codewordCount < 2)
        {
            throw new IllegalArgumentException(String.format(
                "No generator polynomial exists for n=%d",
                codewordCount));
        }

        if (GENERATOR_CACHE.containsKey(codewordCount))
        {
            return GENERATOR_CACHE.get(codewordCount);
        }

        final int maxGenerated = GENERATOR_CACHE.keySet().stream()
            .mapToInt(key -> key)
            .max()
            .orElse(2);

        for (int i = maxGenerated; i < codewordCount; i++)
        {
            final IPolynomial current = GENERATOR_CACHE.get(i);
            final IPolynomial next = generatorStep(i + 1);

            GENERATOR_CACHE.put(i + 1, current.multipliedBy(next));
        }

        return GENERATOR_CACHE.get(codewordCount);
    }

    private static IPolynomial generatorStep(final int count)
    {
        return new Polynomial(List.of(
            new GaloisTerm(1, 1),
            new GaloisTerm(Galois.log(count - 1), 0)));
    }
}
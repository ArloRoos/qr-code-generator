package com.aroos.qr.generator.encoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import com.aroos.qr.generator.common.ResourceReader;

/**
 * The {@link AlphaNumericEncoding} class implements behavior for a strategy that
 * encodes input strings using the alphanumeric encoding scheme.
 */
public final class AlphaNumericEncoding implements IQREncoding
{
    private static final Map<Character, Integer> CHARACTER_ENCODING = new HashMap<>();

    static
    {
        final String mappings = ResourceReader.read("alphanumeric.txt");

        try (
            final StringReader reader = new StringReader(mappings);
            final BufferedReader buffered = new BufferedReader(reader))
        {
            buffered.lines()
                .forEach(line ->
                {
                    final char character = line.charAt(0);
                    final int encoded = Integer.parseInt(line.substring(2));

                    CHARACTER_ENCODING.put(character, encoded);
                });
        }
        catch (final IOException error)
        {
            throw new UncheckedIOException(error);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BitSet encode(final String content, final int qrVersion)
    {
        return new BitSet();
    }
}
package com.aroos.qr.generator.encoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.common.ResourceReader;

/**
 * The {@link AlphaNumericEncoding} class implements behavior for a strategy that
 * encodes input strings using the alphanumeric encoding scheme.
 */
public final class AlphaNumericEncoding extends QREncoding
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

    public AlphaNumericEncoding(final QRConfiguration config)
    {
        super(config);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void encodeContent(final String content)
    {
    }
}
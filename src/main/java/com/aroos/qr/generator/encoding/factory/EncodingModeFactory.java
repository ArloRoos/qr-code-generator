package com.aroos.qr.generator.encoding.factory;

import java.util.stream.Stream;

import com.aroos.qr.generator.encoding.EncodingMode;

/**
 * The {@link EncodingModeFactory} class implements a factory which provides
 * the most space efficient encoding method for a given content string.
 */
public final class EncodingModeFactory implements IEncodingModeFactory
{
    /**
     * {@inheritDoc}
     */
    @Override
    public EncodingMode analyze(final String content)
    {
        return Stream.of(EncodingMode.values())
            .filter(mode -> mode.canEncode(content))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(
                "No encoding mode found to support content string [%s]".formatted(content)));
    }
}
package com.aroos.qr.generator.encoding;

import java.util.BitSet;

/**
 * The {@link ByteEncoding} class implements behavior for a strategy that
 * encodes input strings using the ISO_8859_1 encoding scheme.
 */
public final class ByteEncoding implements IQREncoding
{
    /**
     * {@inheritDoc}
     */
    @Override
    public BitSet encode(final String content)
    {
        return new BitSet();
    }
}
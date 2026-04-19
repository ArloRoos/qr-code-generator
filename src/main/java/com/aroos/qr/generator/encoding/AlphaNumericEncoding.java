package com.aroos.qr.generator.encoding;

import java.util.BitSet;

/**
 * The {@link AlphaNumericEncoding} class implements behavior for a strategy that
 * encodes input strings using the alphanumeric encoding scheme.
 */
public final class AlphaNumericEncoding implements IQREncoding
{
    /**
     * {@inheritDoc}
     */
    @Override
    public BitSet encode(final String content, final int qrVersion)
    {
        return new BitSet();
    }
}
package com.aroos.qr.generator.encoding;

import java.util.BitSet;

/**
 * The {@link NumericEncoding} class implements behavior for a strategy that 
 * encodes input strings using the pure numeric encoding scheme.
 */
public final class NumericEncoding implements IQREncoding
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
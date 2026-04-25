package com.aroos.qr.generator.encoding;

import java.util.BitSet;

class BitEncodingTest
{
    private final IQREncoding encodingStrategy;

    protected BitEncodingTest(final IQREncoding encodingStrategy)
    {
        this.encodingStrategy = encodingStrategy;
    }

    protected void encodingTest(final String content, final int qrVersion, final String expectedBits)
    {
        final BitSet result = this.encodingStrategy.encode(content, qrVersion);

        final BitSet expected = new BitSet();
    }
}
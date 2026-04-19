package com.aroos.qr.generator.encoding;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The {@link QREncoding} class implements a base class containing common
 * encoding methods for all QR encoding strategies.
 */
abstract class QREncoding
{
    private static final int CODE_LENGTH = 4;

    protected final BitSet bits;

    private final EncodingMode mode;
    private final AtomicInteger position;

    protected QREncoding(final EncodingMode mode)
    {
        this.bits = new BitSet();
        this.mode = mode;
        this.position = new AtomicInteger(0);
    }

    protected void putMode()
    {
        this.putInt(this.mode.code(), CODE_LENGTH);
    }

    protected void putLength(final int length, final int qrVersion)
    {
        this.putInt(length, this.mode.getLengthBits(qrVersion));
    }

    protected void putInt(final int val, final int length)
    {
        for (int i = 0; i < length; i++)
        {
            final boolean bit = (((val >> i) & 1) == 1);

            this.bits.set(this.position.getAndIncrement(), bit);
        }
    }
}
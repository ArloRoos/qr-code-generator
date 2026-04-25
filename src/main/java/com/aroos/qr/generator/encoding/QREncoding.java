package com.aroos.qr.generator.encoding;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;

import com.aroos.qr.generator.common.QRConfiguration;

/**
 * The {@link QREncoding} class implements a base class containing common
 * encoding methods for all QR encoding strategies.
 */
abstract class QREncoding implements IQREncoding
{
    private static final int CODE_LENGTH = 4;

    protected final BitSet bits;
    protected final QRConfiguration config;

    private final AtomicInteger position;

    protected QREncoding(final QRConfiguration config)
    {
        this.config = config;
        this.bits = new BitSet();
        this.position = new AtomicInteger(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BitSet encode(final String content)
    {
        this.putMode();
        this.putLength(content.length());
        this.encodeContent(content);
        this.putTerminator();
        this.putPadBytes();

        return this.bits;
    }

    /**
     * Encode the content string using this object's encoding method.
     * @param content The content to encode.
     */
    protected abstract void encodeContent(final String content);

    /**
     * Put the first length bits of an integer into the bit string,
     * @param val The integer to insert.
     * @param length The amount of bits to write from the value.
     */
    protected final void putInt(final int val, final int length)
    {
        for (int i = 0; i < length; i++)
        {
            final boolean bit = (((val >> i) & 1) == 1);

            this.bits.set(this.position.getAndIncrement(), bit);
        }
    }

    private void putMode()
    {
        this.putInt(this.config.mode().code(), CODE_LENGTH);
    }

    private void putLength(final int length)
    {
        this.putInt(length, this.config.mode().getLengthBits(this.config.version()));
    }

    private void putTerminator()
    {

    }

    private void putPadBytes()
    {

    }
}
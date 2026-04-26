package com.aroos.qr.generator.encoding;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import com.aroos.qr.generator.common.QRConfiguration;

/**
 * The {@link QREncoding} class implements a base class containing common
 * encoding methods for all QR encoding strategies.
 */
abstract class QREncoding implements IQREncoding
{
    private static final int CODE_LENGTH = 4;
    private static final int MAX_TERMINATOR_LENGTH = 4;
    private static final int PAD_BYTE_1 = 236;
    private static final int PAD_BYTE_2 = 17;

    protected final BitSet bits;
    protected final QRConfiguration config;

    private final AtomicInteger position;
    private final int requiredBits;

    protected QREncoding(final QRConfiguration config)
    {
        this.config = config;
        this.bits = new BitSet();
        this.position = new AtomicInteger(0);
        this.requiredBits = Capacities.getCodewordCount(config.version(), config.level()) * 8;
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
            final boolean bit = ((val >> (length - i - 1)) & 1) == 1;

            this.bits.set(this.position.getAndIncrement(), bit);
        }
    }

    /**
     * Partition the given string into a list of substrings with the given size.
     * @param content The content to partition.
     * @param partitionSize The size of each partition.
     * @return The partitions of the original string.
     */
    protected final Collection<String> partition(final String content, final int partitionSize)
    {
        final Collection<String> parts = new ArrayList<>();

        for (int i = 0; i < content.length(); i += partitionSize)
        {
            parts.add(content.substring(i, Math.min(content.length(), i + partitionSize)));
        }

        return parts;
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
        // Add a terminator of 0s until there are 4 or the total bit capacity is
        // filled.
        final int terminatorLength = Math.min(this.requiredBits - this.position.get(), MAX_TERMINATOR_LENGTH);

        for (int i = 0; i < terminatorLength; i++)
        {
            this.putInt(0, 1);
        }

        // Add padding 0s until the bit set is a multiple of 8.
        if (this.position.get() < this.requiredBits && this.position.get() % 8 != 0)
        {
            final int paddingLength = 8 - (this.position.get() % 8);

            for (int i = 0; i < paddingLength; i++)
            {
                this.putInt(0, 1);
            }
        }
    }

    private void putPadBytes()
    {
        // This division should always result in a whole number, since the
        // position should be a multiple of 8 by this point.
        final int padCount = (this.requiredBits - this.position.get()) / 8;

        for (int i = 0; i < padCount; i++)
        {
            // These bytes are an arbitrary part of the QR specification, and
            // alternate until the entire QR capacity is full.
            final int padByte = i % 2 == 0
                ? PAD_BYTE_1
                : PAD_BYTE_2;

            this.putInt(padByte, 8);
        }
    }
}
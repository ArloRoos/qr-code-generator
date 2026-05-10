package com.aroos.qr.generator.common;

import java.util.BitSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The {@link BitStream} class defines a structure which contains a stream
 * of individual bits. These bits can be manipulated 1-by-1, split into 8 bit
 * codewords, listed as a string, and other useful operations.
 */
public final class BitStream implements IBitStream
{
    private final BitSet bits;
    private final AtomicInteger size;

    public BitStream()
    {
        this.bits = new BitSet();
        this.size = new AtomicInteger(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return IntStream.range(0, this.size.get())
            .mapToObj(i -> this.bits.get(i))
            .map(boolValue -> boolValue ? "1" : "0")
            .collect(Collectors.joining());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean at(final int index)
    {
        return this.bits.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BitSet getBits()
    {
        return this.bits;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Byte> getCodewords()
    {
        if (this.size.get() % 8 != 0)
        {
            throw new IllegalStateException(
                "Current BitStream size %d cannot be divided into 8-bit codewords.".formatted(this.size.get()));
        }

        final byte[] bytes = this.bits.toByteArray();


        return IntStream.range(0, bytes.length)
            .mapToObj(i -> bytes[i])

        for (int i = 0; i < this.size.get() % 8; i++)
        {
            final byte[] bytes = this.getBits().toByteArray();
            final BitSet range = this.getBits().get(i, i + 8);
            final byte b = range.toByteArray()[0];

            codewords.add(b);
        }

        return codewords;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize()
    {
        return this.size.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putBit(final boolean value)
    {
        this.putInt(value ? 1 : 0, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putByte(final byte value)
    {
        this.putInt((int)value, Byte.BYTES);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putInt(final int value, final int length)
    {
        for (int i = 0; i < length; i++)
        {
            final boolean bit = ((value >> (length - i - 1)) & 1) == 1;

            this.bits.set(this.size.getAndIncrement(), bit);
        }
    }
}
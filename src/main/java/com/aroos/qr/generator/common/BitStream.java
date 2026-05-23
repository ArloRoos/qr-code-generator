package com.aroos.qr.generator.common;

import java.util.ArrayList;
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
    private final List<Boolean> bits;
    private final AtomicInteger size;

    public BitStream()
    {
        this.bits = new ArrayList<>();
        this.size = new AtomicInteger(0);
    }

    ////////////////////////////////////////////////////////////////////////////
    // region IBitStream
    ////////////////////////////////////////////////////////////////////////////

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
    public List<Byte> getCodewords()
    {
        if (this.size.get() % 8 != 0)
        {
            throw new IllegalStateException(
                "Current BitStream size %d cannot be divided into 8-bit codewords.".formatted(this.size.get()));
        }

        final byte[] bytes = this.toByteArray();

        return IntStream.range(0, bytes.length)
            .mapToObj(i -> bytes[i])
            .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Boolean> getBitList()
    {
        return List.copyOf(this.bits);
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
        this.putInt((int)value, 8);
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

            this.bits.add(bit);
            this.size.incrementAndGet();
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // region Private Helpers
    ////////////////////////////////////////////////////////////////////////////

    private byte[] toByteArray()
    {
        final int size = this.getSize() / 8;
        final byte[] bytes = new byte[size];

        for (int i = 0; i < this.getSize(); i += 8)
        {
            byte b = 0;

            for (int j = 0; j < 8; j++)
            {
                b <<= 1;

                if (this.at(i + j))
                {
                    b |= 1;
                }
            }

            bytes[i / 8] = b;
        }

        return bytes;
    }
}
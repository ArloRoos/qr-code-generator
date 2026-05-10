package com.aroos.qr.generator.common;

import java.util.BitSet;
import java.util.List;

/**
 * The {@link IBitStream} interface defines a structure which contains a stream
 * of individual bits. These bits can be manipulated 1-by-1, split into 8 bit
 * codewords, listed as a string, and other useful operations. 
 */
public interface IBitStream
{
    /**
     * Get the modifyable underlying {@link BitSet}.
     * @return The bit set backing this stream.
     */
    BitSet getBits();

    /**
     * Gets the current size of this bit stream. Note that this is NOT
     * equivalent to the size of the underlying {@link BitSet}.
     * @return The total number of bits put into this stream.
     */
    int getSize();

    /**
     * Gets the 8-bit codewords of this bit stream.
     * @return A list of codeword bytes.
     */
    List<Byte> getCodewords();

    /**
     * Gets the bit at the given index.
     * @param index The index.
     * @return The boolean bit value at the given index.
     */
    boolean at(int index);

    /**
     * Appends a given amount of bits from the integer to the end of the stream.
     * @param value The integer value to append.
     * @param size The number of bits to append (little endian ordering).
     */
    void putInt(int value, int size);

    /**
     * Appends a single bit to the end of the stream.
     * @param value The bit value.
     */
    void putBit(boolean value);

    /**
     * Appends a single codeword to the end of the stream.
     * @param value The codeword byte.
     */
    void putByte(byte value);
}
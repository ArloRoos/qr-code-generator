package com.aroos.qr.generator.encoding;

/**
 * The {@link EncodingMode} enumeration defines the various encoding modes that
 * a QR can encode its data with.
 */
public enum EncodingMode
{
    // Code: 0001
    NUMERIC(1, new int[] { 10, 12, 14 }),

    // Code: 0010
    ALPHANUMERIC(2, new int[] { 9, 11, 13 }),

    // Code: 0100
    BYTE(4, new int[] { 8, 16, 16 }),

    // Code: 1000
    KANJI(8, new int[] { 8, 10, 12 });

    private final int code;
    private final int[] lengthValues;

    EncodingMode(final int code, final int[] lengthValues)
    {
        this.code = code;
        this.lengthValues = lengthValues;
    }

    public int code()
    {
        return this.code;
    }

    public int getLengthBits(final int qrVersion)
    {
        if (qrVersion < 1 || qrVersion > 40)
        {
            throw new IllegalArgumentException("QR version %d is out of valid range [1-40]".formatted(qrVersion));
        }

        if (qrVersion < 10)
        {
            return this.lengthValues[0];
        }
        else if (qrVersion < 27)
        {
            return this.lengthValues[1];
        }
        else
        {
            return this.lengthValues[2];
        }
    }
}
package com.aroos.qr.generator.encoding;

import java.nio.charset.StandardCharsets;

import com.aroos.qr.generator.encoding.factory.EncodingModeFactory;
import com.aroos.qr.generator.encoding.factory.IEncodingModeFactory;

/**
 * The {@link EncodingMode} enumeration defines the various encoding modes that
 * a QR can encode its data with.
 */
public enum EncodingMode
{
    // The ordering of this enumeration IS impactful, as it determines the order
    // to search for a valid encoding mode. Only change the ordering if the
    // impacts are well understood.

    // Code: 0001
    NUMERIC(1, new int[] { 10, 12, 14 })
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean canEncode(final String content)
        {
            return content.chars()
                .filter(codepoint -> codepoint < 48 || codepoint > 57)
                .findAny()
                .isEmpty();
        }
    },

    // Code: 0010
    ALPHANUMERIC(2, new int[] { 9, 11, 13 })
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean canEncode(final String content)
        {
            return content.chars()
                .filter(codepoint -> !inCharset(codepoint))
                .findAny()
                .isEmpty();
        }

        private static boolean inCharset(final int codepoint)
        {
            return
                (codepoint >= 45 && codepoint <= 58) || // Numeric or one of -./:
                (codepoint >= 42 && codepoint <= 43) || // + or *
                (codepoint >= 36 && codepoint <= 37) || // $ or %
                (codepoint >= 65 && codepoint <= 90) || // Capital letters
                (codepoint == 32);                      // Space
        }
    },

    // Code: 0100
    BYTE(4, new int[] { 8, 16, 16 })
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean canEncode(final String content)
        {
            return StandardCharsets.ISO_8859_1.newEncoder().canEncode(content);
        }
    },

    // Code: 1000
    KANJI(8, new int[] { 8, 10, 12 })
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean canEncode(final String content)
        {
            // Always false, not currently implemented.
            return false;
        }
    };

    private final int code;
    private final int[] lengthValues;

    EncodingMode(final int code, final int[] lengthValues)
    {
        this.code = code;
        this.lengthValues = lengthValues;
    }

    /**
     * Determines if this mode can encode the given content string.
     * @param content The content desired to be encoded.
     * @return Whether or not this mode can encode the string.
     */
    public abstract boolean canEncode(String content);

    /**
     * Get the 4 bit code of this encoding mode.
     * @return The mode's code.
     */
    public int code()
    {
        return this.code;
    }

    /**
     * Get the number of length bits required for the given encoding mode. This
     * number varies based on the QR code's version.
     * @param qrVersion The QR code version.
     * @return The number of bits required to be used for the content length.
     */
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

    public static IEncodingModeFactory factory()
    {
        return new EncodingModeFactory();
    }
}
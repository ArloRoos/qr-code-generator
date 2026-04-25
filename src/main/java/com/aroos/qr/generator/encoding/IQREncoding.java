package com.aroos.qr.generator.encoding;

import java.util.BitSet;

/**
 * The {@link IQREncoding} interface defines behavior for a strategy that 
 * encodes input strings for use in a QR code.
 */
public interface IQREncoding
{
    /**
     * Encode the given content into a bit string.
     * @param content The content to encode.
     * @return A bit set containing the full data bits of the QR code.
     */
    BitSet encode(String content);
}
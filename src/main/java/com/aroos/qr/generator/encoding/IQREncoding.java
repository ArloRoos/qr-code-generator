package com.aroos.qr.generator.encoding;

import java.util.BitSet;

/**
 * The {@link IQREncoding} interface defines behavior for a strategy that 
 * encodes input strings for use in a QR code.
 */
public interface IQREncoding
{
    BitSet encode(String content, int qrVersion);
}
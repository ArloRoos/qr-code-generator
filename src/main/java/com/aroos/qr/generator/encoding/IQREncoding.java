package com.aroos.qr.generator.encoding;

import com.aroos.qr.generator.common.IBitStream;
import com.aroos.qr.generator.encoding.factory.IQREncodingFactory;
import com.aroos.qr.generator.encoding.factory.QREncodingFactory;

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
    IBitStream encode(String content);

    static IQREncodingFactory factory()
    {
        return new QREncodingFactory();
    }
}
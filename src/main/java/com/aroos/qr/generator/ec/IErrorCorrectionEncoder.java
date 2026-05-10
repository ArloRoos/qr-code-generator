package com.aroos.qr.generator.ec;

import com.aroos.qr.generator.common.IBitStream;
import com.aroos.qr.generator.common.QRConfiguration;

/**
 * The {@link IErrorCorrectionEncoder} interface defines a utility for 
 * generating and adding error correction codewords to the end of an existing
 * {@link IBitStream}.
 */
public interface IErrorCorrectionEncoder
{
    /**
     * Encode the error correction codewords into a given bit stream.
     * @param bits The bit stream to generate error correction codewords for.
     * @param config The QR code configuration information.
     * @return The updated bit stream, with EC codewords added to the end.
     */
    IBitStream encode(IBitStream bits, QRConfiguration config);
}
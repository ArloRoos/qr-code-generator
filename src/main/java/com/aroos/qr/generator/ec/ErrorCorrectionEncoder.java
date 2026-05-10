package com.aroos.qr.generator.ec;

import com.aroos.qr.generator.common.IBitStream;
import com.aroos.qr.generator.common.QRConfiguration;

/**
 * The {@link ErrorCorrectionEncoder} class implements a utility for 
 * generating and adding error correction codewords to the end of an existing
 * {@link IBitStream}.
 */
public final class ErrorCorrectionEncoder implements IErrorCorrectionEncoder
{
    /**
     * {@inheritDoc}
     */
    @Override
    public IBitStream encode(final IBitStream bits, final QRConfiguration config)
    {
        return null;
    }
}
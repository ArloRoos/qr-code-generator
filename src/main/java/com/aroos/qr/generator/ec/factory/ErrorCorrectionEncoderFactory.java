package com.aroos.qr.generator.ec.factory;

import com.aroos.qr.generator.common.services.Codewords;
import com.aroos.qr.generator.ec.ErrorCorrectionEncoder;
import com.aroos.qr.generator.ec.IErrorCorrectionEncoder;

/**
 * The {@link IErrorCorrectionEncoderFactory} class implements behavior for a
 * factory which can provide an {@link IErrorCorrectionEncoder} instance.
 */
public final class ErrorCorrectionEncoderFactory implements IErrorCorrectionEncoderFactory
{
    /**
     * {@inheritDoc}
     */
    @Override
    public IErrorCorrectionEncoder provide()
    {
        return new ErrorCorrectionEncoder(new Codewords());
    }
}
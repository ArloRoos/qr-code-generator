package com.aroos.qr.generator.ec.factory;

import com.aroos.qr.generator.ec.IErrorCorrectionEncoder;

/**
 * The {@link IErrorCorrectionEncoderFactory} interface defines behavior for a
 * factory which can provide an {@link IErrorCorrectionEncoder} instance.
 */
public interface IErrorCorrectionEncoderFactory
{
    IErrorCorrectionEncoder provide();
}
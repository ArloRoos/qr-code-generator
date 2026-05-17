package com.aroos.qr.generator;

import com.aroos.qr.generator.ec.ErrorCorrectionLevel;
import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link QRCodeGenerator} class implements the top level driver for 
 * generating a QR code. It implements a single method which takes in a string
 * to be encoded and outputs a fully constructed QR code with the string
 * encoded.
 */
public final class QRCodeGenerator implements IQRCodeGenerator
{
    public QRCodeGenerator()
    {

    }

    @Override
    public IQRCode generate(String content, ErrorCorrectionLevel level)
    {
        return null;
    }
}
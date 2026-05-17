package com.aroos.qr.generator;

import com.aroos.qr.generator.ec.ErrorCorrectionLevel;
import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link IQRCodeGenerator} interface defines the top level driver for 
 * generating a QR code. It defines a single method which takes in a string to
 * be encoded and outputs a fully constructed QR code with the string encoded.
 */
public interface IQRCodeGenerator
{
    /**
     * Generate a QR code encoding the given content string, with the given
     * error correction level.
     * @param content The content string to encode.
     * @param level The error correction level.
     * @return The generated QR code.
     */
    IQRCode generate(String content, ErrorCorrectionLevel level);
}
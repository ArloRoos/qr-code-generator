package com.aroos.qr.generator.common;

import com.aroos.qr.generator.ec.ErrorCorrectionLevel;
import com.aroos.qr.generator.encoding.EncodingMode;

/**
 * The {@link QRConfiguration} class defines a record which contains all 
 * contextual information needed to generate a QR code.
 */
public final record QRConfiguration(int version, ErrorCorrectionLevel level, EncodingMode mode)
{
    // No additional API.
}
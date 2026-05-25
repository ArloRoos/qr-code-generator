package com.aroos.qr.generator.modules.masking;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link IQRCodeMasking} interface defines behavior for a service which
 * can "mask" a QR code, switching certain modules from black to white to make
 * it easier for a QR code reader to scan.
 */
public interface IQRCodeMasking
{
    /**
     * Mask a QR code.
     * @param code The code to mask.
     * @return The masked QR code, with all reserved areas maintained.
     */
    IQRCode mask(IQRCode code);
}
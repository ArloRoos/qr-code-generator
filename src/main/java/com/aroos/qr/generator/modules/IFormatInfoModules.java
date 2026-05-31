package com.aroos.qr.generator.modules;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.modules.masking.MaskingPattern;

/**
 * The {@link IFormatInfoModules} interface defines behavior for a service which can
 * apply format and version information modules to a QR code.
 */
public interface IFormatInfoModules
{
    /**
     * Apply the format information bits.
     * @param maskType The masking pattern used on the QR code.
     * @param config The configuration information of the QR code.
     */
    void applyFormatInfo(MaskingPattern maskType, QRConfiguration config);

    /**
     * Apply the version information bits.
     * @param config The configuration information of the QR code.
     */
    void applyVersionInfo(QRConfiguration config);
}
package com.aroos.qr.generator.modules.meta;

import com.aroos.qr.generator.common.QRConfiguration;

/**
 * The {@link IVersionInfoModules} interface defines behavior for a service which 
 * can apply version information modules to a QR code.
 */
public interface IVersionInfoModules
{
    /**
     * Apply the version information bits.
     * @param config The configuration information of the QR code.
     */
    void applyVersionInfo(QRConfiguration config);
}

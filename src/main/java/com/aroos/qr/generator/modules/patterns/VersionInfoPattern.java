package com.aroos.qr.generator.modules.patterns;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link VersionInfoPattern} class implements behavior for adding the 
 * reserved version information areas to qr codes version 7 and above.
 */
public final class VersionInfoPattern implements IModulePattern
{
    private static final int QR_VERSION_MIN = 7;

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final IQRCode qrCode)
    {
        if (qrCode.getVersion()  >= QR_VERSION_MIN)
        {
            reserveVersionInfoModules(qrCode);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private Helpers
    ////////////////////////////////////////////////////////////////////////////

    private static void reserveVersionInfoModules(final IQRCode qrCode)
    {
        PatternUtil.fillReservedSquare(qrCode, 0, qrCode.getSize() - 11, 6, 3, false);
        PatternUtil.fillReservedSquare(qrCode, qrCode.getSize() - 11, 0, 3, 6, false);
    }
}
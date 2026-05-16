package com.aroos.qr.generator.modules.patterns;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link FormatInfoPattern} class implements behavior for temporarily 
 * reserving special format areas within a QR code.
 */
public final class FormatInfoPattern implements IModulePattern
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final IQRCode qrCode)
    {
        reserveTopLeftFormatInfo(qrCode);
        reserveTopRightFormatInfo(qrCode);
        reserveBottomLeftFormatInfo(qrCode);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private Helpers
    ////////////////////////////////////////////////////////////////////////////

    private static void reserveTopLeftFormatInfo(final IQRCode qrCode)
    {
        PatternUtil.fillReservedSquare(qrCode, 0, 8, 9, 1, false, false);
        PatternUtil.fillReservedSquare(qrCode, 8, 0, 1, 8, false, false);
    }

    private static void reserveTopRightFormatInfo(final IQRCode qrCode)
    {
        PatternUtil.fillReservedSquare(qrCode, qrCode.getSize() - 8, 8, 8, 1, false, false);
    }

    private static void reserveBottomLeftFormatInfo(final IQRCode qrCode)
    {
        PatternUtil.fillReservedSquare(qrCode, 8, qrCode.getSize() - 7, 1, 7, false, false);
    }
}
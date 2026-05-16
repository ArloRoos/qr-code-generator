package com.aroos.qr.generator.modules.patterns;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link PatternUtil} class implements behavior for a static utility class
 * providing useful methods for filling in QR code modules.
 */
public final class PatternUtil
{
    public static void fillReservedSquare(
        final IQRCode qrCode,
        final int x,
        final int y,
        final int width,
        final int height,
        final boolean value)
    {
        fillReservedSquare(qrCode, x, y, width, height, value, true);
    }

    public static void fillReservedSquare(
        final IQRCode qrCode,
        final int x,
        final int y,
        final int width,
        final int height,
        final boolean value,
        final boolean override)
    {
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                addReservedModuleSafe(qrCode, x + i, y + j, value, override);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private Helpers
    ////////////////////////////////////////////////////////////////////////////

    private static void addReservedModuleSafe(
        final IQRCode qrCode,
        final int x,
        final int y,
        final boolean value,
        final boolean override)
    {
        if (x < 0 ||
            x >= qrCode.getSize() ||
            y  < 0 ||
            y >= qrCode.getSize())
        {
            return;
        }

        if (!override && qrCode.isReserved(x, y))
        {
            return;
        }

        qrCode.setReservedModule(x, y, value);
    }
}
package com.aroos.qr.generator.modules.patterns;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link FinderPattern} class implements behavior for adding the concentric 
 * square patterns to three corners of a QR code.
 */
public final class FinderPattern implements IModulePattern
{
    private static final int L1_LENGTH = 9;
    private static final int L2_LENGTH = 7;
    private static final int L3_LENGTH = 5;
    private static final int L4_LENGTH = 3;

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final IQRCode qrCode)
    {
        // TOP LEFT FINDER
        addSingleFinder(qrCode, -1, -1);

        // TOP RIGHT FINDER
        addSingleFinder(qrCode, qrCode.getSize() - 8, -1);

        // BOTTOM LEFT FINDER
        addSingleFinder(qrCode, -1, qrCode.getSize() - 8);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private Helpers
    ////////////////////////////////////////////////////////////////////////////

    private static void addSingleFinder(
        final IQRCode qrCode,
        final int topLeftX,
        final int topLeftY)
    {
        PatternUtil.fillReservedSquare(qrCode, topLeftX, topLeftY, L1_LENGTH, L1_LENGTH, false);
        PatternUtil.fillReservedSquare(qrCode, topLeftX + 1, topLeftY + 1, L2_LENGTH, L2_LENGTH, true);
        PatternUtil.fillReservedSquare(qrCode, topLeftX + 2, topLeftY + 2, L3_LENGTH, L3_LENGTH, false);
        PatternUtil.fillReservedSquare(qrCode, topLeftX + 3, topLeftY + 3, L4_LENGTH, L4_LENGTH, true);
    }
}
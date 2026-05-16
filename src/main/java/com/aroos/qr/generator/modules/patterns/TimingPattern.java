package com.aroos.qr.generator.modules.patterns;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link TimingPattern} class implements behavior for adding the
 * alternating black and white line pattern to a QR code.
 */
public final class TimingPattern implements IModulePattern
{
    private static final int HORIZONTAL_PATTERN_Y = 6;
    private static final int VERTICAL_PATTERN_X = 6;

    private static final int HORTIZONTAL_PATTERN_START_X = 8;
    private static final int VERTICAL_PATTERN_START_Y = 8;

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final IQRCode qrCode)
    {
        addHorizontalTimingPattern(qrCode);
        addVerticalTimingPattern(qrCode);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private Helpers
    ////////////////////////////////////////////////////////////////////////////

    private static void addHorizontalTimingPattern(final IQRCode qrCode)
    {
        for (int i = 0; i <  timingPatternLength(qrCode); i++)
        {
            qrCode.setReservedModule(HORTIZONTAL_PATTERN_START_X  + i, HORIZONTAL_PATTERN_Y, i % 2 == 0);
        }
    }

    private static void addVerticalTimingPattern(final IQRCode qrCode)
    {
        for (int i = 0; i <  timingPatternLength(qrCode); i++)
        {
            qrCode.setReservedModule(VERTICAL_PATTERN_X, VERTICAL_PATTERN_START_Y + i, i % 2 == 0);
        }
    }

    private static int timingPatternLength(final IQRCode qrCode)
    {
        return qrCode.getSize() - 16;
    }
}
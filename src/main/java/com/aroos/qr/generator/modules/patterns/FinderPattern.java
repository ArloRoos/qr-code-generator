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
        addSquare(qrCode, topLeftX, topLeftY, L1_LENGTH, false);
        addSquare(qrCode, topLeftX + 1, topLeftY + 1, L2_LENGTH, true);
        addSquare(qrCode, topLeftX + 2, topLeftY + 2, L3_LENGTH, false);
        addSquare(qrCode, topLeftX + 3, topLeftY + 3, L4_LENGTH, true);
        addModule(qrCode, topLeftX + 4, topLeftY + 4, true);
    }

    private static void addSquare(
        final IQRCode qrCode,
        final int topLeftX,
        final int topLeftY,
        final int size,
        final boolean value)
    {
        addLine(qrCode, topLeftX, topLeftY, Direction.RIGHT, size, value);
        addLine(qrCode, topLeftX, topLeftY, Direction.DOWN, size, value);
        addLine(qrCode, topLeftX + size - 1, topLeftY + size - 1, Direction.LEFT, size, value);
        addLine(qrCode, topLeftX + size - 1, topLeftY + size - 1, Direction.UP, size, value);
    }

    private static void addLine(
        final IQRCode qrCode,
        final int x,
        final int y,
        final Direction dir,
        final int size,
        final boolean value)
    {
        for (int i = 0; i < size; i++)
        {
            addModule(qrCode, x + (dir.getX() * i), y + (dir.getY() * i), value);
        }
    }

    private static void addModule(
        final IQRCode qrCode,
        final int x,
        final int y,
        final boolean value)
    {
        if (x < 0 ||
            x >= qrCode.getSize() ||
            y  < 0 ||
            y >= qrCode.getSize())
        {
            return;
        }

        qrCode.setReservedModule(x, y, value);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private Enumerations
    ////////////////////////////////////////////////////////////////////////////

    private enum Direction
    {
        LEFT(-1, 0),
        RIGHT(1, 0),
        UP(0, -1),
        DOWN(0, 1);

        private final int xOffset;
        private final int yOffset;

        private Direction(final int xOffset, final int yOffset)
        {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }

        public int getX()
        {
            return this.xOffset;
        }

        public int getY()
        {
            return this.yOffset;
        }
    }
}
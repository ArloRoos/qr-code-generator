package com.aroos.qr.generator.modules;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.aroos.qr.generator.common.IBitStream;

/**
 * The {@link DataBitModules} class implements behavior for placing data 
 * codewords into a QR code.
 */
public final class DataBitModules implements IDataBitModules
{
    private static final int TIMING_PATTERN_COLUMN = 6;

    private final IQRCode qrCode;
    private final AtomicBoolean goingUp;
    private final AtomicBoolean onLeft;
    private final AtomicInteger currentX;
    private final AtomicInteger currentY;

    public DataBitModules(final IQRCode qrCode)
    {
        this.qrCode = qrCode;
        this.goingUp = new AtomicBoolean(true);
        this.onLeft = new AtomicBoolean(false);
        this.currentX = new AtomicInteger(qrCode.getSize() - 1);
        this.currentY = new AtomicInteger(qrCode.getSize() - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final IBitStream data)
    {
        data.getBitList().forEach(this::processNext);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private Helpers
    ////////////////////////////////////////////////////////////////////////////

    private void processNext(final boolean value)
    {
        while (this.qrCode.isReserved(this.currentX.get(), this.currentY.get()))
        {
            this.adjustPosition();
        }

        this.qrCode.setModule(this.currentX.get(), this.currentY.get(), value);

        this.adjustPosition();
    }

    private void adjustPosition()
    {
        final int dx = this.onLeft.get() ? 1 : -1;
        final int dy = this.onLeft.get()
            ? this.goingUp.get() ? -1 : 1
            : 0;

        this.currentX.addAndGet(dx);
        this.currentY.addAndGet(dy);

        // Alternate between left and right.
        this.onLeft.set(!this.onLeft.get());

        // After adjusting coordinates, make sure we're not out of bounds.
        this.processDirectionChange();
    }

    private void processDirectionChange()
    {
        if (this.outOfBounds())
        {
            // Reverse the current direction and adjust the current coordinates
            // accordingly.
            this.goingUp.set(!this.goingUp.get());
            this.currentX.addAndGet(this.onLeft.get() ? -1 : -2);
            this.currentY.set(this.goingUp.get() ? this.qrCode.getSize() - 1 : 0);

            // If the current column is the same as the timing pattern, skip it.
            if (this.currentX.get() == TIMING_PATTERN_COLUMN)
            {
                this.currentX.incrementAndGet();
            }

            // If we're still out of bounds after a column change, it means
            // we've traversed into negative x coordinates. Throw an error.
            if (this.outOfBounds())
            {
                throw new IllegalArgumentException("Provided bit stream is too large to fit in the given QR code.");
            }
        }
    }

    private boolean outOfBounds()
    {
        return this.currentX.get() < 0 ||
            this.currentX.get() >= this.qrCode.getSize() ||
            this.currentY.get()  < 0 ||
            this.currentY.get() >= this.qrCode.getSize();
    }
}
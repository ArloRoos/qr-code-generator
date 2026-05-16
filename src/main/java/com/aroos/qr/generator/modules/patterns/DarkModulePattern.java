package com.aroos.qr.generator.modules.patterns;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link DarkModulePattern} class implements behavior for adding a single 
 * dark module in the bottom left of a QR code
 */
public final class DarkModulePattern implements IModulePattern
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final IQRCode qrCode)
    {
        qrCode.setReservedModule(8, (4 * qrCode.getVersion()) + 9, true);
    }
}
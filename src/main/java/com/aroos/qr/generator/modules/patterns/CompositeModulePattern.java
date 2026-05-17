package com.aroos.qr.generator.modules.patterns;

import java.util.List;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link CompositeModulePattern} class implements a composite pattern which
 * applies all preset module patterns to a QR code in the correct order.
 */
public final class CompositeModulePattern implements IModulePattern
{
    private static List<IModulePattern> DELEGATES = List.of(
        new FinderPattern(),
        new AlignmentPattern(),
        new TimingPattern(),
        new DarkModulePattern(),
        new FormatInfoPattern(),
        new VersionInfoPattern());

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final IQRCode qrCode)
    {
        DELEGATES.forEach(pattern -> pattern.accept(qrCode));
    }
}
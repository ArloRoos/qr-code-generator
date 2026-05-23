package com.aroos.qr.generator.modules.factory;

import java.util.List;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.modules.IQRCode;
import com.aroos.qr.generator.modules.QRCode;
import com.aroos.qr.generator.modules.patterns.AlignmentPattern;
import com.aroos.qr.generator.modules.patterns.DarkModulePattern;
import com.aroos.qr.generator.modules.patterns.FinderPattern;
import com.aroos.qr.generator.modules.patterns.FormatInfoPattern;
import com.aroos.qr.generator.modules.patterns.IModulePattern;
import com.aroos.qr.generator.modules.patterns.TimingPattern;
import com.aroos.qr.generator.modules.patterns.VersionInfoPattern;

/**
 * The {@link QRCodeFactory} class implements behavior for a factory which 
 * provides a pre-configured QR code instance, with all reserved modules filled 
 * in.
 */
public final class QRCodeFactory implements IQRCodeFactory
{
    private static final List<IModulePattern> PATTERNS = List.of(
        new FinderPattern(),
        new AlignmentPattern(),
        new TimingPattern(),
        new DarkModulePattern(),
        new FormatInfoPattern(),
        new VersionInfoPattern());

    /**
     * {@inheritDoc}
     */
    public IQRCode provide(final QRConfiguration config)
    {
        final IQRCode code = new QRCode(config);

        PATTERNS.forEach(pattern -> pattern.accept(code));

        return code;
    }
}
package com.aroos.qr.generator.modules;

import org.testng.annotations.Test;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;
import com.aroos.qr.generator.encoding.EncodingMode;
import com.aroos.qr.generator.modules.patterns.FinderPattern;
import com.aroos.qr.generator.modules.patterns.IModulePattern;
import com.aroos.qr.generator.png.IPNGImage;
import com.aroos.qr.generator.png.PNGWriter;

public final class QRCodeTest
{
    @Test
    public void toPNGTest()
    {
        final QRConfiguration config = new QRConfiguration(5, ErrorCorrectionLevel.LEVEL_H, EncodingMode.BYTE);
        final IQRCode code = new QRCode(config);
        final IModulePattern pattern = new FinderPattern();

        pattern.accept(code);

        final IPNGImage image = code.toPNG(5);

        PNGWriter.writeImage(image, "test.png");
    }    
}
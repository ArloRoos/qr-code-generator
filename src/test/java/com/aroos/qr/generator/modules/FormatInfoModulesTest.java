package com.aroos.qr.generator.modules;

import org.testng.annotations.Test;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;
import com.aroos.qr.generator.modules.masking.MaskingPattern;
import com.aroos.qr.generator.png.IPNGImage;
import com.aroos.qr.generator.png.PNGWriter;

public final class FormatInfoModulesTest
{
    @Test
    public void formatInfoModulesTest()
    {
        final QRConfiguration config = new QRConfiguration(1, ErrorCorrectionLevel.LEVEL_M, null);
        final IQRCode code = IQRCode.factory().provide(config);
        final IFormatInfoModules modules = new FormatInfoModules(code);

        modules.applyFormatInfo(MaskingPattern.MASK_0, config);

        final IPNGImage img = code.toPNG(10);
        PNGWriter.writeImage(img, "test.png");
    }
}
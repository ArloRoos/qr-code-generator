package com.aroos.qr.generator.modules;

import org.testng.annotations.Test;

import com.aroos.qr.generator.IQRCodeGenerator;
import com.aroos.qr.generator.QRCodeGenerator;
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;
import com.aroos.qr.generator.png.IPNGImage;
import com.aroos.qr.generator.png.PNGWriter;

public final class QRCodeTest
{
    @Test
    public void toPNGTest()
    {
        final String testContent = "https://www.thonky.com/qr-code-tutorial/module-placement-matrix";
        final ErrorCorrectionLevel level = ErrorCorrectionLevel.LEVEL_H;
        final IQRCodeGenerator generator = new QRCodeGenerator();
        final IQRCode code = generator.generate(testContent, level, 15);
        final IPNGImage image = code.toPNG(5);

        PNGWriter.writeImage(image, "test.png");
    }
}
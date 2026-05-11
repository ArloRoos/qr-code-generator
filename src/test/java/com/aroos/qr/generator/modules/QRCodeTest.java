package com.aroos.qr.generator.modules;

import org.testng.annotations.Test;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;
import com.aroos.qr.generator.encoding.EncodingMode;
import com.aroos.qr.generator.png.IPNGImage;
import com.aroos.qr.generator.png.PNGWriter;

public final class QRCodeTest
{
    @Test
    public void toPNGTest()
    {
        final QRConfiguration config = new QRConfiguration(5, ErrorCorrectionLevel.LEVEL_H, EncodingMode.BYTE);
        final IQRCode code = new QRCode(config);

        code.setModule(10, 10, true);
        code.setModule(11, 10, true);
        code.setModule(12, 10, true);
        code.setModule(13, 10, true);
        code.setModule(14, 10, true);

        
        code.setModule(10, 12, true);
        code.setModule(11, 12, true);
        code.setModule(12, 12, true);
        code.setModule(13, 12, true);
        code.setModule(14, 12, true);

        final IPNGImage image = code.toPNG(5);

        PNGWriter.writeImage(image, "test.png");
    }    
}
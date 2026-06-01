package com.aroos.qr.generator.modules;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.aroos.qr.generator.IQRCodeGenerator;
import com.aroos.qr.generator.QRCodeGenerator;
import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;
import com.aroos.qr.generator.png.IPNGImage;
import com.aroos.qr.generator.png.PNGWriter;

public final class QRCodeTest
{
    @Test
    public void toPNGTest()
    {
        final String testContent = "HELLO WORLD";
        final ErrorCorrectionLevel level = ErrorCorrectionLevel.LEVEL_Q;
        final IQRCodeGenerator generator = new QRCodeGenerator();
        final IQRCode code = generator.generate(testContent, level);
        final IPNGImage image = code.toPNG(5);

        PNGWriter.writeImage(image, "test.png");
    }

    @Test
    public void copyTest()
    {
        final QRConfiguration config = new QRConfiguration(1, null, null);
        final IQRCode code = new QRCode(config);

        code.setModule(1, 2, false);
        code.setModule(5, 5, true);
        code.setModule(6, 3, false);

        code.setReservedModule(2, 1, false);
        code.setReservedModule(5, 6, true);
        code.setReservedModule(3, 6, true);

        final IQRCode copy = code.copy();

        for (int i = 0; i < code.getSize(); i++)
        {
            for (int j = 0; j < code.getSize(); j++)
            {
                assertThat(code.isReserved(i, j))
                    .isEqualTo(copy.isReserved(i, j));

                assertThat(code.getModule(i, j))
                    .isEqualTo(copy.getModule(i, j));
            }
        }

        code.setModule(0, 0, true);

        assertThat(code.getModule(0, 0))
            .isNotEqualTo(copy.getModule(0, 0));
    }
}
package com.aroos.qr.generator.modules;

import org.testng.annotations.Test;

import com.aroos.qr.generator.common.BitStream;
import com.aroos.qr.generator.common.IBitStream;
import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;
import com.aroos.qr.generator.encoding.EncodingMode;
import com.aroos.qr.generator.modules.patterns.AlignmentPattern;
import com.aroos.qr.generator.modules.patterns.DarkModulePattern;
import com.aroos.qr.generator.modules.patterns.FinderPattern;
import com.aroos.qr.generator.modules.patterns.FormatInfoPattern;
import com.aroos.qr.generator.modules.patterns.IModulePattern;
import com.aroos.qr.generator.modules.patterns.TimingPattern;
import com.aroos.qr.generator.modules.patterns.VersionInfoPattern;
import com.aroos.qr.generator.png.IPNGImage;
import com.aroos.qr.generator.png.PNGWriter;

public final class QRCodeTest
{
    @Test
    public void toPNGTest()
    {
        final QRConfiguration config = new QRConfiguration(4, ErrorCorrectionLevel.LEVEL_H, EncodingMode.BYTE);
        final IQRCode code = new QRCode(config);
        final IModulePattern finder = new FinderPattern();
        final IModulePattern alignment = new AlignmentPattern();
        final IModulePattern timing = new TimingPattern();
        final IModulePattern dark = new DarkModulePattern();
        final IModulePattern format = new FormatInfoPattern();
        final IModulePattern version = new VersionInfoPattern();

        finder.accept(code);
        alignment.accept(code);
        timing.accept(code);
        dark.accept(code);
        format.accept(code);
        version.accept(code);

        final IDataBitModules bitModules = new DataBitModules(code);
        final IBitStream data = new BitStream();

        for (int i = 0; i < 50; i++)
        {
            data.putInt(12345, 16);
        }

        bitModules.accept(data);

        final IPNGImage image = code.toPNG(5);

        PNGWriter.writeImage(image, "test.png");
    }    
}
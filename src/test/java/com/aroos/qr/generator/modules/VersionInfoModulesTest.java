package com.aroos.qr.generator.modules;

import org.testng.annotations.Test;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.modules.meta.IVersionInfoModules;
import com.aroos.qr.generator.modules.meta.VersionInfoModules;
import com.aroos.qr.generator.png.IPNGImage;
import com.aroos.qr.generator.png.PNGWriter;

public final class VersionInfoModulesTest
{
    @Test
    public void versionInfoModulesTest()
    {
        final QRConfiguration config = new QRConfiguration(8, null, null);
        final IQRCode code = IQRCode.factory().provide(config);
        final IVersionInfoModules modules = new VersionInfoModules(code);

        modules.applyVersionInfo(config);

        final IPNGImage img = code.toPNG(10);
        PNGWriter.writeImage(img, "test.png");
    }
}
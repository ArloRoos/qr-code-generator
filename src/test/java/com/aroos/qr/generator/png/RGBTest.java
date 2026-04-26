package com.aroos.qr.generator.png;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aroos.qr.generator.png.pixels.IColor;
import com.aroos.qr.generator.png.pixels.RGB;

public final class RGBTest
{
    @DataProvider
    public Object[][] getTestCases()
    {
        return Stream.<Object[]>of(
            new Object[] { RGB.from(255, 0, 0), 0xFF0000FF },
            new Object[] { RGB.from(0, 255, 0), 0x00FF00FF },
            new Object[] { RGB.from(0, 0, 255), 0x0000FFFF },
            new Object[] { RGB.from(0, 0, 0), 0x000000FF },
            new Object[] { RGB.from(255, 255, 255), 0xFFFFFFFF })
            .toArray(Object[][]::new);

    }

    @Test(dataProvider = "getTestCases")
    public void toIntTest(final IColor pixel, final int expected)
    {
        assertThat(pixel.pack())
            .isEqualTo(expected);
    }
}
package com.aroos.qr.generator.png;

import org.testng.annotations.Test;

import com.aroos.qr.generator.png.pixels.RGB;

public final class PNGWriterTest
{
    @Test
    public void writeTest()
    {
        final IPNGImage image = new PNGImage(25, 25, RGB.from(255, 255, 255));

        image.setPixel(10, 10, RGB.from(0, 0, 255, 50));
        image.setPixel(10, 11, RGB.from(0, 0, 255, 50));
        image.setPixel(10, 12, RGB.from(0, 0, 255, 50));
        image.setPixel(10, 13, RGB.from(0, 0, 255, 50));
        image.setPixel(10, 14, RGB.from(0, 0, 255, 50));

        PNGWriter.writeImage(image, "test.png");
    }
}
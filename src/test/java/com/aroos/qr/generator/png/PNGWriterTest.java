package com.aroos.qr.generator.png;

import org.testng.annotations.Test;

public final class PNGWriterTest
{
    @Test
    public void writeTest()
    {
        final IPNGImage image = new PNGImage(25, 25, RGB.from(255, 255, 255));

        image.setPixel(10, 10, RGB.from(0, 0, 0));
        image.setPixel(10, 11, RGB.from(0, 0, 0));
        image.setPixel(10, 12, RGB.from(0, 0, 0));
        image.setPixel(10, 13, RGB.from(0, 0, 0));
        image.setPixel(10, 14, RGB.from(0, 0, 0));

        PNGWriter.writeImage(image, "test.png");
    }
}
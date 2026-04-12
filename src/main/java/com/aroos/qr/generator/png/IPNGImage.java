package com.aroos.qr.generator.png;

import com.aroos.qr.generator.png.pixels.IColor;

/**
 * The {@link IPNGImage} interface defines a structure for creating, modifying,
 * and writing out a PNG-formatted image.
 */
public interface IPNGImage extends IWritable
{
    /**
     * Sets the pixel at the given coordinates to the RGB value.
     * @param x The row of the pixel.
     * @param y The column of the pixel.
     * @param pixel The pixel RGBA value.
     */
    void setPixel(int x, int y, IColor pixel);
}
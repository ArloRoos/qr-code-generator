package com.aroos.qr.generator.png;

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
     * @param pixel The pixel RGB(A) value.
     */
    void setPixel(int x, int y, RGB pixel);
}
package com.aroos.qr.generator.png.pixels;

/**
 * The {@link IColor} interface defines behavior for a serializable 4 byte
 * pixel.
 */
public interface IColor
{
    /**
     * Packs the color values of this pixel into an integer.
     * @return The serialized pixel.
     */
    int pack();

    public static byte[] unpack(final int color)
    {
        final int COLOR_MASK = 0x000000FF;

        return new byte[]
        {
            (byte)(color >> 24 & COLOR_MASK),
            (byte)(color >> 16 & COLOR_MASK),
            (byte)(color >> 8 & COLOR_MASK),
            (byte)(color & COLOR_MASK)
        };
    }
}
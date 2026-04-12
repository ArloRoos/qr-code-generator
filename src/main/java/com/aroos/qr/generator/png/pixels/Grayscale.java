package com.aroos.qr.generator.png.pixels;

/**
 * The {@link Grayscale} record implements a pixel with a grayscale and alpha
 * value.
 */
public final record Grayscale(int val, int alpha) implements IColor
{
    public static IColor from(final int val)
    {
        return new Grayscale(val, 255);
    }

    public static IColor from(final int val, final int alpha)
    {
        return new Grayscale(val, alpha);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int pack()
    {
        return (val << 24) | (val << 16) | (val << 8) | alpha;
    }
}
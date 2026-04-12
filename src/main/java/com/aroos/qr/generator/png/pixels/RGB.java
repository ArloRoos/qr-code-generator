package com.aroos.qr.generator.png.pixels;

/**
 * The {@link RGB} record implements a pixel with an RGB and alpha channel.
 */
public final record RGB(int r, int g, int b, int alpha) implements IColor
{
    public static IColor from(final int r, final int g, final int b)
    {
        return new RGB(r, g, b, 255);
    }

    public static IColor from(final int r, final int g, final int b, final int alpha)
    {
        return new RGB(r, g, b, alpha);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int pack()
    {
        return (r << 24) | (g << 16) | (b << 8) | alpha;
    }
}
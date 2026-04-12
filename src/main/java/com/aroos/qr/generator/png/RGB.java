package com.aroos.qr.generator.png;

/**
 * The {@link RGB} record implements a pixel with an RGB and alpha channel.
 */
public final record RGB(int r, int g, int b, int alpha)
{
    public static RGB from(final int r, final int g, final int b)
    {
        return new RGB(r, g, b, 0);
    }

    public static RGB from(final int r, final int g, final int b, final int alpha)
    {
        return new RGB(r, g, b, alpha);
    }

    public int toInt()
    {
        return (r << 16) | (g << 8) | b;
    }
}
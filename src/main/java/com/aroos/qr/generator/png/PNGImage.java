package com.aroos.qr.generator.png;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

import com.aroos.qr.generator.png.chunks.DataChunk;
import com.aroos.qr.generator.png.chunks.EndChunk;
import com.aroos.qr.generator.png.chunks.HeaderChunk;
import com.aroos.qr.generator.png.chunks.PNGChunk;

/**
 * The {@link PNGImage} class implements a structure for creating, modifying,
 * and writing out a PNG-formatted image.
 */
public final class PNGImage implements IPNGImage
{
    private static final byte[] PNG_SIGNATURE = new byte[] {
        (byte)0x89,
        (byte)0x50,
        (byte)0x4E,
        (byte)0x47,
        (byte)0x0D,
        (byte)0x0A,
        (byte)0x1A,
        (byte)0x0A
    };

    private final int[][] pixels;
    private final int width;
    private final int height;

    /**
     * Initializes a new PNGImage with the given width and height, with a black
     * background by default.
     * @param width The width of the image.
     * @param height The height of the image.
     */
    public PNGImage(final int width, final int height)
    {
        this.pixels = new int[height][width];
        this.width = width;
        this.height = height;

        initializePixels(pixels, RGB.from(0, 0, 0));
    }

    /**
     * Initializes a new PNGImage with the given width and height, with a
     * default color provided.
     * @param width The width of the image.
     * @param height The height of the image.
     * @param background The default background color.
     */
    public PNGImage(final int width, final int height, final RGB background)
    {
        this.pixels = new int[width][height];
        this.width = width;
        this.height = height;

        initializePixels(pixels, background);
    }

    /**
     * {@inheritDoc}
     */
    public void setPixel(final int x, final int y, final RGB pixel)
    {
        if (x >= this.width || x < 0)
        {
            throw new IllegalArgumentException(String.format("x value %d is out of range [0-%d].", x, this.width));
        }

        if (y >= this.height || y < 0)
        {
            throw new IllegalArgumentException(String.format("y value %d is out of range [0-%d].", y, this.height));
        }

        this.pixels[y][x] = pixel.toInt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final WritableByteChannel channel)
        throws IOException
    {
        final PNGChunk header = new HeaderChunk(this.width, this.height);
        final PNGChunk data = new DataChunk(this.pixels);
        final PNGChunk end = new EndChunk();
        final ByteBuffer signature = ByteBuffer.wrap(PNG_SIGNATURE);

        while (signature.hasRemaining())
        {
            channel.write(signature);
        }

        header.write(channel);
        data.write(channel);
        end.write(channel);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private Helpers
    ////////////////////////////////////////////////////////////////////////////'

    private static void initializePixels(final int[][] pixels, final RGB initialColor)
    {
        final int colorAsInt = initialColor.toInt();

        for (int i = 0; i < pixels.length; i++)
        {
            for (int j = 0; j < pixels[i].length; j++)
            {
                pixels[i][j] = colorAsInt;
            }
        }
    }
}
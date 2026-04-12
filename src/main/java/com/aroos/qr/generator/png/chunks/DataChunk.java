package com.aroos.qr.generator.png.chunks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.util.zip.Deflater;

import com.aroos.qr.generator.png.pixels.IColor;

/**
 * The {@link DataChunk} class implements a {@link PNGChunk} representing the
 * actual image data of the PNG.
 */
public final class DataChunk extends PNGChunk
{
    private static final byte FILTER_TYPE = 0x00;
    private static final int DEFLATER_BUFFER_SIZE = 1024;

    private final int[][] pixels;

    public DataChunk(final int[][] pixels)
    {
        super("IDAT");

        this.pixels = pixels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    byte[] getData()
    {
        final byte[] flattened = flatten(this.pixels);
        final Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        deflater.setInput(flattened);
        deflater.finish();

        try (final ByteArrayOutputStream out = new ByteArrayOutputStream())
        {
            final byte[] buffer = new byte[DEFLATER_BUFFER_SIZE];

            while (!deflater.finished())
            {
                final int compressedSize = deflater.deflate(buffer);
                out.write(buffer, 0, compressedSize);
            }

            final byte[] result = out.toByteArray();

            return result;
        }
        catch (final IOException error)
        {
            throw new UncheckedIOException(error);
        }
    }

    private static byte[] flatten(final int[][] arr)
    {
        // Buffer size calculated by:
        // (Width * Height * Channels) + Height
        // The additional height addition is for the filter byte at the start of
        // each scanline.
        final int height = arr.length;
        final int width = arr[0].length;
        final ByteBuffer buf = ByteBuffer.allocate((width * height * 4) + height);

        for (int i = 0; i < height; i++)
        {
            // Filter byte.
            buf.put(FILTER_TYPE);

            for (int j = 0; j < width; j++)
            {
                final int v = arr[i][j];

                buf.put(IColor.unpack(v));
            }
        }

        return buf.array();
    }
}
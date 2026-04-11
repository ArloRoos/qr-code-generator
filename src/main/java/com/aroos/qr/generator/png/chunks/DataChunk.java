package com.aroos.qr.generator.png.chunks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.zip.DeflaterOutputStream;

/**
 * The {@link DataChunk} class implements a {@link PNGChunk} representing the
 * actual image data of the PNG.
 */
public final class DataChunk extends PNGChunk
{
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
        try (
            final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            final DeflaterOutputStream deflate = new DeflaterOutputStream(bytes))
        {
            for (int i = 0; i < pixels.length; i++)
            {
                for (int j = 0; j < pixels[i].length; j++)
                {
                    deflate.write(pixels[i][j]);
                }
            }

            return bytes.toByteArray();
        }
        catch (final IOException error)
        {
            throw new UncheckedIOException(error);
        }
    }
}
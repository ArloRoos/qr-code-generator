package com.aroos.qr.generator.png.chunks;

/**
 * The {@link DataChunk} class implements a {@link PNGChunk} representing the
 * actual image data of the PNG.
 */
public final class DataChunk extends PNGChunk
{
    private final byte[][] pixels;

    public DataChunk(final byte[][] pixels)
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
        return new byte[10];
    }
}
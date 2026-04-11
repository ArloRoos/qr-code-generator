package com.aroos.qr.generator.png.chunks;

/**
 * The {@link EndChunk} class implements a {@link PNGChunk} representing the
 * terminator of a PNG image.
 */
public final class EndChunk extends PNGChunk
{
    public EndChunk()
    {
        super("IEND");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    byte[] getData()
    {
        return new byte[0];
    }
}
package com.aroos.qr.generator.png.chunks;

import java.nio.ByteBuffer;

/**
 * The {@link HeaderChunk} class implements a {@link PNGChunk} containing
 * information about the header of a PNG file.
 */
public final class HeaderChunk extends PNGChunk
{
    // 8 bits per pixel (per channel)
    private static final byte BIT_DEPTH = 0x08;

    // Color type 2 (RGB/truecolor)
    private static final byte COLOR_TYPE = 0x02;

    // Deflate compression (0 is the only valid value)
    private static final byte COMPRESSION_METHOD = 0x00;

    // Paeth filter, not really used (0 is the only valid value)
    private static final byte FILTER_METHOD = 0x00;

    // Whether the image is interlaced (0 is no)
    private static final byte INTERLACED = 0x00;

    private final int width;
    private final int height;

    public HeaderChunk(final int width, final int height)
    {
        super("IHDR");

        this.width = width;
        this.height = height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    byte[] getData()
    {
        final byte[] bytes = new byte[13];
        final ByteBuffer buffer = ByteBuffer.wrap(bytes);

        buffer.putInt(width);
        buffer.putInt(height);
        buffer.put(BIT_DEPTH);
        buffer.put(COLOR_TYPE);
        buffer.put(COMPRESSION_METHOD);
        buffer.put(FILTER_METHOD);
        buffer.put(INTERLACED);

        return buffer.array();
    }
}
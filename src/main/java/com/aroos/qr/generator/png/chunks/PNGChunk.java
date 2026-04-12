package com.aroos.qr.generator.png.chunks;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import com.aroos.qr.generator.png.IWritable;

/**
 * The {@link PNGChunk} class implements the base class for a writable chunk of
 * a PNG file.
 */
public abstract class PNGChunk implements IWritable
{
    private static final int RESERVED_BUFFER_SIZE = 12;

    private final int type;

    protected PNGChunk(final String type)
    {
        this.type = convertChunkType(type);
    }

    abstract byte[] getData();

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final WritableByteChannel channel)
        throws IOException
    {
        final byte[] data = this.getData();
        final int length = data.length;
        final long checksum = getChecksum(data, this.type);
        final ByteBuffer buffer = ByteBuffer.allocate(RESERVED_BUFFER_SIZE + length);

        buffer.putInt(length);
        buffer.putInt(type);
        buffer.put(data);
        buffer.putInt((int)checksum);
        buffer.flip();

        while (buffer.hasRemaining())
        {
            channel.write(buffer);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private Helpers
    ////////////////////////////////////////////////////////////////////////////

    private static int convertChunkType(final String name)
    {
        if (name.length() != 4)
        {
            throw new IllegalArgumentException("Chunk type cannot be more than 4 characters.");
        }

        return (name.codePointAt(0) << 24) |
            (name.codePointAt(1) << 16) |
            (name.codePointAt(2) << 8) |
            name.codePointAt(3);
    }

    private static long getChecksum(final byte[] data, final int type)
    {
        final ByteBuffer buffer = ByteBuffer.allocate(data.length + Integer.BYTES);

        buffer.putInt(type);
        buffer.put(data);

        final Checksum checksum = new CRC32();

        checksum.update(buffer.array());

        return checksum.getValue();
    }
}
package com.aroos.qr.generator.png;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;

/**
 * The {@link IWritable} interface defines behavior for an object that can write
 * (serialize) itself to a byte channel.
 */
public interface IWritable
{
    /**
     * Serialize this object to the byte channel.
     * @param channel The channel to write to.
     * @throws IOException If there's a problem writing to the channel.
     */
    void write(WritableByteChannel channel)
        throws IOException;
}
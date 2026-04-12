package com.aroos.qr.generator.png;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.channels.FileChannel;

/**
 * The {@link PNGWriter} class implements a static utility class for writing png
 * images to a file.
 */
public final class PNGWriter
{
    public static void writeImage(final IPNGImage image, final String file)
    {
        try (
            final FileOutputStream out = new FileOutputStream(file);
            final FileChannel channel = out.getChannel())
        {
            image.write(channel);
        }
        catch (final IOException error)
        {
            throw new UncheckedIOException("Error occured while writing image to %s.".formatted(file), error);
        }
    }
}
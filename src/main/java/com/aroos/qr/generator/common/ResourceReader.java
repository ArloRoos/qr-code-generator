package com.aroos.qr.generator.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

/**
 * The {@link ResourceReader} class implements a static utility for reading
 * classpath resources.
 */
public final class ResourceReader
{
    private static final String BASE_PATH = "com/aroos/qr/generator/";

    public static String read(final String name)
    {
        final String resolvedPath = BASE_PATH + name;

        try (final InputStream in = ResourceReader.class.getClassLoader().getResourceAsStream(resolvedPath))
        {
            final byte[] bytes = in.readAllBytes();

            return new String(bytes, StandardCharsets.UTF_8);
        }
        catch (final IOException error)
        {
            throw new UncheckedIOException(error);
        }
    }
}
package com.aroos.qr.generator.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@link LookupTables} class implements a static utility for populating
 * lookup tables from a resource file.
 */
public final class LookupTables
{
    public static <TKey, TValue> void fillTable(
        final String resource,
        final Pattern lineRegex,
        final Function<Matcher, TKey> keyMapper,
        final Function<Matcher, TValue> valueMapper,
        final BiConsumer<TKey, TValue> populator)
    {
        fillTable(
            resource,
            line -> Optional.of(lineRegex.matcher(line))
                .filter(Matcher::matches)
                .map(keyMapper)
                .orElseThrow(noMatch(line, resource)),
            line -> Optional.of(lineRegex.matcher(line))
                .filter(Matcher::matches)
                .map(valueMapper)
                .orElseThrow(noMatch(line, resource)),
            populator);
    }

    public static <TKey, TValue> void fillTable(
        final String resource,
        final Function<String, TKey> keyMapper,
        final Function<String, TValue> valueMapper,
        final BiConsumer<TKey, TValue> populator)
    {
        final String content = ResourceReader.read(resource);

        try (
            final StringReader reader = new StringReader(content);
            final BufferedReader buffered = new BufferedReader(reader))
        {
            buffered.lines()
                .forEach(line ->
                {
                    final TKey key = keyMapper.apply(line);
                    final TValue value = valueMapper.apply(line);

                    populator.accept(key, value);
                });
        }
        catch (final IOException error)
        {
            throw new UncheckedIOException(error);
        }
    }

    private static Supplier<RuntimeException> noMatch(final String line, final String file)
    {
        return () -> new RuntimeException(
            "Could not find a matcher for line [%s] while generating lookup table from [%s]".formatted(line, file));
    }
}
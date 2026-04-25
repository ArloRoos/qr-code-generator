package com.aroos.qr.generator.encoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aroos.qr.generator.common.ResourceReader;
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;

/**
 * The {@link Capacities} class implements a static utility that can retrieve
 * capacity information given qr code metadata such as version, encoding mode,
 * and error correction level.
 */
public final class Capacities
{
    // region Static Initializers

    private static final Map<CapacityKey, Integer> CAPACITY_MAP = new HashMap<>();
    private static final Map<CodewordsKey, Integer> CODEWORDS_MAP = new HashMap<>();

    private static final Pattern CAPACITY_REGEX = Pattern.compile("^(?<VERSION>\\d+):(?<LEVEL>[A-Z_]+):(?<MODE>[A-Z]+):(?<VALUE>\\d+)$");
    private static final Pattern CODEWORDS_REGEX = Pattern.compile("^(?<VERSION>\\d+):(?<LEVEL>[A-Z_]+):(?<VALUE>\\d+)$");

    private static final String VERSION_GROUP = "VERSION";
    private static final String LEVEL_GROUP = "LEVEL";
    private static final String MODE_GROUP = "MODE";
    private static final String VALUE_GROUP = "VALUE";

    static
    {
        final String mappings = ResourceReader.read("capacities.txt");

        try (
            final StringReader reader = new StringReader(mappings);
            final BufferedReader buffered = new BufferedReader(reader))
        {
            buffered.lines()
                .map(CAPACITY_REGEX::matcher)
                .filter(Matcher::find)
                .forEach(m ->
                {
                    final CapacityKey key = new CapacityKey(
                        Integer.parseInt(m.group(VERSION_GROUP)),
                        ErrorCorrectionLevel.valueOf(m.group(LEVEL_GROUP)),
                        EncodingMode.valueOf(m.group(MODE_GROUP)));

                    CAPACITY_MAP.put(key, Integer.parseInt(m.group(VALUE_GROUP)));
                });
        }
        catch (final IOException error)
        {
            throw new UncheckedIOException(error);
        }
    }

    static
    {
        final String mappings = ResourceReader.read("data_codewords.txt");

        try (
            final StringReader reader = new StringReader(mappings);
            final BufferedReader buffered = new BufferedReader(reader))
        {
            buffered.lines()
                .map(CODEWORDS_REGEX::matcher)
                .filter(Matcher::find)
                .forEach(m ->
                {
                    final CodewordsKey key = new CodewordsKey(
                        Integer.parseInt(m.group(VERSION_GROUP)),
                        ErrorCorrectionLevel.valueOf(m.group(LEVEL_GROUP)));

                    CODEWORDS_MAP.put(key, Integer.parseInt(m.group(VALUE_GROUP)));
                });
        }
        catch (final IOException error)
        {
            throw new UncheckedIOException(error);
        }
    }

    // endregion
    // region Public API

    public static int getCodewordCount(final int version, final ErrorCorrectionLevel ecLevel)
    {
        return CODEWORDS_MAP.get(new CodewordsKey(version, ecLevel));
    }

    public static int getSmallestVersion(
        final int contentLength,
        final EncodingMode mode,
        final ErrorCorrectionLevel ecLevel)
    {
        return CAPACITY_MAP.entrySet().stream()
            .filter(e -> e.getKey().ecLevel().equals(ecLevel))
            .filter(e -> e.getKey().mode().equals(mode))
            .filter(e -> e.getValue() >= contentLength)
            .sorted(Comparator.comparing(e -> e.getKey().version()))
            .findFirst()
            .map(e -> e.getKey().version())
            .orElseThrow(() -> new IllegalArgumentException(String.format(
                "No sutible QR version could be found to fit content of size %d.",
                contentLength)));
    }

    // region Private Records

    private static record CapacityKey(int version, ErrorCorrectionLevel ecLevel, EncodingMode mode)
    {
        // No additional API.
    }

    private static record CodewordsKey(int version, ErrorCorrectionLevel ecLevel)
    {
        // No additional API.
    }

    // endregion
}
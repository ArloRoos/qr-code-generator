package com.aroos.qr.generator.encoding;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.aroos.qr.generator.common.LookupTables;
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

    private static final Pattern CAPACITY_REGEX = Pattern.compile("^(?<VERSION>\\d+):(?<LEVEL>[A-Z_]+):(?<MODE>[A-Z]+):(?<VALUE>\\d+)$");

    private static final String VERSION_GROUP = "VERSION";
    private static final String LEVEL_GROUP = "LEVEL";
    private static final String MODE_GROUP = "MODE";
    private static final String VALUE_GROUP = "VALUE";

    static
    {
        LookupTables.fillTable(
            "capacities.txt",
            CAPACITY_REGEX,
            match -> new CapacityKey(
                Integer.parseInt(match.group(VERSION_GROUP)),
                ErrorCorrectionLevel.valueOf(match.group(LEVEL_GROUP)),
                EncodingMode.valueOf(match.group(MODE_GROUP))),
            match -> Integer.parseInt(match.group(VALUE_GROUP)),
            CAPACITY_MAP::put);
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

    private static record CapacityKey(int version, ErrorCorrectionLevel ecLevel, EncodingMode mode)
    {
        // No additional API.
    }

    // endregion
}
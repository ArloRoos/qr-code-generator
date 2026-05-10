package com.aroos.qr.generator.math;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.aroos.qr.generator.common.LookupTables;

/**
 * The {@link Galois} class implements a static utility for looking up log/
 * antilog values for Galois field mathematical operations.
 */
public final class Galois
{
    // Use when going from exponent -> integer
    private static final Map<Integer, Integer> LOGS = new HashMap<>();

    // Use when going from integer -> exponenet
    private static final Map<Integer, Integer> ANTILOGS = new HashMap<>();

    private static final Pattern LOG_ENTRY = Pattern.compile("(\\d+)\\:(\\d+)");

    static
    {
        LookupTables.fillTable(
            "logs.txt",
            LOG_ENTRY,
            match -> Integer.parseInt(match.group(1)),
            match -> Integer.parseInt(match.group(2)),
            LOGS::put);

        LookupTables.fillTable(
            "antilogs.txt",
            LOG_ENTRY,
            match -> Integer.parseInt(match.group(1)),
            match -> Integer.parseInt(match.group(2)),
            ANTILOGS::put);
    }

    /**
     * Computes the log of the given value in GF(255). Use when going from
     * exponent -> integer.
     * @param value The exponent.
     * @return The base.
     */
    public static int log(final int value)
    {
        return LOGS.get(value);
    }

    /**
     * Computes the antilog of the given value in GF(255). Use when going from
     * integer -> exponent.
     * @param value The base.
     * @return The exponent.
     */
    public static int antilog(final int value)
    {
        return ANTILOGS.get(value);
    }
}
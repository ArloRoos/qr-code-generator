package com.aroos.qr.generator.common;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.aroos.qr.generator.ec.ErrorCorrectionLevel;

public final class CodewordsMirror
{
    ////////////////////////////////////////////////////////////////////////////
    // region Static mirrors
    ////////////////////////////////////////////////////////////////////////////

    private static final Map<CodewordsKey, CodewordsValue> CODEWORDS_MAP_MIRROR = new HashMap<>();

    private static final Pattern CODEWORDS_REGEX = Pattern.compile(
        "^(\\d+):([A-Z_]+),CODEWORDS:(\\d+):(\\d+),GROUP1:(\\d+):(\\d+),GROUP2:(\\d+):(\\d+)$");

    static
    {
        LookupTables.fillTable(
            "data_codewords.txt",
            CODEWORDS_REGEX,
            match -> new CodewordsKey(
                Integer.parseInt(match.group(1)),
                ErrorCorrectionLevel.valueOf(match.group(2))),
            match -> new CodewordsValue(
                Integer.parseInt(match.group(3)),
                Integer.parseInt(match.group(4)),
                new Group(
                    Integer.parseInt(match.group(5)),
                    Integer.parseInt(match.group(6))),
                new Group(
                    Integer.parseInt(match.group(7)),
                    Integer.parseInt(match.group(8)))),
            CODEWORDS_MAP_MIRROR::put);
    }

    ////////////////////////////////////////////////////////////////////////////
    // region Public API
    ////////////////////////////////////////////////////////////////////////////

    public static CodewordsValue get(QRConfiguration config)
    {
        return CODEWORDS_MAP_MIRROR.get(new CodewordsKey(config.version(), config.level()));
    }

    ////////////////////////////////////////////////////////////////////////////
    // region Private types
    ////////////////////////////////////////////////////////////////////////////

    public static record CodewordsKey(int version, ErrorCorrectionLevel ecLevel)
    {
        // No additional API.
    }

    public static record CodewordsValue(int totalCodewords, int ecPerCodeword, Group group1, Group group2)
    {
        // No additional API.
    }

    public static record Group(int blockCount, int codewordsPerBlock)
    {
        // No additional API.
    }
}
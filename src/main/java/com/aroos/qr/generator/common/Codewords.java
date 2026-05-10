package com.aroos.qr.generator.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.aroos.qr.generator.ec.ErrorCorrectionLevel;

public final class Codewords implements ICodewords
{
    ////////////////////////////////////////////////////////////////////////////
    // region Static initializers
    ////////////////////////////////////////////////////////////////////////////

    private static final Map<CodewordsKey, CodewordsValue> CODEWORDS_MAP = new HashMap<>();

    private static final Pattern CODEWORDS_REGEX = Pattern.compile("^(?<VERSION>\\d+):(?<LEVEL>[A-Z_]+):(?<VALUE>\\d+)$");

    static
    {
        LookupTables.fillTable(
            "data_codewords.txt",
            CODEWORDS_REGEX,
            match -> new CodewordsKey(
                Integer.parseInt(match.group(VERSION_GROUP)),
                ErrorCorrectionLevel.valueOf(match.group(LEVEL_GROUP))),
            match -> Integer.parseInt(match.group(VALUE_GROUP)),
            CODEWORDS_MAP::put);
    }

    ////////////////////////////////////////////////////////////////////////////
    // region Public API
    ////////////////////////////////////////////////////////////////////////////

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDataCodewordCount(final QRConfiguration config)
    {
        return CODEWORDS_MAP.get(fromConfig(config)).totalCodewords();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getErrorCorrectionCodewordsPerBlock(final QRConfiguration config)
    {
        return CODEWORDS_MAP.get(fromConfig(config)).ecPerCodeword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<List<Byte>> getBlocks(final IBitStream bits, final QRConfiguration config)
    {
        return Stream.of(List.of());
    }

    ////////////////////////////////////////////////////////////////////////////
    // region Private helpers
    ////////////////////////////////////////////////////////////////////////////

    private static CodewordsKey fromConfig(final QRConfiguration config)
    {
        return new CodewordsKey(config.version(), config.level());
    }

    ////////////////////////////////////////////////////////////////////////////
    // region Private types
    ////////////////////////////////////////////////////////////////////////////

    private static record CodewordsKey(int version, ErrorCorrectionLevel ecLevel)
    {
        // No additional API.
    }

    private static record CodewordsValue(int totalCodewords, int ecPerCodeword, Group group1, Group group2)
    {
        // No additional API.
    }

    private static record Group(int blockCount, int codewordsPerBlock)
    {
        // No additional API.
    }
}

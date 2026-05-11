package com.aroos.qr.generator.common.services;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.aroos.qr.generator.common.IBitStream;
import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.common.util.LookupTables;
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;

public final class Codewords implements ICodewords
{
    ////////////////////////////////////////////////////////////////////////////
    // region Static initializers
    ////////////////////////////////////////////////////////////////////////////

    private static final Map<CodewordsKey, CodewordsValue> CODEWORDS_MAP = new HashMap<>();

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
        final Queue<Byte> codewords = new ArrayDeque<>(bits.getCodewords());

        if (codewords.size() != this.getDataCodewordCount(config))
        {
            throw new IllegalArgumentException(String.format(
                "Failed to generate codeword blocks: BitStream codeword count %d does not match expected codeword count %d",
                codewords.size(),
                this.getDataCodewordCount(config)));
        }

        final CodewordsValue blockInfo = CODEWORDS_MAP.get(fromConfig(config));

        return Stream.concat(
            IntStream.range(0, blockInfo.group1().blockCount())
                .mapToObj(i -> IntStream.range(0, blockInfo.group1().codewordsPerBlock())
                    .mapToObj(j -> codewords.remove())
                    .toList()),
            blockInfo.group2().blockCount() > 0
                ? IntStream.range(0, blockInfo.group2().blockCount())
                    .mapToObj(i -> IntStream.range(0, blockInfo.group2().codewordsPerBlock())
                        .mapToObj(j -> codewords.remove())
                        .toList())
                : Stream.of());
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

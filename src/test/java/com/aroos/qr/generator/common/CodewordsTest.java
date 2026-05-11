package com.aroos.qr.generator.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aroos.qr.generator.common.CodewordsMirror.CodewordsValue;
import com.aroos.qr.generator.common.services.Codewords;
import com.aroos.qr.generator.common.services.ICodewords;
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;
import com.aroos.qr.generator.encoding.EncodingMode;

public final class CodewordsTest
{
    private static final ICodewords CODEWORDS = new Codewords();
    private static final Random RANDOM = new Random(Instant.now().getEpochSecond());

    ////////////////////////////////////////////////////////////////////////////
    // region Tests
    ////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] codewordsTestCases()
    {
        return Stream.of(
            new Object[] { ErrorCorrectionLevel.LEVEL_L, 1, 19, 7 },
            new Object[] { ErrorCorrectionLevel.LEVEL_M, 4, 64, 18 },
            new Object[] { ErrorCorrectionLevel.LEVEL_Q, 5, 62, 18 },
            new Object[] { ErrorCorrectionLevel.LEVEL_H, 40, 1276, 30 })
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "codewordsTestCases")
    public void codewordsTest(
        final ErrorCorrectionLevel level,
        final int version,
        final int expectedData,
        final int expectedEC)
    {
        final QRConfiguration config = new QRConfiguration(version, level, EncodingMode.BYTE);

        assertThat(CODEWORDS.getDataCodewordCount(config))
            .isEqualTo(expectedData);

        assertThat(CODEWORDS.getErrorCorrectionCodewordsPerBlock(config))
            .isEqualTo(expectedEC);
    }

    @Test(invocationCount = 10)
    public void blocksTest()
    {
        final IBitStream bits = new BitStream();
        final int version = RANDOM.nextInt(1, 41);
        final ErrorCorrectionLevel level = randomECLevel();
        final QRConfiguration config = new QRConfiguration(version, level, EncodingMode.BYTE);
        final int dataCodewords = CODEWORDS.getDataCodewordCount(config);
        final CodewordsValue blockInfo = CodewordsMirror.get(config);

        for (int i = 0; i < dataCodewords; i++)
        {
            bits.putInt(RANDOM.nextInt(), 8);
        }

        final List<List<Byte>> blocks = CODEWORDS.getBlocks(bits, config).toList();

        assertThat(blocks.stream()
                .flatMap(List::stream)
                .toList())
            .hasSameSizeAs(bits.getCodewords())
            .containsExactlyElementsOf(bits.getCodewords());

        final int expectedBlocks = blockInfo.group1().blockCount() + blockInfo.group2().blockCount();

        assertThat(blocks)
            .hasSize(expectedBlocks);

        final List<Integer> expectedSizes = Stream.concat(
            IntStream.range(0, blockInfo.group1().blockCount())
                .map(i -> blockInfo.group1().codewordsPerBlock())
                .boxed(),
            blockInfo.group2().blockCount() > 0
                ? IntStream.range(0, blockInfo.group2().blockCount())
                    .map(i -> blockInfo.group2().codewordsPerBlock())
                    .boxed()
                : Stream.of())
            .toList();

        assertThat(blocks)
            .hasSameSizeAs(expectedSizes);

        for (int i = 0; i < blocks.size(); i++)
        {
            assertThat(blocks.get(i))
                .hasSize(expectedSizes.get(i));
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // region Private helpers
    ////////////////////////////////////////////////////////////////////////////

    private static ErrorCorrectionLevel randomECLevel()
    {
        return ErrorCorrectionLevel.values()[RANDOM.nextInt(ErrorCorrectionLevel.values().length)];
    }
}
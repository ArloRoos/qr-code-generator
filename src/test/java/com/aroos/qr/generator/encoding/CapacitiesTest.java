package com.aroos.qr.generator.encoding;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aroos.qr.generator.ec.ErrorCorrectionLevel;

public final class CapacitiesTest
{
    @DataProvider
    public Object[][] capacityTestCases()
    {
        return Stream.of(
            new Object[] { EncodingMode.ALPHANUMERIC, ErrorCorrectionLevel.LEVEL_L, 1, 1},
            new Object[] { EncodingMode.ALPHANUMERIC, ErrorCorrectionLevel.LEVEL_L, 50, 3},
            new Object[] { EncodingMode.BYTE, ErrorCorrectionLevel.LEVEL_M, 14, 1},
            new Object[] { EncodingMode.BYTE, ErrorCorrectionLevel.LEVEL_Q, 15, 2},
            new Object[] { EncodingMode.NUMERIC, ErrorCorrectionLevel.LEVEL_L, 7000, 40},
            new Object[] { EncodingMode.KANJI, ErrorCorrectionLevel.LEVEL_H, 20, 4})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "capacityTestCases")
    public void smallestCapacityTest(
        final EncodingMode mode,
        final ErrorCorrectionLevel level,
        final int contentLength,
        final int expectedVersion)
    {
        assertThat(Capacities.getSmallestVersion(contentLength, mode, level))
            .isEqualTo(expectedVersion);
    }

    @Test
    public void tooLargeTest()
    {
        final EncodingMode mode = EncodingMode.ALPHANUMERIC;
        final ErrorCorrectionLevel level = ErrorCorrectionLevel.LEVEL_H;
        final int contentLength = 10000;

        assertThatThrownBy(() -> Capacities.getSmallestVersion(contentLength, mode, level))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("No sutible QR version could be found to fit content of size 10000.");
    }
}
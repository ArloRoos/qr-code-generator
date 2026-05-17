package com.aroos.qr.generator.encoding;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * The {@link CanEncodeTest} class implements
 */
public final class CanEncodeTest
{
    @DataProvider
    public Object[][] getTestCases()
    {
       return Stream.of(
            new Object[] { EncodingMode.NUMERIC, "12345", true },
            new Object[] { EncodingMode.NUMERIC, "ABCD123", false },
            new Object[] { EncodingMode.ALPHANUMERIC, "ABCD123:/", true },
            new Object[] { EncodingMode.ALPHANUMERIC, "abcd123", false },
            new Object[] { EncodingMode.BYTE, "abcd123", true },
            new Object[] { EncodingMode.BYTE, "ẞẞẞabcd", false })
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "getTestCases")
    public void canEncodeTest(final EncodingMode mode, final String input, final boolean expected)
        throws ReflectiveOperationException
    {
        assertThat(mode.canEncode(input))
            .isEqualTo(expected);
    }
}
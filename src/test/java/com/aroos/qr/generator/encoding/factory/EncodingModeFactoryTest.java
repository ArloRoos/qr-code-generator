package com.aroos.qr.generator.encoding.factory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aroos.qr.generator.encoding.EncodingMode;

public final class EncodingModeFactoryTest
{
    @DataProvider
    public Object[][] getTestCases()
    {
       return Stream.of(
            new Object[] { "12345", EncodingMode.NUMERIC},
            new Object[] { "ABC1234", EncodingMode.ALPHANUMERIC},
            new Object[] { "abc123", EncodingMode.BYTE})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "getTestCases")
    public void analyzeTest(final String content, final EncodingMode expected)
    {
        final IEncodingModeFactory factory = new EncodingModeFactory();

        assertThat(factory.analyze(content))
            .isEqualTo(expected);
    }
}
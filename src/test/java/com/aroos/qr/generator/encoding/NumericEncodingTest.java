package com.aroos.qr.generator.encoding;

import java.util.stream.Stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public final class NumericEncodingTest extends BitEncodingTest
{
    @DataProvider
    public Object[][] getTestCases()
    {
       return Stream.of(
            new Object[] { "123", "0001111011" },
            new Object[] { "1234", "00011110110100" },
            new Object[] { "08229", "10100100011101"})
           .toArray(Object[][]::new);
    }

    @Test(dataProvider = "getTestCases")
    public void numericEncodingTest(final String content, final String expectedBits)
    {
        encodingTest(content, EncodingMode.NUMERIC, expectedBits);
    }
}
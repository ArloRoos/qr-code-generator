package com.aroos.qr.generator.encoding;

import java.util.stream.Stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public final class ByteEncodingTest extends BitEncodingTest
{
    @DataProvider
    public Object[][] getTestCases()
    {
       return Stream.of(
            new Object[] { "Ayo", "010000010111100101101111" },
            new Object[] { ",!#", "001011000010000100100011" },
            new Object[] { "Hello, world!", "01001000011001010110110001101100011011110010110000100000011101110110111101110010011011000110010000100001"})
           .toArray(Object[][]::new);
    }

    @Test(dataProvider = "getTestCases", invocationCount = ITERATIONS)
    public void byteEncodingTest(final String content, final String expectedBits)
    {
        this.encodingTest(content, EncodingMode.BYTE, expectedBits);
    }
}
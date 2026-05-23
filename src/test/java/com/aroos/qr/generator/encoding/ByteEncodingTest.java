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
            new Object[] { "Hello, world!", "01001000011001010110110001101100011011110010110000100000011101110110111101110010011011000110010000100001"},
            new Object[] { "https://www.thonky.com/qr-code-tutorial/module-placement-matrix", "011010000111010001110100011100000111001100111010001011110010111101110111011101110111011100101110011101000110100001101111011011100110101101111001001011100110001101101111011011010010111101110001011100100010110101100011011011110110010001100101001011010111010001110101011101000110111101110010011010010110000101101100001011110110110101101111011001000111010101101100011001010010110101110000011011000110000101100011011001010110110101100101011011100111010000101101011011010110000101110100011100100110100101111000"})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "getTestCases", invocationCount = ITERATIONS)
    public void byteEncodingTest(final String content, final String expectedBits)
    {
        this.encodingTest(content, EncodingMode.BYTE, expectedBits);
    }
}
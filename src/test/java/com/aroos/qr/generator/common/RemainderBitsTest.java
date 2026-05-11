package com.aroos.qr.generator.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aroos.qr.generator.common.services.IRemainderBits;
import com.aroos.qr.generator.common.services.RemainderBits;

public final class RemainderBitsTest
{
    @DataProvider
    public Object[][] getTestCases()
    {
        return Stream.of(
            new Object[] { 1, 0 },
            new Object[] { 5, 7 },
            new Object[] { 14, 3 },
            new Object[] { 26, 4 },
            new Object[] { 40, 0 })
            .toArray(Object[][]::new);
    }    

    @Test(dataProvider = "getTestCases")
    public void remainderBitsTest(final int version, final int expectedBits)
    {
        final IRemainderBits remainderBits = new RemainderBits();
        final IBitStream bits = new BitStream();

        remainderBits.addRemainderBits(bits, fromVersion(version));

        assertThat(bits.getSize())
            .isEqualTo(expectedBits);

        for (int i = 0; i < bits.getSize(); i++)
        {
            assertThat(bits.at(i))
                .isFalse();
        }
    }

    private static QRConfiguration fromVersion(final int version)
    {
        return new QRConfiguration(version, null, null);
    }
}
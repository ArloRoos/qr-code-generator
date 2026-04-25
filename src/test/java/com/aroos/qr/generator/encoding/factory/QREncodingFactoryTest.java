package com.aroos.qr.generator.encoding.factory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;
import com.aroos.qr.generator.encoding.AlphaNumericEncoding;
import com.aroos.qr.generator.encoding.ByteEncoding;
import com.aroos.qr.generator.encoding.EncodingMode;
import com.aroos.qr.generator.encoding.IQREncoding;
import com.aroos.qr.generator.encoding.NumericEncoding;

public final class QREncodingFactoryTest
{
    @DataProvider
    public Object[][] getTestCases()
    {
        return Stream.of(
            new Object[] { EncodingMode.NUMERIC, NumericEncoding.class },
            new Object[] { EncodingMode.ALPHANUMERIC, AlphaNumericEncoding.class },
            new Object[] { EncodingMode.BYTE, ByteEncoding.class })
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "getTestCases")
    public void factoryTest(final EncodingMode mode, final Class<? extends IQREncoding> expected)
    {
        final QRConfiguration config = new QRConfiguration(1, ErrorCorrectionLevel.LEVEL_L, mode);

        assertThat(IQREncoding.factory().provide(config))
            .isInstanceOf(expected);
    }
}
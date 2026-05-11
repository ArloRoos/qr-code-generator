package com.aroos.qr.generator.encoding.factory;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.common.services.Codewords;
import com.aroos.qr.generator.common.services.ICodewords;
import com.aroos.qr.generator.encoding.AlphaNumericEncoding;
import com.aroos.qr.generator.encoding.ByteEncoding;
import com.aroos.qr.generator.encoding.EncodingMode;
import com.aroos.qr.generator.encoding.IQREncoding;
import com.aroos.qr.generator.encoding.NumericEncoding;

/**
 * The {@link QREncodingFactory} class implements behavior for a factory which
 * can provide an encoding strategy based on QR configuration info.
 */
public final class QREncodingFactory implements IQREncodingFactory
{
    private static final Map<EncodingMode, BiFunction<QRConfiguration, ICodewords, IQREncoding>> CONSTRUCTORS = Map.of(
        EncodingMode.ALPHANUMERIC, AlphaNumericEncoding::new,
        EncodingMode.BYTE, ByteEncoding::new,
        EncodingMode.NUMERIC, NumericEncoding::new);

    /**
     * {@inheritDoc}
     */
    @Override
    public IQREncoding provide(final QRConfiguration config)
    {
        return Optional.ofNullable(CONSTRUCTORS.get(config.mode()))
            .map(f -> f.apply(config, new Codewords()))
            .orElseThrow(() -> new IllegalArgumentException(String.format(
                "No encoding strategy found for encoding mode %s",
                config.mode().name())));
    }
}
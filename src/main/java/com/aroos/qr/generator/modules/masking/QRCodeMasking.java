package com.aroos.qr.generator.modules.masking;

import java.util.Comparator;
import java.util.stream.Stream;

import com.aroos.qr.generator.modules.IQRCode;
import com.aroos.qr.generator.modules.evaluators.IEvaluator;

/**
 * The {@link QRCodeMasking} class implements behavior for a service which
 * can "mask" a QR code, switching certain modules from black to white to make
 * it easier for a QR code reader to scan.
 */
public final class QRCodeMasking implements IQRCodeMasking
{
    private final IEvaluator evaluator;

    public QRCodeMasking()
    {
        this.evaluator = IEvaluator.instance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaskResult mask(final IQRCode code)
    {
        return Stream.of(MaskingPattern.values())
            .map(pattern -> new MaskResult(pattern.mask(code), pattern))
            .map(result -> new MaskTuple(this.evaluator.evaluate(result.masked()), result))
            .min(Comparator.comparing(MaskTuple::penalty))
            .map(MaskTuple::result)
            .orElseThrow();
    }

    private static record MaskTuple(int penalty, MaskResult result)
    {
        // No additional API.
    }
}
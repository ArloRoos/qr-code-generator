package com.aroos.qr.generator.modules.evaluators;

import static org.assertj.core.api.Assertions.assertThat;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.modules.IQRCode;
import com.aroos.qr.generator.modules.QRCode;
import com.aroos.qr.generator.png.IPNGImage;
import com.aroos.qr.generator.png.PNGWriter;

abstract class EvaluatorTest
{
    private final IEvaluator evaluator;
    private final int expectedPenalty;

    EvaluatorTest(final IEvaluator evaluator, final int expectedPenalty)
    {
        this.evaluator = evaluator;
        this.expectedPenalty = expectedPenalty;
    }

    void testEvaluator()
    {
        final QRConfiguration config = new QRConfiguration(1, null, null);
        final IQRCode code = new QRCode(config);

        prepQRCode(code);

        final IPNGImage img = code.toPNG(10);
        PNGWriter.writeImage(img, "test.png");

        assertThat(this.evaluator.evaluate(code))
            .isEqualTo(this.expectedPenalty);
    }

    abstract void prepQRCode(final IQRCode code);
}
package com.aroos.qr.generator.encoding;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.BeforeMethod;

import com.aroos.qr.generator.common.IBitStream;
import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.common.services.Codewords;
import com.aroos.qr.generator.common.services.ICodewords;
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;

class BitEncodingTest
{
    protected static final int ITERATIONS = 15;

    private static final Random RANDOM = new Random(Instant.now().getEpochSecond());
    private static final AtomicInteger COUNTER = new AtomicInteger(0);
    private static final ICodewords CODEWORDS = new Codewords();

    @BeforeMethod
    public void reset()
    {
        COUNTER.set(0);
    }

    protected void encodingTest(final String content, final EncodingMode mode, final String expectedDataBits)
    {
        final QRConfiguration config = new QRConfiguration(
            randomVersion(),
            randomErrorCorrection(),
            mode);

        // Since I'm randomly generating version/error correction levels, there
        // is a case where the given content string is impossible. Since I run
        // all the encoding tests over several iterations, I just skip this 
        // case.
        if (Capacities.getSmallestVersion(content.length(), config.mode(), config.level()) > config.version())
        {
            return;
        }

        final IQREncoding encoding = IQREncoding.factory().provide(config);
        final IBitStream result = encoding.encode(content);
        final int expectedBits = CODEWORDS.getDataCodewordCount(config) * 8;

        try
        {
            // Validate mode bits.
            validateSection(result, toBinaryString(config.mode().code(), 4));

            // Validate content length bits.
            validateSection(
                result,
                toBinaryString(content.length(), config.mode().getLengthBits(config.version())));

            // Validate content bits.
            validateSection(result, expectedDataBits);

            // Validate terminator 0s.
            validateSection(result, toBinaryString(0, 4));

            if (COUNTER.get() % 8 != 0)
            {
                validateSection(result, toBinaryString(0, 8 - (COUNTER.get() % 8)));
            }

            // Validate pad bytes.
            final AtomicBoolean switcher = new AtomicBoolean(false);
            while (COUNTER.get() < expectedBits)
            {
                final boolean current = switcher.get();
                switcher.set(!current);

                final int currentPad = current ? 17 : 236;

                validateSection(result, toBinaryString(currentPad, 8));
            }

            assertThat(COUNTER.get())
                .isEqualTo(expectedBits);
        }
        catch (final AssertionError error)
        {
            System.out.println("Error while validating content encoding at position %d:".formatted(COUNTER.get()));
            System.out.println("");
            System.out.println("    Config: %s".formatted(config));
            System.out.println("    Expected: %s".formatted(expectedDataBits));
            System.out.println("    Got:      %s %s".formatted(
                toBinaryString(result, expectedBits).substring(0, COUNTER.get()),
                toBinaryString(result, expectedBits).substring(COUNTER.get())));

            throw error;
        }
    }

    private void validateSection(final IBitStream bits, final String expectedBinary)
    {
        expectedBinary.chars()
            .mapToObj(codePoint -> (char)codePoint)
            .map(c -> c.equals('1'))
            .forEach(b -> assertThat(bits.at(COUNTER.getAndIncrement()))
                .isEqualTo(b));
    }

    // region Static Helpers

    private static String toBinaryString(final IBitStream bits, final int size)
    {
        return bits.toString().substring(0, size);
    }

    private static String toBinaryString(final int value, final int length)
    {
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++)
        {
            builder.append((value >> (length - i - 1) & 1) == 1
                ? '1'
                : '0');
        }

        return builder.toString();
    }

    private static int randomVersion()
    {
        return RANDOM.nextInt(1,41);
    }

    private static ErrorCorrectionLevel randomErrorCorrection()
    {
        return ErrorCorrectionLevel.values()[RANDOM.nextInt(ErrorCorrectionLevel.values().length)];
    }
}
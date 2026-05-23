package com.aroos.qr.generator.ec;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import com.aroos.qr.generator.common.BitStream;
import com.aroos.qr.generator.common.IBitStream;
import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.common.services.ICodewords;
import com.aroos.qr.generator.math.GaloisTerm;
import com.aroos.qr.generator.math.IPolynomial;
import com.aroos.qr.generator.math.IPolynomial.IQuotient;
import com.aroos.qr.generator.math.ITerm;
import com.aroos.qr.generator.math.Polynomial;

/**
 * The {@link ErrorCorrectionEncoder} class implements a utility for 
 * generating and adding error correction codewords to the end of an existing
 * {@link IBitStream}.
 */
public final class ErrorCorrectionEncoder implements IErrorCorrectionEncoder
{
    private final ICodewords codewords;

    public ErrorCorrectionEncoder(final ICodewords codewords)
    {
        this.codewords = codewords;
    }   

    ////////////////////////////////////////////////////////////////////////////
    // region IErrorCorrectionEncoder
    ////////////////////////////////////////////////////////////////////////////

    /**
     * {@inheritDoc}
     */
    @Override
    public IBitStream encode(final IBitStream bits, final QRConfiguration config)
    {
        final int ecCodewordCount = this.codewords.getErrorCorrectionCodewordsPerBlock(config);
        final IPolynomial generator = GeneratorPolynomials.generate(ecCodewordCount);
        final List<List<Byte>> dataCodewords = this.codewords.getBlocks(bits, config).toList();
        final List<List<Byte>> ecCodewords = dataCodewords.stream()
            .map(block -> getMessagePolynomial(block, ecCodewordCount))
            .map(message -> message.dividedByExact(generator))
            .map(quotient -> extractCodewords(quotient, ecCodewordCount))
            .toList();

        return interleave(dataCodewords, ecCodewords);
    }

    ////////////////////////////////////////////////////////////////////////////
    // region Private helpers
    ////////////////////////////////////////////////////////////////////////////
    
    private static List<Byte> extractCodewords(final IQuotient quotient, final int expectedCodewords)
    {
        final List<Byte> result = new ArrayList<>();
        final List<ITerm> terms = quotient.getRemainder().getTerms();

        for (int i = expectedCodewords - 1; i >= 0; i--)
        {
            final int exponent = i;

            result.add(terms.stream()
                .filter(t -> t.getExponent() == exponent)
                .map(t -> t.getCoefficient())
                .map(c -> (byte)c.intValue())
                .findAny()
                .orElse((byte)0));
        }

        return result;
    }

    private static IBitStream interleave(final List<List<Byte>> dataCodewords, final List<List<Byte>> ecCodewords)
    {
        final IBitStream result = new BitStream();
        
        interleave(dataCodewords, result::putByte);
        interleave(ecCodewords, result::putByte);

        return result;
    }

    private static IPolynomial getMessagePolynomial(final List<Byte> codewords, final int ecCodewordCount)
    {
        final AtomicInteger exponent = new AtomicInteger(codewords.size() - 1);
        
        return new Polynomial(codewords.stream()
            .<ITerm>map(b -> new GaloisTerm(Byte.toUnsignedInt(b), exponent.getAndDecrement() + ecCodewordCount))
            .toList());
    }

    private static void interleave(final List<List<Byte>> codewords, final Consumer<Byte> processor)
    {
        final List<Queue<Byte>> queued = codewords.stream()
            .<Queue<Byte>>map(ArrayDeque::new)
            .toList();

        while (hasAnyRemaining(queued))
        {
            queued.forEach(q -> Optional.ofNullable(q.poll())
                .ifPresent(processor));
        }
    }

    private static boolean hasAnyRemaining(final List<Queue<Byte>> queued)
    {
        return queued.stream()
            .filter(q -> !q.isEmpty())
            .findAny()
            .isPresent();
    }
}
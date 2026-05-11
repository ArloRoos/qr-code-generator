package com.aroos.qr.generator.common.services;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.aroos.qr.generator.common.IBitStream;
import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.common.util.LookupTables;

/*
 * The {@link IRemainderBits} interface defines behavior for a service which
 * appends remainder bits to the end of a bit stream.
 */
public final class RemainderBits implements IRemainderBits
{   
    private static Map<Integer, Integer> REMAINDER_BITS = new HashMap<>();
    private static Pattern REMAINDER_BITS_PATTERN = Pattern.compile("(\\d):(\\d)"); 

    static
    {
        LookupTables.fillTable(
            "remainder_bits.txt",
            REMAINDER_BITS_PATTERN,
            matcher -> Integer.parseInt(matcher.group(1)),
            matcher -> Integer.parseInt(matcher.group(2)),
            REMAINDER_BITS::put);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBitStream addRemainderBits(final IBitStream bits, final QRConfiguration config)
    {
        final int remainderBitCount = REMAINDER_BITS.get(config.version());

        bits.putInt(0, remainderBitCount);

        return bits;
    }
}
package com.aroos.qr.generator.encoding;

import java.util.Collection;

import com.aroos.qr.generator.common.QRConfiguration;

/**
 * The {@link NumericEncoding} class implements behavior for a strategy that 
 * encodes input strings using the pure numeric encoding scheme.
 */
public final class NumericEncoding
    extends QREncoding
{
    private static final int PARTITION_SIZE = 3;

    public NumericEncoding(final QRConfiguration config)
    {
        super(config);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void encodeContent(final String content)
    {
        final Collection<String> partitioned = this.partition(content, PARTITION_SIZE);

        partitioned.forEach(s ->
        {
            // Note that for all encoding methods, string validity is checked at
            // the factory level, meaning we can assume all characters in the
            // input string are numeric.
            final int value = Integer.parseInt(s);

            // Has two leading 0s, encode as a 4 bit binary number.
            if (value < 10)
            {
                this.putInt(value, 4);
            }
            // Has one leading 0, encode as a 7 bit binary number.
            else if (value < 100)
            {
                this.putInt(value, 7);
            }
            // No leading 0s, encode as a 10 bit binary number.
            else
            {
                this.putInt(value, 10);
            }
        });
    }
}
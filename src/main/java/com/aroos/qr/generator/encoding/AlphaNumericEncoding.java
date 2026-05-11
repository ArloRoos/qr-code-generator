package com.aroos.qr.generator.encoding;

import java.util.HashMap;
import java.util.Map;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.common.services.ICodewords;
import com.aroos.qr.generator.common.util.CollectionUtils;
import com.aroos.qr.generator.common.util.LookupTables;

/**
 * The {@link AlphaNumericEncoding} class implements behavior for a strategy that
 * encodes input strings using the alphanumeric encoding scheme.
 */
public final class AlphaNumericEncoding extends QREncoding
{
    private static final int PARTITION_SIZE = 2;
    private static final Map<Character, Integer> CHARACTER_ENCODING = new HashMap<>();

    static
    {
        LookupTables.fillTable(
            "alphanumeric.txt",
            line -> line.charAt(0),
            line -> Integer.parseInt(line.substring(2)),
            CHARACTER_ENCODING::put);
    }

    public AlphaNumericEncoding(final QRConfiguration config, final ICodewords codewords)
    {
        super(config, codewords);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void encodeContent(final String content)
    {
        CollectionUtils.partition(content, PARTITION_SIZE).forEach(s ->
        {
            if (s.length() == 2)
            {
                final int value =
                    CHARACTER_ENCODING.get(s.charAt(0)) * 45 +
                    CHARACTER_ENCODING.get(s.charAt(1));

                this.bits.putInt(value, 11);
            }
            // The original string was an odd length, handle the final character
            // slightly different.
            else
            {
                final int value = CHARACTER_ENCODING.get(s.charAt(0));

                this.bits.putInt(value, 6);
            }
        });
    }
}
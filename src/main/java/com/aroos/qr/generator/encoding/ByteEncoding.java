package com.aroos.qr.generator.encoding;

import java.nio.charset.StandardCharsets;

import com.aroos.qr.generator.common.ICodewords;
import com.aroos.qr.generator.common.QRConfiguration;

/**
 * The {@link ByteEncoding} class implements behavior for a strategy that
 * encodes input strings using the ISO_8859_1 encoding scheme.
 */
public final class ByteEncoding extends QREncoding
{
    public ByteEncoding(final QRConfiguration config, final ICodewords codewords)
    {
        super(config, codewords);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void encodeContent(final String content)
    {
        for (final byte b : content.getBytes(StandardCharsets.ISO_8859_1))
        {
            this.bits.putInt(b, 8);
        }
    }
}
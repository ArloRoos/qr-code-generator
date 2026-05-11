package com.aroos.qr.generator.encoding;

import com.aroos.qr.generator.common.BitStream;
import com.aroos.qr.generator.common.IBitStream;
import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.common.services.ICodewords;

/**
 * The {@link QREncoding} class implements a base class containing common
 * encoding methods for all QR encoding strategies.
 */
abstract class QREncoding implements IQREncoding
{
    private static final int CODE_LENGTH = 4;
    private static final int MAX_TERMINATOR_LENGTH = 4;
    private static final int PAD_BYTE_1 = 236;
    private static final int PAD_BYTE_2 = 17;

    protected final IBitStream bits;
    protected final QRConfiguration config;

    private final int requiredBits;

    protected QREncoding(final QRConfiguration config, final ICodewords codewords)
    {
        this.config = config;
        this.bits = new BitStream();
        this.requiredBits = codewords.getDataCodewordCount(config) * 8;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBitStream encode(final String content)
    {
        this.putMode();
        this.putLength(content.length());
        this.encodeContent(content);
        this.putTerminator();
        this.putPadBytes();

        return this.bits;
    }

    /**
     * Encode the content string using this object's encoding method.
     * @param content The content to encode.
     */
    protected abstract void encodeContent(final String content);

    private void putMode()
    {
        this.bits.putInt(this.config.mode().code(), CODE_LENGTH);
    }

    private void putLength(final int length)
    {
        this.bits.putInt(length, this.config.mode().getLengthBits(this.config.version()));
    }

    private void putTerminator()
    {
        // Add a terminator of 0s until there are 4 or the total bit capacity is
        // filled.
        final int terminatorLength = Math.min(this.requiredBits - this.bits.getSize(), MAX_TERMINATOR_LENGTH);

        for (int i = 0; i < terminatorLength; i++)
        {
            this.bits.putInt(0, 1);
        }

        // Add padding 0s until the bit set is a multiple of 8.
        if (this.bits.getSize() < this.requiredBits && this.bits.getSize() % 8 != 0)
        {
            final int paddingLength = 8 - (this.bits.getSize() % 8);

            for (int i = 0; i < paddingLength; i++)
            {
                this.bits.putInt(0, 1);
            }
        }
    }

    private void putPadBytes()
    {
        // This division should always result in a whole number, since the
        // position should be a multiple of 8 by this point.
        final int padCount = (this.requiredBits - this.bits.getSize()) / 8;

        for (int i = 0; i < padCount; i++)
        {
            // These bytes are an arbitrary part of the QR specification, and
            // alternate until the entire QR capacity is full.
            final int padByte = i % 2 == 0
                ? PAD_BYTE_1
                : PAD_BYTE_2;

            this.bits.putInt(padByte, 8);
        }
    }
}
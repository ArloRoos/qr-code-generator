package com.aroos.qr.generator.common.services;

import java.util.List;
import java.util.stream.Stream;

import com.aroos.qr.generator.common.IBitStream;
import com.aroos.qr.generator.common.QRConfiguration;

/**
 * The {@link ICodewords} interface defines behavior for a service providing
 * utility methods for working with bit stream codewords.
 */
public interface ICodewords
{
    /**
     * Gets the total amount of data codewords for the given QR code
     * configuration.
     * @param config The QR code configuration information.
     * @return The number of data codewords for the given QR code.
     */
    int getDataCodewordCount(QRConfiguration config);

    /**
     * Gets the total amount of error correction codewords for the given QR code
     * configuration.
     * @param config The QR code configuration information.
     * @return The number of data codewords for the given QR code.
     */
    int getErrorCorrectionCodewordsPerBlock(QRConfiguration config);

    /**
     * Splits the given bit stream into blocks for error correction processing.
     * @param bits The current bits stream of data.
     * @param config The QR code configuration information.
     * @return A stream of "blocks" derived from the given bit stream. A block
     * consists of a list of 8 bit data codewords.
     */
    Stream<List<Byte>> getBlocks(IBitStream bits, QRConfiguration config);
}
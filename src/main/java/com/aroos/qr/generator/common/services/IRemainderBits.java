package com.aroos.qr.generator.common.services;

import com.aroos.qr.generator.common.IBitStream;
import com.aroos.qr.generator.common.QRConfiguration;

/*
 * The {@link IRemainderBits} interface defines behavior for a service which
 * appends remainder bits to the end of a bit stream.
 */
public interface IRemainderBits
{
    /**
     * Appends remainder bits to the end of a bit stream.
     * @param bits The current bit stream.
     * @param config The QR code configuration information. 
     * @return The bit stream with remainder bits appended.
     */
    IBitStream addRemainderBits(IBitStream bits, QRConfiguration config);
}
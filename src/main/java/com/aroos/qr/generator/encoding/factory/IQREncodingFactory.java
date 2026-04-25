package com.aroos.qr.generator.encoding.factory;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.encoding.IQREncoding;

/**
 * The {@link IQREncodingFactory} interface defines behavior for a factory which
 * can provide an encoding strategy based on QR configuration info.
 */
public interface IQREncodingFactory
{
    IQREncoding provide(QRConfiguration config);
}
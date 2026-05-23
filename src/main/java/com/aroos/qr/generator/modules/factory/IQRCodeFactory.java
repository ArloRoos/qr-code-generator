package com.aroos.qr.generator.modules.factory;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link IQRCodeFactory} interface defines behavior for a factory which
 * provides a pre-configured QR code instance, with all reserved modules filled 
 * in.
 */
public interface IQRCodeFactory
{
    IQRCode provide(QRConfiguration config);
}
package com.aroos.qr.generator.modules;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.png.PNGImage;

/*
 * The {@link IQRCode} interface defines behavior for a structure representing
 * a graphical QR code. 
 */
public interface IQRCode
{
    int getSize();

    int getVersion();

    boolean getModule(int x, int y);

    void setModule(int x, int y, boolean value);

    void setReservedModule(int x, int y, boolean value);

    boolean isReserved(int x, int y);

    PNGImage toPNG(int scaling);

    static IQRCode from(QRConfiguration config)
    {
        return new QRCode(config);
    }
}
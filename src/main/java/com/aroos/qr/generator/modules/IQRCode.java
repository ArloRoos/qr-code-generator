package com.aroos.qr.generator.modules;

import com.aroos.qr.generator.modules.factory.IQRCodeFactory;
import com.aroos.qr.generator.modules.factory.QRCodeFactory;
import com.aroos.qr.generator.png.PNGImage;

/*
 * The {@link IQRCode} interface defines behavior for a structure representing
 * a graphical QR code. 
 */
public interface IQRCode
{
    /**
     * Gets the size of this QR code.
     * @return The size.
     */
    int getSize();

    /**
     * Gets the version of this QR code, from 1-40.
     * @return The version.
     */
    int getVersion();

    /**
     * Get the module value at the given coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The module value, true for black, false for white.
     */
    boolean getModule(int x, int y);

    /**
     * Sets the module at the given coordinates to the given value.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param value The value to set the module at.
     */
    void setModule(int x, int y, boolean value);

    /**
     * Sets a reserved module at the given coordinates. This module will not be
     * overwritten by subsequent calls to setModule(), but can still be
     * overwritten by a call to setReservedModule().
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param value The value to set the reserved module to.
     */
    void setReservedModule(int x, int y, boolean value);

    /**
     * Checks whether the module at the given coordinates is reserved.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return True if the module is reserved, false otherwise.
     */
    boolean isReserved(int x, int y);

    /**
     * Checks whether the module at the given coordinates has been set.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return True if the module has been set, false otherwise.
     */
    boolean isSet(int x, int y);

    /**
     * Copies this QR code, preserves all reserved information.
     * @return A copy of this QR code.
     */
    IQRCode copy();

    /**
     * Converts this QR code to a PNG image.
     * @param scaling How many pixels should be used per module. For instance,
     * if a scaling value of 5 is given, each module of the QR code will take up
     * a 5x5 square of pixels in the final PNG image.
     * @return The PNG representation of this QR code.
     */
    PNGImage toPNG(int scaling);

    /**
     * Get a factory provider for QR codes.
     * @return The QR code factory.
     */
    static IQRCodeFactory factory()
    {
        return new QRCodeFactory();
    }
}
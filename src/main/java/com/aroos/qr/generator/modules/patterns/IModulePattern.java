package com.aroos.qr.generator.modules.patterns;

import java.util.function.Consumer;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link IModulePattern} interface defines behavior for a QR code modifier
 * which adds a fixed pattern to an existing QR code.
 */
public interface IModulePattern extends Consumer<IQRCode>
{
    // No additional API.
}
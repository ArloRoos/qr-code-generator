package com.aroos.qr.generator.modules;

import java.util.function.Consumer;

import com.aroos.qr.generator.common.IBitStream;

/**
 * The {@link IDataBitModules} interface defines behavior for placing data 
 * codewords into a QR code.
 */
public interface IDataBitModules extends Consumer<IBitStream>
{
    // No additional API.
}
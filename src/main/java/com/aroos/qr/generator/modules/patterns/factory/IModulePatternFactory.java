package com.aroos.qr.generator.modules.patterns.factory;

import com.aroos.qr.generator.modules.patterns.IModulePattern;

/**
 * The {@link IModulePatternFactory} interface defines a factory which can 
 * provide an {@link IModulePattern} instance.
 */
public interface IModulePatternFactory
{
    /**
     * Provides a module pattern applicator instance.
     * @return A module pattern which applies all required module patterns to a
     * QR code.
     */
    IModulePattern provide();
}
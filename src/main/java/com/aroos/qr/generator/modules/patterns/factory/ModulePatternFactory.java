package com.aroos.qr.generator.modules.patterns.factory;

import com.aroos.qr.generator.modules.patterns.CompositeModulePattern;
import com.aroos.qr.generator.modules.patterns.IModulePattern;

public final class ModulePatternFactory implements IModulePatternFactory
{
    @Override
    public IModulePattern provide()
    {
        return new CompositeModulePattern();
    }
}
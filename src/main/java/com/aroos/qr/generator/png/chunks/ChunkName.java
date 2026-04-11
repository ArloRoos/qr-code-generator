package com.aroos.qr.generator.png.chunks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@link ChunkName} @interface defines an annotation for labeling PNG
 * chunk types with their ASCII name code.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ChunkName
{
    String value();
}
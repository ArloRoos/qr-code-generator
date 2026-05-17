package com.aroos.qr.generator.encoding.factory;

import com.aroos.qr.generator.encoding.EncodingMode;

/**
 * The {@link IEncodingModeFactory} interface defines a factory which provides
 * the most space efficient encoding method for a given content string.
 */
public interface IEncodingModeFactory
{
    /**
     * Analyze the content string and return the most space efficient encoding
     * mode.
     * @param content The content to analyze.
     * @return The most efficient encoding mode for the given string.
     */
    EncodingMode analyze(String content);
}
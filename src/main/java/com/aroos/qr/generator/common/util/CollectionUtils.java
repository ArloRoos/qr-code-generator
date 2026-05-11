package com.aroos.qr.generator.common.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The {@link CollectionUtils} class implements static utility methods for
 * working with collections.
 */
public final class CollectionUtils
{
    /**
     * Partition the given string into a list of substrings with the given size.
     * @param content The content to partition.
     * @param partitionSize The size of each partition.
     * @return The partitions of the original string.
     */
    public static Collection<String> partition(final String content, final int partitionSize)
    {
        final Collection<String> parts = new ArrayList<>();

        for (int i = 0; i < content.length(); i += partitionSize)
        {
            parts.add(content.substring(i, Math.min(content.length(), i + partitionSize)));
        }

        return parts;
    }
}
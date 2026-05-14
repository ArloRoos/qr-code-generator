package com.aroos.qr.generator.modules;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testng.annotations.Test;

import com.aroos.qr.generator.common.util.ResourceReader;

public final class ConvertAlignments
{
    @Test(groups = "convert")
    public void convertTable()
        throws IOException
    {
        final String content = ResourceReader.read("old/old_alignments.txt");

        if (!Files.exists(Path.of("alignments.txt")))
        {
            Files.createFile(Path.of("alignments.txt"));
        }

        try (final FileOutputStream out = new FileOutputStream("alignments.txt"))
        {
            for (final String line : content.lines().toList())
            {
                this.processLine(out, line);
            }
        }
    }

    private void processLine(final FileOutputStream out, final String line)
        throws IOException
    {
        final String[] tokens = line.split("\t");
        final int version = Integer.parseInt(tokens[0].substring(11));
        final Collection<Integer> coords = Stream.of(Arrays.copyOfRange(tokens, 1, tokens.length))
            .map(Integer::parseInt)
            .toList();

        writeCapacity(out, version, coords);
    }

    private static void writeCapacity(
        final FileOutputStream out,
        final int version,
        final Collection<Integer> coords) throws IOException
    {
        final String entry = Integer.toString(version) + ":" + coords.stream()
            .map(i -> i.toString())
            .collect(Collectors.joining(",")) + "\n";

        out.write(entry.getBytes(StandardCharsets.UTF_8));
    }
}

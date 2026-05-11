package com.aroos.qr.generator.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.testng.annotations.Test;

import com.aroos.qr.generator.common.util.ResourceReader;
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;

public final class ConvertCodewords
{
    @Test(groups = "convert")
    public void convertTest()
        throws IOException
    {
        final String content = ResourceReader.read("old/old_data_codewords.txt");

        if (!Files.exists(Path.of("new_codewords.txt")))
        {
            Files.createFile(Path.of("new_codewords.txt"));
        }

        try (final FileOutputStream out = new FileOutputStream("new_codewords.txt"))
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
        final String[] keyTokens = tokens[0].split("-");

        final int version = toInt(keyTokens[0]);
        final ErrorCorrectionLevel ec = parseEC(keyTokens[1]);
        final int dataCodewords = toInt(tokens[1]);
        final int ecCodewords = toInt(tokens[2]);
        final int group1Blocks = toInt(tokens[3]);
        final int group1Codewords = toInt(tokens[4]);

        final int group2Blocks = tokens[5] != ""
            ? toInt(tokens[5])
            : 0;
        final int group2Codewords = tokens[6] != ""
            ? toInt(tokens[6])
            : 0;

        writeCodewords(
            out,
            version,
            ec,
            dataCodewords,
            ecCodewords,
            group1Blocks,
            group1Codewords,
            group2Blocks,
            group2Codewords);
    }

    private ErrorCorrectionLevel parseEC(final String token)
    {
        switch(token)
        {
            case "L":
                return ErrorCorrectionLevel.LEVEL_L;
            case "M":
                return ErrorCorrectionLevel.LEVEL_M;
            case "Q":
                return ErrorCorrectionLevel.LEVEL_Q;
            case "H":
                return ErrorCorrectionLevel.LEVEL_H;
            default:
                return null;
        }
    }

    private static int toInt(final String token)
    {
        return Integer.parseInt(token);
    }

    private static void writeCodewords(
        final FileOutputStream out,
        final int version,
        final ErrorCorrectionLevel ec,
        final int dataCodewords,
        final int ecCodewords,
        final int group1Blocks,
        final int group1Codewords,
        final int group2Blocks,
        final int group2Codewords) throws IOException
    {
        final String entry = String.format(
            "%d:%s,CODEWORDS:%d:%d,GROUP1:%d:%d,GROUP2:%d:%d\n",
            version,
            ec.name(),
            dataCodewords,
            ecCodewords,
            group1Blocks,
            group1Codewords,
            group2Blocks,
            group2Codewords);

        out.write(entry.getBytes(StandardCharsets.UTF_8));
    }
}
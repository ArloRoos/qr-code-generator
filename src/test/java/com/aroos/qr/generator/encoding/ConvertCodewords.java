package com.aroos.qr.generator.encoding;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.testng.annotations.Test;

import com.aroos.qr.generator.common.ResourceReader;
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;

public final class ConvertCodewords
{
    @Test
    public void convertTest()
        throws IOException
    {
        final String content = ResourceReader.read("data_codewords.txt");

        if (!Files.exists(Path.of("new_codewords.txt")))
        {
            Files.createFile(Path.of("new_codewords.txt"));
        }

        try (final StringReader reader = new StringReader(content);
            final BufferedReader buffered = new BufferedReader(reader);
            final FileOutputStream out = new FileOutputStream("new_codewords.txt"))
        {
            while (true)
            {
                final String line = buffered.readLine();

                if (line == null) break;

                this.processLine(out, line);
            }
        }


    }

    private void processLine(final FileOutputStream out, final String line)
        throws IOException
    {
        final String[] tokens = line.split("\t");
        final String[] versionLevelTokens = tokens[0].split("-");
        final int version = Integer.parseInt(versionLevelTokens[0]);
        final ErrorCorrectionLevel level = parseEC(versionLevelTokens[1]);
        final String[] equationTokens = tokens[tokens.length - 1].split("= ");
        final int value = Integer.parseInt(equationTokens[1]);

        writeCodewords(out, version, level, value);
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

    private static void writeCodewords(
        final FileOutputStream out,
        final int version,
        final ErrorCorrectionLevel ec,
        final int value) throws IOException
    {
        final String entry = String.format("%d:%s:%d\n", version, ec.name(), value);

        out.write(entry.getBytes(StandardCharsets.UTF_8));
    }
}
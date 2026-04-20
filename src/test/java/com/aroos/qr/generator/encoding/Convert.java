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

public final class Convert
{
    @Test
    public void convertTable()
        throws IOException
    {
        final String content = ResourceReader.read("capacities.txt");

        if (!Files.exists(Path.of("capacities.txt")))
        {
            Files.createFile(Path.of("capacities.txt"));
        }

        try (final StringReader reader = new StringReader(content);
            final BufferedReader buffered = new BufferedReader(reader);
            final FileOutputStream out = new FileOutputStream("capacities.txt"))
        {
            buffered.readLine();

            while (true)
            {
                final String versionLine = buffered.readLine();

                if (versionLine == null) break;

                final int version = this.processVersionLine(out, versionLine);

                this.processLine(out, buffered.readLine(), version);
                this.processLine(out, buffered.readLine(), version);
                this.processLine(out, buffered.readLine(), version);
            }
        }   
    }

    private int processVersionLine(final FileOutputStream out, final String line)
        throws IOException
    {
        final String[] tokens = line.split("\t");

        final int version = Integer.parseInt(tokens[0]);
        final ErrorCorrectionLevel level = parseEC(tokens[1]);
        
        writeCapacity(out, version, level, EncodingMode.NUMERIC, Integer.parseInt(tokens[2]));
        writeCapacity(out, version, level, EncodingMode.ALPHANUMERIC, Integer.parseInt(tokens[3]));
        writeCapacity(out, version, level, EncodingMode.BYTE, Integer.parseInt(tokens[4]));
        writeCapacity(out, version, level, EncodingMode.KANJI, Integer.parseInt(tokens[5]));

        return version;
    }

    private void processLine(final FileOutputStream out, final String line, final int version)
        throws IOException
    {
        final String[] tokens = line.split("\t");

        final ErrorCorrectionLevel level = parseEC(tokens[0]);
        
        writeCapacity(out, version, level, EncodingMode.NUMERIC, Integer.parseInt(tokens[1]));
        writeCapacity(out, version, level, EncodingMode.ALPHANUMERIC, Integer.parseInt(tokens[2]));
        writeCapacity(out, version, level, EncodingMode.BYTE, Integer.parseInt(tokens[3]));
        writeCapacity(out, version, level, EncodingMode.KANJI, Integer.parseInt(tokens[4]));
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

    private static void writeCapacity(
        final FileOutputStream out, 
        final int version, 
        final ErrorCorrectionLevel ec, 
        final EncodingMode mode, 
        final int value) throws IOException
    {
        final String entry = String.format("%d:%s:%s:%d\n", version, ec.name(), mode.name(), value);

        out.write(entry.getBytes(StandardCharsets.UTF_8));
    }
}

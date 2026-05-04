package com.aroos.qr.generator.math;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.testng.annotations.Test;

import com.aroos.qr.generator.common.ResourceReader;

public final class ConvertLogAntilog
{
    @Test(groups = "convert")
    public void convertTest()
        throws IOException
    {
        final String content = ResourceReader.read("old/old_log_antilog.txt");

        try (final StringReader reader = new StringReader(content);
            final BufferedReader buffered = new BufferedReader(reader);
            final FileOutputStream logs = new FileOutputStream("logs.txt");
            final FileOutputStream antilogs = new FileOutputStream("antilogs.txt"))
        {
            while (true)
            {
                final String line = buffered.readLine();

                if (line == null) break;

                final String[] tokens = line.split("\t+");
                final String logsEntry = "%s:%s\n".formatted(tokens[0], tokens[1]);
                final String antilogsEntry = "%s:%s\n".formatted(tokens[2], tokens[3]);

                logs.write(logsEntry.getBytes(StandardCharsets.UTF_8));
                antilogs.write(antilogsEntry.getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
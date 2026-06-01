package com.aroos.qr.generator.modules.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import com.aroos.qr.generator.common.BitStream;
import com.aroos.qr.generator.common.IBitStream;
import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.common.util.LookupTables;
import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link VersionInfoModules} class implements behavior for a service which
 * can apply version information modules to a QR code.
 */
public final class VersionInfoModules implements IVersionInfoModules
{
    ////////////////////////////////////////////////////////////////////////////
    // Static Initializers
    ////////////////////////////////////////////////////////////////////////////

    private static final Map<Integer, String> VERSION_INFO_MAP = new HashMap<>();
    private static final Pattern VERSION_INFO_PATTERN = Pattern.compile("(\\d+):([01]+)");
    private static final int VERSION_INFO_SIZE = 18;
    private static final int VERSION_INFO_DIM_1 = 6;
    private static final int VERSION_INFO_DIM_2 = 3;

    static
    {
        LookupTables.fillTable(
            "version_codes.txt",
            VERSION_INFO_PATTERN,
            match -> Integer.parseInt(match.group(1)),
            match -> match.group(2),
            VERSION_INFO_MAP::put);
    }

    ////////////////////////////////////////////////////////////////////////////
    // FormatInfoModules
    ////////////////////////////////////////////////////////////////////////////

    private final IQRCode qrCode;
    private final List<List<Coordinate>> versionCoords;

    public VersionInfoModules(final IQRCode qrCode)
    {
        this.qrCode = qrCode;
        this.versionCoords = new ArrayList<>();

        for (int i = 0; i < VERSION_INFO_SIZE; i++)
        {
            versionCoords.add(new ArrayList<>());
        }

        // Bottom left coordinates
        for (int i = 0; i < VERSION_INFO_DIM_1; i++)
        {
            for (int j = 0; j < VERSION_INFO_DIM_2; j++)
            {
                versionCoords.get(i * VERSION_INFO_DIM_2 + j)
                    .add(new Coordinate(i, qrCode.getSize() - 8 - (VERSION_INFO_DIM_2 - j)));
            }
        }

        // Top right coordinates
        for (int i = 0; i < VERSION_INFO_DIM_1; i++)
        {
            for (int j = 0; j < VERSION_INFO_DIM_2; j++)
            {
                versionCoords.get(i * VERSION_INFO_DIM_2 + j)
                    .add(new Coordinate(qrCode.getSize() - 8 - (VERSION_INFO_DIM_2 - j), i));
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Public API
    ////////////////////////////////////////////////////////////////////////////

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyVersionInfo(final QRConfiguration config)
    {
        // Version info bits are only for codes version 7 and above.
        if (config.version() < 7)
        {
            return;
        }

        final String bits = VERSION_INFO_MAP.get(config.version());
        final IBitStream bitStream = new BitStream();

        bits.chars()
            .mapToObj(Character::toString)
            .forEach(bit -> Optional.of(bit)
                .filter(b -> b.equals("1"))
                .ifPresentOrElse(
                    b -> bitStream.putBit(true),
                    () -> bitStream.putBit(false)));

        IntStream.range(0, VERSION_INFO_SIZE)
            .forEach(i -> this.versionCoords.get(i).forEach(coord ->
                this.qrCode.setReservedModule(coord.x(), coord.y(), bitStream.at(i))));
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private Types
    ////////////////////////////////////////////////////////////////////////////

    private static record Coordinate(int x, int y)
    {
        // No additional API.
    }
}
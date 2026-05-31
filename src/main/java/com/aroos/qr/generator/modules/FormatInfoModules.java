package com.aroos.qr.generator.modules;

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
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;
import com.aroos.qr.generator.modules.masking.MaskingPattern;

/**
 * The {@link FormatInfoModules} class implements behavior for a service which
 * can apply format and version information modules to a QR code.
 */
public final class FormatInfoModules implements IFormatInfoModules
{
    ////////////////////////////////////////////////////////////////////////////
    // Static Initializers
    ////////////////////////////////////////////////////////////////////////////

    private static final Map<FormatInfoKey, String> FORMAT_INFO_MAP = new HashMap<>();
    private static final Map<Integer, String> VERSION_INFO_MAP = new HashMap<>();
    private static final Pattern FORMAT_INFO_PATTERN = Pattern.compile("([A-Z_]+):(\\d):([01]+)");
    private static final Pattern VERSION_INFO_PATTERN = Pattern.compile("(\\d+):([01]+)");
    private static final int FORMAT_INFO_SIZE = 15;
    private static final int FORMAT_INFO_DIVIDER = 7;

    static
    {
        LookupTables.fillTable(
            "ec_mask_codes.txt",
            FORMAT_INFO_PATTERN,
            match -> new FormatInfoKey(
                Integer.parseInt(match.group(2)),
                ErrorCorrectionLevel.valueOf(match.group(1))),
            match -> match.group(3),
            FORMAT_INFO_MAP::put);

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
    private final List<List<Coordinate>> formatCoords;

    public FormatInfoModules(final IQRCode qrCode)
    {
        this.qrCode = qrCode;
        this.formatCoords = new ArrayList<>();

        for (int i = 0; i < FORMAT_INFO_SIZE; i++)
        {
            formatCoords.add(new ArrayList<>());
        }

        // Constant format positions
        formatCoords.get(0).add(new Coordinate(0, 8));
        formatCoords.get(1).add(new Coordinate(1, 8));
        formatCoords.get(2).add(new Coordinate(2, 8));
        formatCoords.get(3).add(new Coordinate(3, 8));
        formatCoords.get(4).add(new Coordinate(4, 8));
        formatCoords.get(5).add(new Coordinate(5, 8));
        formatCoords.get(6).add(new Coordinate(7, 8));
        formatCoords.get(7).add(new Coordinate(8, 8));
        formatCoords.get(8).add(new Coordinate(8, 7));
        formatCoords.get(9).add(new Coordinate(8, 5));
        formatCoords.get(10).add(new Coordinate(8, 4));
        formatCoords.get(11).add(new Coordinate(8, 3));
        formatCoords.get(12).add(new Coordinate(8, 2));
        formatCoords.get(13).add(new Coordinate(8, 1));
        formatCoords.get(14).add(new Coordinate(8, 0));

        // Bottom left coordinates
        for (int i = 0; i < FORMAT_INFO_DIVIDER; i++)
        {
            formatCoords.get(i).add(new Coordinate(8, qrCode.getSize() - (i + 1)));
        }

        // Top right coordinates
        for (int i = FORMAT_INFO_DIVIDER; i < FORMAT_INFO_SIZE; i++)
        {
            formatCoords.get(i).add(new Coordinate(qrCode.getSize() - (FORMAT_INFO_SIZE - i), 8));
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Public API
    ////////////////////////////////////////////////////////////////////////////

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyFormatInfo(final MaskingPattern maskType, final QRConfiguration config)
    {
        final int maskId = maskType.getId();
        final FormatInfoKey key = new FormatInfoKey(maskId, config.level());
        final String bits = FORMAT_INFO_MAP.get(key);
        final IBitStream bitStream = new BitStream();

        bits.chars()
            .mapToObj(Character::toString)
            .forEach(bit -> Optional.of(bit)
                .filter(b -> b.equals("1"))
                .ifPresentOrElse(
                    b -> bitStream.putBit(true),
                    () -> bitStream.putBit(false)));

        IntStream.range(0, FORMAT_INFO_SIZE)
            .forEach(i -> this.formatCoords.get(i).forEach(coord ->
                this.qrCode.setModule(coord.x(), coord.y(), bitStream.at(i))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyVersionInfo(final QRConfiguration config)
    {
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private Types
    ////////////////////////////////////////////////////////////////////////////

    private static record FormatInfoKey(int maskCode, ErrorCorrectionLevel level)
    {
        // No additional API.
    }

    private static record Coordinate(int x, int y)
    {
        // No additional API.
    }
}
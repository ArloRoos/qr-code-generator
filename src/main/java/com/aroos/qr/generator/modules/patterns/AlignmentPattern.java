package com.aroos.qr.generator.modules.patterns;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.aroos.qr.generator.common.util.LookupTables;
import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link AlignmentPattern} class implements behavior for adding the small
 * concentric square alignment patterns to designated coordinates of a given QR
 * code.
 */
public final class AlignmentPattern implements IModulePattern
{
    private static final int TOP_LEFT_OFFSET = 2;
    private static final Collection<ModuleOffset> SINGLE_PATTERN = List.of(
        // L1
        offset(0, 0, true),
        offset(0, 1, true),
        offset(0, 2, true),
        offset(0, 3, true),
        offset(0, 4, true),
        offset(1, 0, true),
        offset(2, 0, true),
        offset(3, 0, true),
        offset(4, 0, true),
        offset(4, 1, true),
        offset(4, 2, true),
        offset(4, 3, true),
        offset(4, 4, true),
        offset(1, 4, true),
        offset(2, 4, true),
        offset(3, 4, true),

        // L2
        offset(1, 1, false),
        offset(1, 2, false),
        offset(1, 3, false),
        offset(2, 1, false),
        offset(3, 1, false),
        offset(2, 3, false),
        offset(3, 2, false),
        offset(3, 3, false),

        // CENTER
        offset(2, 2, true));

    private static final Map<Integer, List<Integer>> VERSION_COORDS = new HashMap<>();

    static
    {
        LookupTables.fillTable(
            "alignments.txt",
            line -> Integer.parseInt(line.split(":")[0]),
            line -> Stream.of(line.split(":")[1].split(","))
                .map(Integer::parseInt)
                .toList(),
            VERSION_COORDS::put);

        // Version 1 has no finder patterns, and is not in the lookup table. To
        // avoid superfluous checking in the main logic, just add an empty list
        // manually.
        VERSION_COORDS.put(1, List.of());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final IQRCode qrCode)
    {
        final List<Integer> coordinates = VERSION_COORDS.get(qrCode.getVersion());

        coordinates.stream()
            .flatMap(x -> coordinates.stream()
                .map(y -> Map.entry(x, y)))
            .map(e -> Map.entry(e.getKey() - TOP_LEFT_OFFSET, e.getValue() - TOP_LEFT_OFFSET))
            .filter(e -> alignmentPositionValid(qrCode, e.getKey(), e.getValue()))
            .forEach(e -> putAlignmentPattern(qrCode, e.getKey(), e.getValue()));
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private Helpers
    ////////////////////////////////////////////////////////////////////////////

    private static boolean alignmentPositionValid(final IQRCode qrCode, final int topLeftX, final int topLeftY)
    {
        return !SINGLE_PATTERN.stream()
            .map(module -> qrCode.isReserved(topLeftX + module.xOffset(), topLeftY + module.yOffset()))
            .filter(b -> b)
            .findAny()
            .isPresent();
    }

    private static void putAlignmentPattern(final IQRCode qrCode, final int topLeftX, final int topLeftY)
    {
        SINGLE_PATTERN.forEach(module -> qrCode.setReservedModule(
            topLeftX + module.xOffset(),
            topLeftY + module.yOffset(),
            module.value()));
    }

    private static ModuleOffset offset(final int xOffset, final int yOffset, final boolean value)
    {
        return new ModuleOffset(xOffset, yOffset, value);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private Types
    ////////////////////////////////////////////////////////////////////////////

    private static record ModuleOffset(int xOffset, int yOffset, boolean value)
    {
        // No additional API.
    }
}
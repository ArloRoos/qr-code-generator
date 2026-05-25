package com.aroos.qr.generator.modules.masking;

import java.util.function.BiFunction;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link MaskingPattern} enumeration implements behaveior for the 8 
 * different masking patterns which can be used to mask a QR code.
 */
public enum MaskingPattern
{
    MASK_0((x, y) -> (x + y) % 2 == 0),
    MASK_1((x, y) -> x % 2 == 0),
    MASK_2((x, y) -> y % 3 == 0),
    MASK_3((x, y) -> (x + y) % 3 == 0),
    MASK_4((x, y) -> (Math.floor((double)x / 2) + Math.floor((double)y / 3)) % 2 == 0),
    MASK_5((x, y) -> ((x * y) % 2) + ((x * y) % 3) == 0),
    MASK_6((x, y) -> (((x * y) % 2) + ((x * y) % 3)) % 2 == 0),
    MASK_7((x, y) -> (((x + y) % 2) + ((x + y) % 3)) % 2 == 0);

    private final BiFunction<Integer, Integer, Boolean> criteria;

    private MaskingPattern(final BiFunction<Integer, Integer, Boolean> criteria)
    {
        this.criteria = criteria;
    }

    public IQRCode mask(final IQRCode code)
    {
        final IQRCode newCode = code.copy();

        for (int i = 0; i < newCode.getSize(); i++)
        {
            for (int j = 0; j < newCode.getSize(); i++)
            {
                if (!newCode.isReserved(i, j) && this.criteria.apply(i, j))
                {
                    newCode.setModule(i, j, !newCode.getModule(j, i));
                }
            }
        }

        return newCode;
    }
}
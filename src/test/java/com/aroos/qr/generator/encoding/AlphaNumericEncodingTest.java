package com.aroos.qr.generator.encoding;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aroos.qr.generator.common.QRConfiguration;
import com.aroos.qr.generator.common.services.Codewords;
import com.aroos.qr.generator.ec.ErrorCorrectionLevel;

public final class AlphaNumericEncodingTest extends BitEncodingTest
{
    @BeforeClass
    @SuppressWarnings("unused")
    public void setUp()
    {
        // This is so dumb, but I need to make sure the class is actually
        // initialized so the static field exists. Might remove this later when
        // I have actual encoding tests.
        final IQREncoding encoding = new AlphaNumericEncoding(
            new QRConfiguration(
                1,
                ErrorCorrectionLevel.LEVEL_L,
                EncodingMode.ALPHANUMERIC),
            new Codewords());
    }

    @DataProvider
    public Object[][] getCharacterMappingTestCases()
    {
        return Stream.of(
            new Object[] { ' ', 36 },
            new Object[] { ':', 44 },
            new Object[] { '0', 0 },
            new Object[] { 'J', 19 })
            .toArray(Object[][]::new);
    }

    @DataProvider
    public Object[][] getEncodingTestCases()
    {
       return Stream.of(
            new Object[] { "HELLO WORLD", "0110000101101111000110100010111001011011100010011010100001101"},
            new Object[] { "01 A", "0000000000111001011110"},
            new Object[] { "ABC", "00111001101001100"})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "getCharacterMappingTestCases", invocationCount = ITERATIONS)
    public void characterMappingTest(final char character, final int encoded)
    {
        final Map<Character, Integer> mappings = this.getMappings();

        assertThat(mappings.get(character))
            .isNotNull()
            .isEqualTo(encoded);
    }

    @Test
    public void failedMappingTest()
    {
        final Map<Character, Integer> mappings = this.getMappings();

        assertThat(mappings.get('a'))
            .isNull();
    }

    @Test(dataProvider = "getEncodingTestCases")
    public void numericEncodingTest(final String content, final String expectedBits)
    {
        this.encodingTest(content, EncodingMode.ALPHANUMERIC, expectedBits);
    }

    @SuppressWarnings("unchecked")
    private Map<Character, Integer> getMappings()
    {
        try
        {
            final Field field = AlphaNumericEncoding.class.getDeclaredField("CHARACTER_ENCODING");

            field.setAccessible(true);

            return (Map<Character, Integer>)field.get(null);
        }
        catch (final ReflectiveOperationException error)
        {
            throw new RuntimeException(error);
        }

    }
}
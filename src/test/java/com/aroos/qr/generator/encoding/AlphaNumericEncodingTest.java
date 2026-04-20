package com.aroos.qr.generator.encoding;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public final class AlphaNumericEncodingTest
{
    @BeforeClass
    @SuppressWarnings("unused")
    public void setUp()
    {
        // This is so dumb, but I need to make sure the class is actually
        // initialized so the static field exists. Might remove this later when
        // I have actual encoding tests.
        final IQREncoding encoding = new AlphaNumericEncoding();
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

    @Test(dataProvider = "getCharacterMappingTestCases")
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
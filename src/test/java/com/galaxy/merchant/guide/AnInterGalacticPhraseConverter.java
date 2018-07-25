package com.galaxy.merchant.guide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import com.galaxy.merchant.guide.exceptions.InvalidInputFormatException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests the class that converts an intergalactic phrase into roman numeric phrase
 *
 * @author Gayathri Thiyagarajan
 */
public class AnInterGalacticPhraseConverter {

    InterGalacticPhraseConverter interGalacticPhraseConverter;

    private static HashMap<String, String> interGalacticUnitsFromInput = new HashMap<>();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void initialiseInterGalacticUnits() {

        interGalacticUnitsFromInput.put("glob", "I");
        interGalacticUnitsFromInput.put("prok", "V");
        interGalacticUnitsFromInput.put("pish", "X");
        interGalacticUnitsFromInput.put("tegj", "L");
    }

    @Before
    public void initInterGalacticPhraseConverter() {
        interGalacticPhraseConverter = new InterGalacticPhraseConverter(interGalacticUnitsFromInput);
    }

    @Test
    public void initialisesSuccessfully() {
        //Given
        // an romanNumeralConverter

        //When
        // initialised with interGalacticUnitsFromInput

        //Then
        assertNotNull(interGalacticPhraseConverter.getInterGalacticConversionUnits());
        assertEquals(interGalacticUnitsFromInput.size(), interGalacticPhraseConverter.getInterGalacticConversionUnits().size());
    }

    @Test
    public void convertsInterGalacticPhraseIntoRomanNumericPhrase() {
        //Given
        String interGalacticPhrase = "glob glob";

        //verify
        String equivalentNumericPhrase = "II";

        //when
        String romanNumericSegment = null;
        try {
            romanNumericSegment = interGalacticPhraseConverter.convertInterGalacticPhraseIntoRomanSegment(interGalacticPhrase);
        } catch (InvalidInputFormatException e) {
            fail("Should not have thrown any exception");
        }

        //then
        assertNotNull(romanNumericSegment);
        assertEquals(equivalentNumericPhrase, romanNumericSegment);
    }

    @Test
    public void convertsInputDatasetIntoCorrespondingRomanNumericPhrase() {
        //Given
        HashMap<String, String> interGalacticPhraseMap = new HashMap<>();
        interGalacticPhraseMap.put("glob glob", "II");
        interGalacticPhraseMap.put("glob prok", "IV");
        interGalacticPhraseMap.put("pish tegj", "XL");
        interGalacticPhraseMap.put("pish tegj glob glob", "XLII");
        interGalacticPhraseMap.put("pish pish", "XX");
        interGalacticPhraseMap.put("pish glob", "XI");

        //when
        for (String galacticPhrase : interGalacticPhraseMap.keySet()) {
            //then
            try {
                assertEquals(interGalacticPhraseMap.get(galacticPhrase),
                        interGalacticPhraseConverter.convertInterGalacticPhraseIntoRomanSegment(galacticPhrase));
            } catch (InvalidInputFormatException e) {
                fail("Should not have thrown any exception");
            }
        }
    }

    @Test
    public void failWhenInputPhraseIsInvalidFormat() throws InvalidInputFormatException {
        //Given
        String interGalacticPhrase = "glob fox glob";

        //when
        try {
            interGalacticPhraseConverter.convertInterGalacticPhraseIntoRomanSegment(interGalacticPhrase);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals("Input format is invalid", e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }
    }

    @Test
    public void failWhenInputPhraseIsNull() throws InvalidInputFormatException {
        //Given
        String interGalacticPhrase = null;

        //verify
        String expectedErrorMessage = "Input phrase is absent";

        //when
        try {
            interGalacticPhraseConverter.convertInterGalacticPhraseIntoRomanSegment(interGalacticPhrase);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        interGalacticPhrase = StringUtils.EMPTY;

        //when
        try {
            interGalacticPhraseConverter.convertInterGalacticPhraseIntoRomanSegment(interGalacticPhrase);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }

        //Given
        interGalacticPhrase = StringUtils.SPACE;

        //when
        try {
            interGalacticPhraseConverter.convertInterGalacticPhraseIntoRomanSegment(interGalacticPhrase);
            fail("Should have thrown exception");
        } catch (InvalidInputFormatException e) {
            //then
            assertEquals(expectedErrorMessage, e.getErrorMessage());
        } catch (Exception e) {
            fail("Should have thrown InvalidInputFormatException");
        }
    }

}

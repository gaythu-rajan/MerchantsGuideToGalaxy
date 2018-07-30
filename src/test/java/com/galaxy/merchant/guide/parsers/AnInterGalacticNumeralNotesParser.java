package com.galaxy.merchant.guide.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

/**
 * Tests for InterGalactic Unit Conversion notes Parser
 *
 * @author Gayathri Thiyagarajan
 */
public class AnInterGalacticNumeralNotesParser {

    private InterGalacticNumeralNotesParser interGalacticNumeralNotesParser = new InterGalacticNumeralNotesParser();;
    private String[] linesFromNotes = {"glob is I", "prok is V","pish is X", "tegj is L"};

    @Test
    public void parsesLinesFromNotesContainingGalacticUnitConversion() {
        //Given

        //When
        HashMap<String, String> galacticUnitConversionMap = interGalacticNumeralNotesParser.parseNotes(Arrays.asList(linesFromNotes));

        //Then
        assertNotNull(galacticUnitConversionMap);
        assertEquals(4, galacticUnitConversionMap.size());

        assertEquals("I", galacticUnitConversionMap.get("glob"));
        assertEquals("V", galacticUnitConversionMap.get("prok"));
        assertEquals("X", galacticUnitConversionMap.get("pish"));
        assertEquals("L", galacticUnitConversionMap.get("tegj"));

    }
}

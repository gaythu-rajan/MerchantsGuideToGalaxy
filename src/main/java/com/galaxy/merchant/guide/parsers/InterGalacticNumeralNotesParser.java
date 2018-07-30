package com.galaxy.merchant.guide.parsers;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * InterGalacticNumeralNotesParser parses lines from notes to
 * map InterGalactic numeral to roman numerals e.g."glob is I" to glob-I
 *
 * @author Gayathri Thiyagarajan
 */
public class InterGalacticNumeralNotesParser implements NotesParser {

    public HashMap<String, String> parseNotes(List<String> galacticUnitLinesFromNotes) {

        HashMap<String, String> interGalacticConversionUnits = new HashMap<>();

        for (String line : galacticUnitLinesFromNotes) {
            String[] wordsInALine = line.split(StringUtils.SPACE);
            String galacticUnit = wordsInALine[0];
            String romanNumericEquiv = wordsInALine[2];
            interGalacticConversionUnits.put(galacticUnit.toLowerCase(), romanNumericEquiv);
        }
        return interGalacticConversionUnits;
    }
}

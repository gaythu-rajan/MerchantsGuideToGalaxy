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

    private HashMap<String, String> interGalacticToRomanConversionMap = new HashMap<>();

    public void parseNotes(List<String> galacticUnitLinesFromNotes) {

        for (String line : galacticUnitLinesFromNotes) {
            String[] wordsInALine = line.split(StringUtils.SPACE);
            String galacticUnit = wordsInALine[0];
            String romanNumericEquiv = wordsInALine[2];
            interGalacticToRomanConversionMap.put(galacticUnit.toLowerCase(), romanNumericEquiv);
        }
    }

    public HashMap<String, String> getInterGalacticToRomanConversionMap() {
        return interGalacticToRomanConversionMap;
    }
}

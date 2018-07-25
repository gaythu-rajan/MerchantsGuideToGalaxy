package com.galaxy.merchant.guide;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * InterGalacticUnitConversionNotesParser class parses lines from notes to
 * extract Galactic Unit conversion data into a map e.g."glob is I" to glob-I
 *
 * @author Gayathri Thiyagarajan
 */
class InterGalacticUnitConversionNotesParser implements NotesParser {

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

package com.galaxy.merchant.guide;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * GalacticUnitConversionNotesParser class to parse lines from notes to
 * extract Galactic Unit conversion data into a map e.g."glob is I" to glob-I
 *
 * @author Gayathri Thiyagarajan
 */
class InterGalacticUnitConversionNotesParser extends NotesParser {

    HashMap<String, String> parseNotes(List<String> galacticUnitLinesFromNotes) {

        HashMap<String, String> interGalacticConversionUnits = new HashMap<>();

        for (String line : galacticUnitLinesFromNotes) {
            String[] wordsInALine = line.split(StringUtils.SPACE);
            String galacticUnit = wordsInALine[0];
            String romanNumericEquiv = wordsInALine[2];               //todo - check they are roman chars
            interGalacticConversionUnits.put(galacticUnit, romanNumericEquiv);
        }
        return interGalacticConversionUnits;
    }
}

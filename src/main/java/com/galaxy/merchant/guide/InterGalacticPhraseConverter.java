package com.galaxy.merchant.guide;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;
import static org.apache.commons.lang3.StringUtils.replaceEach;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.galaxy.merchant.guide.exceptions.InvalidInputFormatException;

/**
 * Converter to convert InterGalactic Phrase into Earthly numeric phrase and numeric value of an earthly phrase
 *
 * @author Gayathri Thiyagarajan
 */
class InterGalacticPhraseConverter {

    //Map of interGalacticConversionUnits and their equivalent numeral e.g. glob-I
    private HashMap<String, String> interGalacticConversionUnits = new HashMap<>();

    InterGalacticPhraseConverter(HashMap<String, String> interGalacticConversionUnits) {
        this.interGalacticConversionUnits = interGalacticConversionUnits;
    }

    /**
     * Converts an Intergalactic phrase into a roman segment using the intergalactic units  parsed earlier
     *
     * @param interGalacticPhrase e.g. "glob glob"
     * @return Equivalent roman segment parsed using Intergalactic units "II"
     * @throws InvalidInputFormatException if the Intergalactic Phrase does not contain a valid inter galactic units from interGalacticConversionUnits
     */
    String convertIntergalacticPhraseIntoRomanSegment(String interGalacticPhrase) throws InvalidInputFormatException {

        checkInterGalacticPhraseContainsValidInterGalacticUnits(interGalacticPhrase);

        String splitPhrase = interGalacticPhrase.replaceAll(SPACE, EMPTY);

        String[] galacticStringsToSearch = new String[interGalacticConversionUnits.size()];
        galacticStringsToSearch = interGalacticConversionUnits.keySet().toArray(galacticStringsToSearch);

        String[] numericStringsToReplace = new String[interGalacticConversionUnits.size()];
        numericStringsToReplace = interGalacticConversionUnits.values().toArray(numericStringsToReplace);

        return replaceEach(splitPhrase, galacticStringsToSearch, numericStringsToReplace);
    }

    /**
     * Checks that the Intergalactic phrase contains valid inter galactic units like glob, pish etc
     *
     * @param interGalacticPhrase e.g. "glob glob"
     * @throws InvalidInputFormatException if the interGalacticPhrase is null, empty/blank, whitespace
     * or contains words that are not one of interGalacticConversionUnits list
     */
    private void checkInterGalacticPhraseContainsValidInterGalacticUnits(String interGalacticPhrase) throws InvalidInputFormatException {

        if(!isNoneBlank(interGalacticPhrase))
            throw new InvalidInputFormatException("Input phrase is absent");

        String[] arr = interGalacticPhrase.split(SPACE);
        List aList = Arrays.stream(arr).filter(s -> !interGalacticConversionUnits.containsKey(s)).collect(Collectors.toList());

        if(aList.size() != 0)
            throw new InvalidInputFormatException("Input format is invalid");
    }

    HashMap<String, String> getInterGalacticConversionUnits() {
        return interGalacticConversionUnits;
    }

}
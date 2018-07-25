package com.galaxy.merchant.guide;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.substringBefore;
import static org.apache.commons.lang3.StringUtils.trim;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.galaxy.merchant.guide.exceptions.InvalidInputFormatException;

/**
 * CreditsForEarthMaterialNotesParser class to parse lines from notes to extract transaction
 * information in order to calculate the number of credits that each EarthMaterial is worth
 * @author Gayathri Thiyagarajan
 */
class EarthMaterialTransactionParser extends NotesParser {

    public static String PATTERN_OF_EARTH_MATERIALS = "(Silver|Gold|Iron){1}";       //todo - chain these from constants
    public static String PATTERN_OF_TRANSACTION_PART ;
    private static String EARTH_MATERIAL_TRANSACTION_FORMAT;
    private static String SPLIT_TRANSACTION_BY_STRING = "(\\sis\\s){1}";
    private static String STRIP_OUT_STRING = "Credits";

    private final HashMap<String, String> interGalacticConversionUnits;

    public EarthMaterialTransactionParser(HashMap<String, String> interGalacticConversionUnits) {
        this.interGalacticConversionUnits = interGalacticConversionUnits;
        Set<String> interGalacticUnits = interGalacticConversionUnits.keySet();
        PATTERN_OF_TRANSACTION_PART = interGalacticUnits.stream().map(e -> e).collect(Collectors.joining("|"));
        EARTH_MATERIAL_TRANSACTION_FORMAT = "^((" + PATTERN_OF_TRANSACTION_PART + ")\\s)+" + PATTERN_OF_EARTH_MATERIALS + "(\\sis\\s){1}\\d+(\\sCredits){1}";
    }

    /**
     * Parses transaction notes like "glob glob Silver is 34 Credits"
     * @param earthMaterialTransactions Array of transactions
     * @return a map of transactions vs credits  e.g "glob glob Silver-34"
     */
    HashMap<String, Double> parseNotes(List<String> earthMaterialTransactions) throws InvalidInputFormatException {

        HashMap<String, Double> creditsForEarthMaterialTransactions = new HashMap<>();

        Pattern compiledTransactionPattern = Pattern.compile(EARTH_MATERIAL_TRANSACTION_FORMAT);
        Pattern compiledEarthMaterialpattern = Pattern.compile(PATTERN_OF_EARTH_MATERIALS);

        InterGalacticPhraseConverter interGalacticPhraseConverter = new InterGalacticPhraseConverter(interGalacticConversionUnits);
        RomanNumeralConverter romanNumeralConverter = new RomanNumeralConverter();
        Matcher matcher;

        for (String aLineOfTransaction : earthMaterialTransactions) {

            //check that the transaction sticks to the expected format
            if (aLineOfTransaction == null || !compiledTransactionPattern.matcher(aLineOfTransaction).matches())
                continue;

            String[] aLineOfTransactionAsArray = aLineOfTransaction.split(SPLIT_TRANSACTION_BY_STRING);

            String remainderOfTheString = aLineOfTransactionAsArray[1];
            String creditsAsString = trim(remainderOfTheString.replace(STRIP_OUT_STRING, EMPTY));
            Integer totalCreditsInTheTransaction = Integer.valueOf(creditsAsString);

            String transaction = aLineOfTransactionAsArray[0];

            matcher = compiledEarthMaterialpattern.matcher(transaction);

            if(matcher.find()) {
                //keyForMap = glob glob*Silver
                String interGalacticValue = trim(substringBefore(transaction, matcher.group(1)));
                String earthMaterial = matcher.group(1);

                Integer quantityOfMaterial = romanNumeralConverter.convertRomanSegmentIntoNumericValue(interGalacticPhraseConverter.convertIntergalacticPhraseIntoRomanSegment(interGalacticValue));
                double numberOfCreditsPerUnitOfMaterial = (double) totalCreditsInTheTransaction / quantityOfMaterial;
                creditsForEarthMaterialTransactions.put(earthMaterial, numberOfCreditsPerUnitOfMaterial);
            }
        }
        return creditsForEarthMaterialTransactions;
    }

}

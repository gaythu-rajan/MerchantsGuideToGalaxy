package com.galaxy.merchant.guide;

import static com.galaxy.merchant.guide.constants.InterGalacticAppConstants.PATTERN_OF_EARTH_MATERIALS;
import static java.util.regex.Pattern.compile;
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
 * TransactionNotesParser class to parse lines from notes to extract transaction
 * information in order to calculate the number of credits that each EarthMaterial is worth
 * @author Gayathri Thiyagarajan
 */
class TransactionNotesParser implements NotesParser {

    private String EARTH_MATERIAL_TRANSACTION_FORMAT;

    private final HashMap<String, String> interGalacticConversionUnits;

    public TransactionNotesParser(HashMap<String, String> interGalacticConversionUnits) {
        this.interGalacticConversionUnits = interGalacticConversionUnits;
        Set<String> interGalacticUnits = interGalacticConversionUnits.keySet();
        String PATTERN_OF_TRANSACTION_PART = interGalacticUnits.stream().map(e -> e).collect(Collectors.joining("|"));
        this.EARTH_MATERIAL_TRANSACTION_FORMAT = "^((" + PATTERN_OF_TRANSACTION_PART + ")\\s)+"
                                                        + PATTERN_OF_EARTH_MATERIALS
                                                        + "(\\sis\\s){1}\\d+(\\scredits){1}";
    }

    /**
     * Parses transaction notes like "glob glob Silver is 34 Credits"
     * @param earthMaterialTransactions Array of transactions
     * @return a map of earth material vs no. of credits per unit  e.g "Silver-17f"
     */
    public HashMap<String, Double> parseNotes(List<String> earthMaterialTransactions) throws InvalidInputFormatException {

        HashMap<String, Double> creditsForEarthMaterialTransactions = new HashMap<>();

        Pattern compiledTransactionPattern = compile(EARTH_MATERIAL_TRANSACTION_FORMAT, Pattern.CASE_INSENSITIVE);
        Pattern compiledEarthMaterialPattern = compile(PATTERN_OF_EARTH_MATERIALS, Pattern.CASE_INSENSITIVE);

        InterGalacticPhraseConverter interGalacticPhraseConverter = new InterGalacticPhraseConverter(interGalacticConversionUnits);
        RomanNumericConverter romanNumericConverter = new RomanNumericConverter();
        Matcher matcher;

        for (String aLineOfTransaction : earthMaterialTransactions) {

            //check that the transaction sticks to the expected format
            if (aLineOfTransaction == null || !compiledTransactionPattern.matcher(aLineOfTransaction).matches())
                continue;

            String SPLIT_TRANSACTION_BY_STRING = "(\\sis\\s)";
            String[] aLineOfTransactionAsArray = aLineOfTransaction.split(SPLIT_TRANSACTION_BY_STRING);

            String remainderOfTheString = aLineOfTransactionAsArray[1];
            String STRIP_OUT_STRING = "credits";
            String creditsAsString = trim(remainderOfTheString.replace(STRIP_OUT_STRING, EMPTY));
            Integer totalCreditsInTheTransaction = Integer.valueOf(creditsAsString);

            String transaction = aLineOfTransactionAsArray[0];

            matcher = compiledEarthMaterialPattern.matcher(transaction);

            if(matcher.find()) {
                String interGalacticValue = trim(substringBefore(transaction, matcher.group(1)));
                String earthMaterial = matcher.group(1);

                Integer quantityOfMaterial = romanNumericConverter.convertRomanSegmentIntoNumericValue(
                                                interGalacticPhraseConverter.convertInterGalacticPhraseIntoRomanSegment(interGalacticValue));
                double numberOfCreditsPerUnitOfMaterial = (double) totalCreditsInTheTransaction / quantityOfMaterial;
                creditsForEarthMaterialTransactions.put(earthMaterial, numberOfCreditsPerUnitOfMaterial);
            }
        }
        return creditsForEarthMaterialTransactions;
    }

}

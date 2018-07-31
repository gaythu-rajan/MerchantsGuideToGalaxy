package com.galaxy.merchant.guide.parsers;

import static com.galaxy.merchant.guide.constants.InterGalacticAppConstants.EARTH_MATERIALS_PART;
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

import com.galaxy.merchant.guide.converters.InterGalacticToRomanConverter;
import com.galaxy.merchant.guide.converters.RomanToArabicConverter;
import com.galaxy.merchant.guide.exceptions.InvalidInputFormatException;

/**
 * TransactionNotesParser class to parse lines from notes to extract transaction
 * information in order to calculate the number of credits that each EarthMaterial is worth
 * @author Gayathri Thiyagarajan
 */
public class TransactionNotesParser implements NotesParser {

    private HashMap<String, Double> creditsPerEarthMaterial = new HashMap<>();

    private final HashMap<String, String> interGalacticConversionUnits;

    private Pattern compiledTransactionPattern;

    private Pattern compiledEarthMaterialsPattern;

    private InterGalacticToRomanConverter interGalacticToRomanConverter;

    private RomanToArabicConverter romanToArabicConverter;


    public TransactionNotesParser(HashMap<String, String> interGalacticConversionUnits) {

        this.interGalacticConversionUnits = interGalacticConversionUnits;

        interGalacticToRomanConverter = new InterGalacticToRomanConverter(interGalacticConversionUnits);
        romanToArabicConverter = new RomanToArabicConverter();
    }

    /**
     * Parses transaction notes like "glob glob Silver is 34 Credits"
     * @param earthMaterialTransactions Array of transactions
     * @return a map of earth material vs no. of credits per unit  e.g "Silver-17f"
     */
    public void parseNotes(List<String> earthMaterialTransactions) throws InvalidInputFormatException {

        compilePatternsForParsing();

        for (String aLineOfTransaction : earthMaterialTransactions) {

            //check that the transaction sticks to the expected format
            if (aLineOfTransaction == null || !compiledTransactionPattern.matcher(aLineOfTransaction).matches())
                continue;

            calculateCreditsPerEarthMaterial(aLineOfTransaction);
        }
    }

    /**
     * Computes and compiles the pattern of a transaction given a list of intergalactic numerals and earth materials for sale
     */
    private void compilePatternsForParsing() {

        //Set the format of a transaction given all the intergalactic numerals from the first part of the input

        Set<String> interGalacticUnits = interGalacticConversionUnits.keySet();
        String INTERGALACTIC_QUANTITY_PART = interGalacticUnits.stream().map(e -> e).collect(Collectors.joining("|"));

        String TRANSACTION_FORMAT = "^(("
                                  + INTERGALACTIC_QUANTITY_PART
                                  + ")\\s)+"
                                  + EARTH_MATERIALS_PART
                                  + "(\\sis\\s){1}\\d+(\\scredits){1}";

        compiledEarthMaterialsPattern = compile(EARTH_MATERIALS_PART, Pattern.CASE_INSENSITIVE);
        compiledTransactionPattern = compile(TRANSACTION_FORMAT, Pattern.CASE_INSENSITIVE);

    }

    /**
     * Calculates number of credits per earth material from each line of transaction
     *
     * @param aLineOfTransaction a single line of transaction
     * @throws InvalidInputFormatException
     */
    private void calculateCreditsPerEarthMaterial(String aLineOfTransaction) throws InvalidInputFormatException {
        Matcher matcher;

        String SPLIT_TRANSACTION_BY_STRING = "(\\sis\\s)";
        String[] aLineOfTransactionAsArray = aLineOfTransaction.split(SPLIT_TRANSACTION_BY_STRING);

        String remainderOfTheString = aLineOfTransactionAsArray[1];
        String STRIP_OUT_STRING = "credits";
        String creditsAsString = trim(remainderOfTheString.replace(STRIP_OUT_STRING, EMPTY));
        Integer totalCreditsInTheTransaction = Integer.valueOf(creditsAsString);

        String transaction = aLineOfTransactionAsArray[0];

        matcher = compiledEarthMaterialsPattern.matcher(transaction);

        if(matcher.find()) {
            String interGalacticValue = trim(substringBefore(transaction, matcher.group(1)));
            String earthMaterial = matcher.group(1);

            Integer quantityOfMaterial = romanToArabicConverter.convertRomanSegmentIntoNumericValue(
                                            interGalacticToRomanConverter.convertInterGalacticPhraseIntoRomanSegment(interGalacticValue));
            double numberOfCreditsPerUnitOfMaterial = (double) totalCreditsInTheTransaction / quantityOfMaterial;
            creditsPerEarthMaterial.put(earthMaterial, numberOfCreditsPerUnitOfMaterial);
        }
    }

    public HashMap<String, Double> getCreditsPerEarthMaterial() {
        return creditsPerEarthMaterial;
    }
}
